package studio.thevipershow.systeminfo.updater;

import studio.thevipershow.systeminfo.SystemInfo;

public final class NotifyDataUpdateHandler implements PluginUpdater.VersionCheckHandler {
    private final SystemInfo plugin;

    public NotifyDataUpdateHandler(SystemInfo plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onResult(PluginUpdater.VersionCompareResult compareResult) {
        if (compareResult != null && compareResult.isOutdated()) {
            plugin.getJoinNotifyListener().notifiedPlayers.clear();
        }
    }

    @Override
    public final void onError(Exception e) {
        plugin.getLogger().warning("Something has went wrong when checking for the latest SystemInfo version available!\n\tErrors below:\n");
        e.printStackTrace();
    }
}
