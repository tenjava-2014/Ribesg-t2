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

Idea
----

Everyone is a Robot. Because of that, you need Energy to live.
* Your level of Energy is written in a Scoreboard
* As an Admin, you see the level of Energy of everybody
* Killing somebody steals 50% of its energy
* You can recharge yourself using a fuel item on a recharging station
* A recharging station is composed of 2 dispensers
* You need to put fuel in one or both dispensers for the recharging station to be activated
* Recharging station consumes 1 item every X ticks to recharge you
* Recharging stations aren't personal
* A sign placed on a recharging station can output the station content power
* Fuel items are basically coal, but anything else can be (configurable)
* When your power level reaches 0, your food level will lose one level per second until you die

Optional bonus things I may not have the time to do
* Fuel can be used for various things
  * Night vision
  * Fly
  * Speed
  * Power-ups (more attack, more defense)
  * Etc.
* Everything activated make your power level decrease faster
* Portable charging device. Some item hard to craft.
