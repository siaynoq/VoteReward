package com.tregele.bukkit.votereward.rewards;

import com.tregele.bukkit.votereward.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Random;


public class ItemReward extends RewardBase {


    public ItemReward(LinkedHashMap<String, Object> reward) throws ConfigurationException {
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
                    throw new ConfigurationException("Item amount is not an integer", e);
                }
            }
        }

        ItemStack itemToSet;
        Object itemObject = reward.get("data_value");
        if (itemObject.getClass() == Integer.class) {
            itemToSet = new ItemStack((Integer) reward.get("data_value"));
        } else {
            //String
            String[] itemData = StringUtils.split((String) reward.get("data_value"), ";");
            if (itemData.length != 2) {
                throw new ConfigurationException("Item data is not in 123;12 format: " + reward.get("data_value"));
            }
            try {
                itemToSet = new ItemStack(Integer.parseInt(itemData[0]), 1, Short.parseShort(itemData[1]));
            } catch (NumberFormatException e) {
                throw new ConfigurationException("Item data is not in 123;12 format", e);
            }
        }


        this.setItem(itemToSet);
        this.setAmountMin(minAmount);
        this.setAmountMax(maxAmount);

    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
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

    private ItemStack item;
    private int amountMin;
    private int amountMax;

    @Override
    public int doAction(Player player, Random random) {
        int amountToAdd = random.nextInt(amountMax - amountMin + 1) + amountMin;
        item.setAmount(amountToAdd);
        player.getInventory().addItem(item);
        return amountToAdd;
    }

    @Override
    public String toString() {
        return "Item: " + getName() + " (" + getItem().getType().name() + " - Amount: " + getAmountMin() + "-" + getAmountMax() + " - Chance: " + getChance() + ")";
    }


}
