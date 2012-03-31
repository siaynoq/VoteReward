package com.tregele.bukkit.votereward.rewards;

public abstract class RewardBase implements Reward {
    
    private int chance;

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
