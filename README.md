bedhome
=======

BedHome is a Bukkit plugin which allows you to teleport to your bed with a simple command: /bed

The plugin also has other (optional) features. You can choose to teleport to a bed that has been destroyed, or you can see it's co-ordinates. Or neither.

It saves player bed co-ordinates and worlds to *beds.yml*. As of me writing this, the plugin is designed for Minecraft 1.7.2, but soon I will create an update which implements UUIDs.

It also features an update checker, which notifies players both through the console and when you join the server, if you have a permission or are an operator. You can disable this of course, in the config, however it's reccommended you keep it enabled so if we find a bug/have an update with some great new feature you'll be in the know.


Permissions
--------------

- **bedhome.bed** - this allows players to use the /bed command, if permissions are enabled in the config.
- **bedhome.getupdates** - this notifies the player of updates when they join the server, if update checks are enabled.

Default config
--------------

    #####Config file for BedHome v2.0 by Superior_Slime#####
  

    ##Boolean values. If these are not set to true or false, you and your players WILL get errors. You have been warned.##
    
    #Whether to use permissions or allow all players to do /bed
    
    permissions: true
  
    #Whether you can teleport to a destroyed bed
  
    tptodestroyedbed: false
  
    #If tpToDestroyedBed is false, this determines whether the player can see their previous bed's coordinates.
  
    #If tpToDestroyedBed is true, this won't do anything.
  
    displaybedcoords: true
    
    #Whether to notify you of new updates (not auto-downloaded). Note this is reccommended, as if a bug is found or a         crucial new feature is added you'll be in the know. 
  
    updatecheck: true


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

