<h1 align="center">
  <img src="https://i.imgur.com/yaUBwkV.png" width="256">
  <br>
  BedHome <sup>v2</sup>
  <br>
  <a href="https://dev.bukkit.org/projects/bedhome/files"><img src="https://img.shields.io/github/release/HeroCC/BedHome.svg?label=release" alt="Release"></a> 
  <a href="https://github.com/HeroCC/BedHome/actions/workflows/build.yml?query=branch%3Amaster"><img src="https://img.shields.io/github/actions/workflow/status/HeroCC/BedHome/build.yml?branch=master" alt="Build Status"></a> 
  <a href="https://github.com/HeroCC/BedHome/blob/master/LICENSE.md"><img src="https://img.shields.io/github/license/HeroCC/bedhome.svg" alt="License"></a>
</h1>
<h4 align="center">The simple bed teleport plugin</h4>


<p align="center">
<a href="#commands">Commands</a> | <a href="#permissions">Permissions</a> | <a href="https://github.com/HeroCC/BedHome/issues">Bug tracker</a>
</p>


Teleport to your bed using /bed. Your bed is saved when you sleep in it.


This plugin is compatible with most Bukkit forks supporting UUIDs.


_Feel free to make a pull request or open an issue._




# FEATURES

Teleport to your bed with /bed!

Lookup other player's beds!

Teleport to other player's beds!

If your player's bed has been destroyed, they can still teleport to it, see its co-ordinates or neither, depending on the config.

One bed per world!


# COMMANDS

/bed - Teleport to your bed.

/bedhome [reload/help] - Reload the configuration file. or get help.

/bedhome lookup/tele <player> <world>  - Get the co-ordinates of or teleport to the specified player's bed. Please note usernames are case-sensitive.

# PERMISSIONS

* **bedhome.bed** - Allows players to use /bed

* **bedhome.admin** - Allows players to use all mod commands

* **bedhome.world** - Allows players to /bed to another world (2.2+)
