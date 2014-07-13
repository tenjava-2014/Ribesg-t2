Ribesg's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ Surviving with an energy dependency - Robot edition
- __Time:__ Time 2 (7/12/2014 09:00 to 7/12/2014 19:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ https://twitch.tv/Ribesg

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/Ribesg-t2`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

Usage
-----

1. Install plugin
2. Do things with it

Explanation
----

Everyone is a Robot. Everyone starts with a configurable amount of Power.
* Your level of Energy is written in a Scoreboard
* As an Admin, you see the level of Energy of everybody
* Killing somebody steals 50% of its energy
* You can recharge yourself using a fuel item on a recharging station
* A recharging station is composed of 2 dispensers, like this:
  * D <-- Oriented downside
  * A <-- This is air
  * A <-- This is also air
  * D <-- Oriented upside
* You need to put fuel in one or both dispensers for the recharging station to be activated
* Recharging station consumes 1 item every 5 (configurable) ticks to recharge you
* Recharging stations aren't personal
* A sign placed on a recharging station can output the station content power
* Fuel items are basically coal, but anything else can be (configurable)
* When your power level reaches 0, your food level will lose one level per second, then your health will decrease by one per second until you die
* There are perks you can use, they consume Power: Attack, Defense, Fly. Use the /perk command
* Admin command /setpower allows you to modify anybody's power level