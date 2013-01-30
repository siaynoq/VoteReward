package com.tregele.bukkit.votereward.rewards;

import java.util.*;

public class RewardGroup {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReward(Reward reward) {
        rewardList.put(totalChance, reward);
        totalChance += reward.getChance();
    }

    public void addAlwaysReward(Reward reward) {
        alwaysRewardList.add(reward);
    }

    public List<Reward> getAlwaysRewardList() {
        return this.alwaysRewardList;
    }

    private String name;
    private Map<Integer, Reward> rewardList = new HashMap<Integer, Reward>();
    private List<Reward> alwaysRewardList = new ArrayList<Reward>();
    private int totalChance = 0;

    public int getRewardListSize() {
        return rewardList.size();
    }

    public Reward rollReward(Random random) {

        int currentRoll = random.nextInt(totalChance);

        for(Integer chance : rewardList.keySet()) {
            if((currentRoll >= chance) && (currentRoll < (chance + rewardList.get(chance).getChance()))) {
                return rewardList.get(chance);
            }
        }

        return null;

    }

}
