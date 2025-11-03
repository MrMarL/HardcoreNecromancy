# HardcoreNecromancy

## For a hardcore server.

If a player holding a totem in both hands writes the command 

```
/necro [nickname of the dead player]
```

, then both totems are used, after which the dead player is resurrected right in front of the resurrector, totem effects are applied to both players, and hunger for 5 minutes, nausea for 10 seconds, weakness and fatigue for 30 seconds are also imposed on the resurrected one.

After using the command, both players (both the resurrector and the resurrected) permanently lose 3 health hearts, the team cannot be used with 4 health hearts, while in observer mode, and on live players.

## config.yml

```
# HardcoreNecromancy Configuration
# Amount of hearts to remove from players during revival (2 hearts = 1 health unit)
hearts-to-remove: 6

# Whether to remove hearts from the reviver (person performing the revival)
# If false, hearts will only be removed from the revived player
remove-hearts-from-reviver: true
```

## messages.yml

```
# HardcoreNecromancy Messages
# Use '&' for color codes and %variable% for placeholders

console-cannot-revive: "&cYou have to be a Player to revive someone."
cannot-revive-while-dead: "&cYou can't revive people when you're dead."
player-not-found: "&cCouldn't find the player %player%"
cannot-revive-self: "&cYou can't revive yourself."
player-not-dead: "&cThis player isn't dead."
need-totems-in-both-hands: "&cYou must hold totems in both hands"
need-more-health: "&cYou need to have 4 health units."
revive-success: "&aYou've revived %player%"
revived-by: "&aYou've been revived by %reviver%"
```

## Required
[ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)

## Download
[Download](https://modrinth.com/plugin/hardcorenecromancy)
