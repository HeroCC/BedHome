bedhome
=======

BedHome is a Bukkit plugin which allows you to teleport to your bed with a simple command: /bed

The plugin also has other (optional) features. You can choose to teleport to a bed that has been destroyed, or you can see it's co-ordinates. Or neither.

It saves player bed co-ordinates and worlds to *beds.yml*. As of me writing this, the plugin is designed for Minecraft 1.7.2, but soon I will create an update which implements UUIDs.

It also features an auto-updater. Of course, you can disable this in the config by setting "auto-update" to false.


Permissions
--------------

- **bedhome.bed** - this allows players to use the /bed command, if permissions are enabled in the config.

Default config
--------------

    #####Config file for BedHome v2.0 by Superior_Slime#####

    ###Boolean values. If these are not set to the given values, you and your players WILL get errors. You have been warned.###
    ##Whether to use permissions or allow all players to do /bed
    permissions: true
    ##What to do if a player has no bed.
    #"nobedmode: a" Allow. Players will be able to teleport to past beds if they have no bed.
    #"nobedmode: b" Block. Players will be given the error message defined below as "nobed"
    #"nobedmode: c" Co-ordinates. Players will be given their past bed's co-ordinates, if they exist.
    nobedmode: c
    #Should we automatically download and install new updates?
    auto-update: true

    ###Messages###
    #What is displayed if a player teleports to their bed
    tpmessage: 'You have been teleported to your bed.'
    #What is displayed if a player does not have the permission bedhome.bed
    noperms: 'You do not have permission to do that.'
    #What is displayed if a player has no bed
    nobed: 'You do not have a bed home set, or it has been destroyed.'
    #What is displayed when a player sets their bed
    bedset: 'Your bed home has been set.'
    #The message displayed if someone's bed is destroyed, but they can see the co-ords
    bedcoordmessage: 'Your bed has been destroyed, however, here are its co-ordinates:' 

