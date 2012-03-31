package com.tregele.bukkit.votereward.rewards;

import org.bukkit.entity.Player;

import java.util.Random;

public class NoReward extends RewardBase {

    @Override
    public int doAction(Player player, Random random) {
          return 0;
    }

    public String toString() {
        return "Nothing: (Well... it's nothing)";
    }
}
