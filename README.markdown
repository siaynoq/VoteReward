# VoteReward v0.4.1

 VoteReward is a plugin for Bukkit Minecraft Servers.
 It rewards selected members of the community who vote on any of the servers which supports [Votifier](https://github.com/vexsoftware/votifier).
 This means this plugin works only if Votifier is setup correctly on your server.

 It uses an internal SQLite DB (stored in votes.db) to log the votes coming from the server.
 Rewards are currently given 1/player/day only (whether it comes from different serverlist page).

## Compatibility

VoteReward was tested and compiled with:

- CraftBukkit 1.4.7-R0.1
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

    messages: ["Successfully voted!", "Item won: {name}"]
    groups:
        - normal:
            - always: Green Gold
              type: item
              data_value: EMERALD
              amount: 5-10
            - name: A wooden axe
              chance: 50
              type: item
              data_value: 271
              amount: 1-5
            - name: Brown wool!
              chance: 2
              type: item
              data_value: 35;12
              amount: 1-3
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

 First, you can set what message(s) the user will see after getting the reward. You can use Bukkit chat color codes by prefixing it with <code>\u00A7</code>.
 E.g. <code>\u00A7aHey</code> is 'Hey' all in green.

 Here we have two groups defined - "normal" and "vip". The "items" underneath are the rewards for each group.
 You have to assign the VoteReward group to the group/player in your permissions.yml with the prefix "votereward" (e.g. "votereward.normal")
 You can control this way which player gets reward from which VoteReward group (or if (s)he gets reward at all! :) )

 Reward properties:

 - Common properties:
    - name: the "description" the user will see when (s)he gets this reward.
      - always: if the "name" is specified with the key "always", the defined reward is ALWAYS given (this doesn't affect random rewards). "chance" in this case is not considered obviously.
    - chance: numeric value, which represents the chance this reward is selected. Sum of chances doesn't have to be '100'.

 Current "types" of rewards:

 - item: an ingame item
     - 'data_value' is the ingame decimal value or the Bukkit uppercase material name for the item. You can give "subitem value" with a semicolon (;). Example: <code>data_value: 35;12</code> is brown wool. This is the same: <code>data_value: WOOL;12</code>
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

# Release history

 - 0.4.1 - 2102/2013 (Beta)
      - added feature: Bukkit material name (e.g. GRASS) can be used as data_value
      - bugfix:
          - "Issue 1" fixed: player name is displayed in logs when offline

 - 0.4.0 - still beta - 30/01/2013
      - added feature:
          - you can define "always" reward(s), which is/are given on top of the random roll

 - 0.3.3 - still beta - 14/04/2012
      - added feature:
          - now you are able to define "subitem data" for Item reward. E.g.: <code>data_value: 35;12</code> is brown wool
      - other:
          - code refactor
          - added config read error handling
 - 0.3.2 - bugfix release
     - bugfixes
         - major bug: votes were considered as "same day" even though they were clearly not
         - added config check if "groups" are missing (to avoid exception)
 - 0.3.1 - bugfix release
     - bugfixes
         - now "nothing" item type is really added to reward group - 03/04/2012
         - redesigned config.yml reading for error cases
     - changed features:
         - changed <code>votes.db</code> SQLite directory file to <code>plugins/Votifier</code>
 - 0.3.0 - still beta - 03/04/2012
     - New features:
         - added configurable message to player
     - bugfixes:
         - sporadic NullPointer exception while rolling reward
         - config reload (<code>/vrreload</code>) now correctly reloads from disk
 - 0.2.0 - still beta, first public release - 01/04/2012
 - 0.1.0 - beta, not really proud of it, so it's not really released


