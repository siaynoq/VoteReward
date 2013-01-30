package com.tregele.bukkit.votereward;

import com.tregele.bukkit.votereward.rewards.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class VoteRewardConfiguration {

    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public List<RewardGroup> getRewardGroups() {
        return rewardGroups;
    }

    private List<RewardGroup> rewardGroups;

    public VoteRewardConfiguration(YamlConfiguration fc) throws ConfigurationException {
        parseConfig(fc);
    }

    public void parseConfig(YamlConfiguration fc) throws ConfigurationException {
        rewardGroups = new ArrayList<RewardGroup>();


        //reading messages
        messages = fc.getStringList("messages");

        if (fc.getList("groups") == null) {
            throw new ConfigurationException("'groups' entry could not be found in configuration, aborting.");
        }

        //reading reward groups
        for (Object groupListObject : fc.getList("groups")) {

            LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> groupList =
                    (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>) groupListObject;

            for (String groupName : groupList.keySet()) {
                RewardGroup rewardGroup = new RewardGroup();
                rewardGroup.setName(groupName);
                ArrayList<LinkedHashMap<String, Object>> rewardList = groupList.get(groupName);
                for (LinkedHashMap<String, Object> reward : rewardList) {

                    Reward rewardToAdd;

                    String rewardType = (String) reward.get("type");
                    //TODO extract to RewardFactory
                    /**
                     * Reward Type: 'item'
                     */
                    if ("item".equals(rewardType)) {
                        rewardToAdd = new ItemReward(reward);
                    }

                    /**
                     * Reward Type: 'xp'
                     */
                    else if ("xp".equals(rewardType)) {
                        rewardToAdd = new XpReward(reward);
                    }
                    /**
                     * Reward Type: 'nothing'
                     */
                    else if ("nothing".equals(rewardType)) {
                        rewardToAdd = new NoReward(reward);
                    }

                    /*else if(rewardType.equals("multiple_items")) {
                        MultipleItemsReward multipleItemsReward = new MultipleItemsReward();

                        multipleItemsReward.setName((String) reward.get("name"));
                        multipleItemsReward.setChance((Integer) reward.get("chance"));

                    }*/
                    else {
                        throw new ConfigurationException("Error in config: '" + rewardType + "' is not a valid reward type");
                    }

                    if(reward.containsKey("always")) {
                        rewardGroup.addAlwaysReward(rewardToAdd);
                    } else {
                        rewardGroup.addReward(rewardToAdd);
                    }


                }

                rewardGroups.add(rewardGroup);
                //log.info("Added reward group: " + rewardGroup.getName() + " with " + rewardGroup.getRewardListSize() + " rewards");
            }

        }

        //log.info("Read from config: " + rewardGroups.size() + " groups");

    }

}
