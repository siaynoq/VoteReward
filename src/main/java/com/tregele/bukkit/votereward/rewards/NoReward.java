package com.tregele.bukkit.votereward.rewards;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Random;

public class NoReward extends RewardBase {

    public NoReward(LinkedHashMap<String, Object> reward) {
        super(reward);
    }

    @Override
    public int doAction(Player player, Random random) {
        return 0;
    }

    public String toString() {
        return "Nothing: (Well... it's nothing)";
    }
}
