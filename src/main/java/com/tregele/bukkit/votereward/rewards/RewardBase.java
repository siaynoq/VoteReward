package com.tregele.bukkit.votereward.rewards;

import java.util.LinkedHashMap;

public abstract class RewardBase implements Reward {
    
    private int chance;

    public RewardBase(LinkedHashMap<String, Object> reward) {
        this.setName((String) reward.get("name"));
        this.setChance((Integer) reward.get("chance"));
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
