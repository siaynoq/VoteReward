package com.tregele.bukkit.votereward.rewards;

import org.bukkit.entity.Player;

import java.util.Random;

public class XpReward extends RewardBase {

    public int getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(int amountMin) {
        this.amountMin = amountMin;
    }

    public int getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(int amountMax) {
        this.amountMax = amountMax;
    }

    private int amountMin;
    private int amountMax;

    @Override
    public int doAction(Player player, Random random) {
        int expToAdd = random.nextInt(amountMax - amountMin + 1) + amountMin;
        player.giveExp(expToAdd);
        return expToAdd;
    }

    public String toString() {
        return "XP: " + getName() + " (Amount: " + getAmountMin() + "-" + getAmountMax() + " - Chance: " + getChance() + ")";
    }
}
