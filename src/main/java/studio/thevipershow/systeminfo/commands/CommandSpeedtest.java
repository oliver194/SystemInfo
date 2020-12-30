package studio.thevipershow.systeminfo.commands;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import java.util.Collections;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CommandSpeedtest extends Command {

    private final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

    public CommandSpeedtest() {
        super("speedtest",
                "Perform a network speedtest",
                "/<command>",
                Collections.emptyList());
    }

    public final static class CustomSpeedtestListener implements ISpeedTestListener {
        private final CommandSender sender;
        private final SpeedTestSocket speedTestSocket;

        public CustomSpeedtestListener(CommandSender sender, SpeedTestSocket speedTestSocket) {
            this.sender = sender;
            this.speedTestSocket = speedTestSocket;
        }

        @Override
        public final void onCompletion(SpeedTestReport speedTestReport) {
            sender.sendMessage(Utils.color("&7» &7Mode: &a" + speedTestReport.getSpeedTestMode().name()));
            sender.sendMessage(Utils.color("&7» &7Result: &a" + Utils.formatData(speedTestReport.getTransferRateOctet().longValue()) + "/s"));
            speedTestSocket.removeSpeedTestListener(this);
        }

        @Override
        public final void onProgress(float v, SpeedTestReport speedTestReport) {

        }

        @Override
        public final void onError(SpeedTestError speedTestError, String s) {
            sender.sendMessage(Utils.color("&7» &cYour machine network is not configured properly."));
            sender.sendMessage(Utils.color("&7» &cThe plugin was not able to perform a speedtest, contact your host or check your system firewall."));
        }
    }

    private void performSpeedtest(CommandSender sender) {
        sender.sendMessage(Utils.color("&2« &7Speedtest &2»"));
        ISpeedTestListener cListener = new CustomSpeedtestListener(sender, speedTestSocket);
        speedTestSocket.addSpeedTestListener(cListener);
        speedTestSocket.startFixedDownload("http://ipv4.ikoula.testdebit.info/100M.iso", 10_000);
    }

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission("systeminfo.commands.speedtest")) {
            if (strings.length == 0) {
                performSpeedtest(commandSender);
                return true;
            }
        }
        return false;
    }
}
