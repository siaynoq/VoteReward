package com.tregele.bukkit.votereward.rewards;

import org.bukkit.entity.Player;

import java.util.Random;

public interface Reward {

    int doAction(Player player, Random random);

    int getChance();

    void setChance(int chanceNum);

    String getName();

    String toString();

}
