package com.herocc.updater;

import net.gravitydevelopment.updater.Updater;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class NumericUpdater extends Updater {

    public NumericUpdater(Plugin plugin, int id, File file, UpdateType type, boolean announce) {
        super(plugin, id, file, type, null, announce);
    }

    public NumericUpdater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback) {
        super(plugin, id, file, type, callback, false);
    }

    public NumericUpdater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback, boolean announce) {
        super(plugin, id, file, type, callback, announce);
    }

    @Override
    public boolean shouldUpdate(String localVersion, String remoteVersion) {
        try {
            double local = Double.parseDouble(localVersion);
            double remote = Double.parseDouble(remoteVersion);
            return local < remote;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
