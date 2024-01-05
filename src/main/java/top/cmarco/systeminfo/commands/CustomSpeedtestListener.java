package top.cmarco.systeminfo.commands;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import org.bukkit.command.CommandSender;
import top.cmarco.systeminfo.utils.Utils;

/**
 * A custom listener for handling speedtest events.
 */
public final class CustomSpeedtestListener implements ISpeedTestListener {
    private final CommandSender sender;
    private final SpeedTestSocket speedTestSocket;

    CustomSpeedtestListener(CommandSender sender, SpeedTestSocket speedTestSocket) {
        this.sender = sender;
        this.speedTestSocket = speedTestSocket;
    }

    @Override
    public void onCompletion(SpeedTestReport speedTestReport) {
        sender.sendMessage(Utils.color("&7» &7Mode: &a" + speedTestReport.getSpeedTestMode().name()));
        sender.sendMessage(Utils.color("&7» &7Result: &a" + Utils.formatData(speedTestReport.getTransferRateOctet().longValue()) + "/s" + " &7or &a" +
                Utils.formatDataBits(speedTestReport.getTransferRateOctet().longValue()) + "/s"));
        speedTestSocket.removeSpeedTestListener(this);
    }

    @Override
    public void onProgress(float v, SpeedTestReport speedTestReport) {
        // Not used in this implementation
    }

    @Override
    public void onError(SpeedTestError speedTestError, String s) {
        sender.sendMessage(Utils.color("&7» &cYour machine network is not configured properly."));
        sender.sendMessage(Utils.color("&7» &cThe plugin was not able to perform a speedtest, contact your host or check your system firewall."));
    }
}
