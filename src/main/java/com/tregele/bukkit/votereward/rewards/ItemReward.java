package com.tregele.bukkit.votereward.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;


public class ItemReward extends RewardBase {

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
        return "Item: " + getName() + " ("  + getItem().getType().name() + " - Amount: " + getAmountMin() + "-" + getAmountMax() + " - Chance: " + getChance() + ")";
    }

    
}
