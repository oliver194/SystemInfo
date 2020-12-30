package studio.thevipershow.systeminfo.updater;

import java.util.logging.Logger;
import studio.thevipershow.systeminfo.SystemInfo;
import studio.thevipershow.systeminfo.listeners.JoinNotifyListener;
import org.bukkit.command.CommandSender;

public final class DefaultConsoleUpdateHandler implements PluginUpdater.VersionCheckHandler {

    private final SystemInfo systemInfo;
    private final Logger logger;

    public DefaultConsoleUpdateHandler(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        this.logger = systemInfo.getLogger();
    }

    @Override
    public final void onResult(PluginUpdater.VersionCompareResult compareResult) {
        CommandSender console = systemInfo.getServer().getConsoleSender();
        JoinNotifyListener.notifyPlayerToUpdate(console, compareResult);
    }

    @Override
    public final void onError(Exception e) {
        logger.warning("Something has went wrong when checking for the latest SystemInfo version available!\n\tErrors below:\n");
        e.printStackTrace();
    }
}
