# VoteReward v0.2.0

 VoteReward is a plugin for Bukkit Minecraft Servers.
 It rewards selected members of the community who vote on any of the servers which supports [Votifier](https://github.com/vexsoftware/votifier).
 This means this plugin works only if Votifier is setup correctly on your server.

 It uses an internal SQLite DB (stored in votes.db) to log the votes coming from the server.
 Rewards are currently given 1/player/day only (whether it comes from different serverlist page).

## Compatibility

VoteReward was tested and compiled with:

- CraftBukkit 1.2.4-R1.0
- Votifier 1.16
- PermissionsEx 1.19.1

## Installation intructions

### Install and setup Votifier

 Follow carefully the installation instructions of the [Votifier](https://github.com/vexsoftware/votifier) plugin.
 You have to have a working Votifier to use VoteReward. Try FlatfileVoteListener.class to check if votes are really coming through.

### Copy VoteReward listener

 Copy the <code>VoteRewardListener.class</code> to your Votifier listeners directory (default is <code>plugins/Votifier/listeners</code>).

### Install VoteReward plugin

 Copy <code>votereward.jar</code> to your plugins directory. By reloading the plugins, it will create its default config.yml in the plugins/VoteReward directory.
 Fine tune it to your needs. Remember to reload the plugins or use <code>/vrreload</code> to re-read the modified config.

# Usage

 VoteReward uses [PermissionsEx] (https://github.com/PEXPlugins/PermissionsEx/wiki) (PEX) to handle permissions. It's important to understand how "group permissions" are used to control who gets what reward.

 Sample <code>config.yml</code>:

    groups:
        - normal:
            - name: A wooden axe
              chance: 50
              type: item
              data_value: 271
              amount: 1-5
            - name: Grand prize!
              chance: 50
              type: item
              #diamond pickaxe
              data_value: 278
              amount: 1
            - name: Some experience
              chance: 10
              type: xp
              amount: 20-50
            - name: Nothing ^^
              chance: 39
              type: nothing
        - vip:
            - name: Some dirt
              chance: 10
              type: item
              data_value: 266
              amount: 1-10
            - name: Grand prize
              chance: 50
              type: item
              data_value: 265
              amount: 1-2

 Here we have two groups defined - "normal" and "vip". The "items" underneath are the rewards for each group.
 You have to assign the VoteReward group to the group/player in your permissions.yml with the prefix "votereward" (e.g. "votereward.normal")
 You can control this way which player gets reward from which VoteReward group (or if (s)he gets reward at all! :) )

 Reward properties:

 - Common properties:
    - name: the "description" the user will see when (s)he gets this reward.
    - chance: numeric value, which represents the chance this reward is selected. Sum of chances doesn't have to be '100'.

 Current "types" of rewards:

 - item: an ingame item
     - 'data_value' is the ingame decimal value for the item.
     - 'amount' is a fix number or an interval of points which will be given. '1' if not defined.
 - xp: experience points
     - 'amount' is a fix number or an interval of points which will be given. '1' if not defined.
 - nothing: well... it's nothing :)

 Important note on <code>'\*'</code> permission:

 Using <code>'\*'</code> is not recommended in PEX. But if you do assign <code>votereward.\*</code> or <code>'\*'<code> to a PEX group/player, this means the following in case of VoteReward:
 - the group/person can execute all commands (see below)
 - if someone from the <code>'\*'</code> group votes, (s)he will get one reward from EACH group!

# Admin/test commands

 All admin commands belong to the permission node "votereward.command"

### <code>/votereward <playerName></code>

 Simulates a votereward action. Doesn't actually log the vote in the DB, so you can use it as many times as you want to.

### <code>/vrreload</code>

 Reloads <code>VoteReward/config.yml</code> file to memory.


