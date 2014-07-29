bedhome
=======

BedHome is a Bukkit plugin which allows you to teleport to your bed with a simple command: /bed

The plugin also has other (optional) features. You can choose to teleport to a bed that has been destroyed, or you can see it's co-ordinates. Or neither.

It saves player bed co-ordinates and worlds to *beds.yml*. As of me writing this, the plugin is designed for Minecraft 1.7.2, but soon I will create an update which implements UUIDs.

Players have one bed per world.

It also features an auto-updater. Of course, you can disable this in the config by setting "auto-update" to false.


Permissions
--------------

- **bedhome.bed** - this allows players to use the /bed command, if permissions are enabled in the config.

- **bedhome.config** - allows players to reload the config with /bedhome reload
Default config
--------------

    #####Config file for BedHome v2.0 by Superior_Slime#####
    v: 2.0
    ###Boolean values. If these are not set to true or false, you and your players WILL get errors. You have been warned.###
    ##Whether to use permissions or allow all players to do /bed
    permissions: true
    #Should we automatically download and install new updates?
    auto-update: true


    ##What to do if a player has no bed.
    #"nobedmode: a" Allow. Players will be able to teleport to past beds if they have no bed.
    #"nobedmode: b" Block. Players will be given the error message defined below as "nobed"
    #"nobedmode: c" Co-ordinates. Players will be given their past bed's co-ordinates, if they exist.
    nobedmode: c

    