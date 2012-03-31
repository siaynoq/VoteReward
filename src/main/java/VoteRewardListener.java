import com.tregele.bukkit.votereward.VoteReward;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VoteListener;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoteRewardListener implements VoteListener {

    private static final Logger log = Logger.getLogger("VoteRewardListener");

    private boolean isActive = false;

    public VoteRewardListener() {
        try {
            Class.forName("org.sqlite.JDBC");
            isActive = true;
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "org.sqlite.JDBC driver not found. VoteRewardListener is disabled", e);
        }
    }


    @Override
    public void voteMade(Vote vote) {

        if(!isActive) {
            return;
        }

        log.info("Vote arrived: " + vote.toString());
        int timestampInt;
        try {
            timestampInt = Integer.parseInt(vote.getTimeStamp());
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Timestamp in vote is not in UNIX timestamp format, vote is not registered/rewarded", e);
            return;
        }

        int numberOfVotesToday = 0;

        try {

            Connection conn =
                    DriverManager.getConnection("jdbc:sqlite:votes.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS vote (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT," +
                    "servicename TEXT," +
                    "address TEXT," +
                    "external_timestamp TEXT," +
                    "timestamp TEXT);");

            PreparedStatement prep = conn.prepareStatement("INSERT INTO vote (username, servicename, address, external_timestamp, timestamp) VALUES " +
                    "(?, ?, ?, ?, date('now'));");

            prep.setString(1, vote.getUsername());
            prep.setString(2, vote.getServiceName());
            prep.setString(3, vote.getAddress());
            prep.setString(4, vote.getTimeStamp());
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM vote WHERE username='" + vote.getUsername() + "' " +
                    "AND date(external_timestamp)>date(" + timestampInt + 86400 + ");");

            if (rs.next()) {
                numberOfVotesToday = rs.getInt(1);
            }
            rs.close();
            conn.close();

        } catch (SQLException e) {
            //check/close rs or conn?
            log.log(Level.SEVERE, "Exception while adding vote to DB", e);
            return;
        }

        if (numberOfVotesToday == 1) {

            VoteReward vr = VoteReward.getInstance();
            if (vr == null) {
                log.severe("VoteReward plugin was not initialised/enabled, cannot proceed - vote could not be rewarded.");
                return;
            }

            vr.voteReward(vote.getUsername());

        } else {
            log.info("User " + vote.getUsername() + " has already voted today, sorry :)");
        }


    }
}
