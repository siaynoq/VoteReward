package com.tregele.bukkit.votereward.rewards;

import com.tregele.bukkit.votereward.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Random;

public class XpReward extends RewardBase {


    public XpReward(LinkedHashMap<String, Object> reward) throws ConfigurationException {

        super(reward);

        int minAmount;
        int maxAmount;

        if (reward.get("amount") == null) {
            minAmount = 1;
            maxAmount = 1;
        } else {
            if (reward.get("amount").getClass() == Integer.class) {
                minAmount = (Integer) reward.get("amount");
                maxAmount = minAmount;
            } else {
                //String
                String[] amountsString = StringUtils.split((String) reward.get("amount"), "-");
                try {
                minAmount = Integer.parseInt(amountsString[0]);
                maxAmount = Integer.parseInt(amountsString[1]);
                } catch (NumberFormatException e) {
                    throw new ConfigurationException("XP amount is not an integer for '" + this.getName() + "'", e);
                }
            }
        }

        this.setAmountMax(maxAmount);
        this.setAmountMin(minAmount);
    }

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
