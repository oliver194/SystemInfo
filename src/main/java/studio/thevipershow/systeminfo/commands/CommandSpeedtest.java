package studio.thevipershow.systeminfo.commands;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CommandSpeedtest extends Command {

    private final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

    public CommandSpeedtest() {
        super("speedtest",
                "Perform a network speedtest",
                "/<command> <GBs>",
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
            sender.sendMessage(Utils.color("&7» &7Result: &a" + Utils.formatData(speedTestReport.getTransferRateOctet().longValue()) + "/s" + " &7or &a" +
                    Utils.formatDataBits(speedTestReport.getTransferRateOctet().longValue()) + "/s"));
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

    private void performSpeedtest(CommandSender sender, int size) {
        sender.sendMessage(Utils.color("&2« &7Speedtest &2»"));
        ISpeedTestListener cListener = new CustomSpeedtestListener(sender, speedTestSocket);
        speedTestSocket.addSpeedTestListener(cListener);
        speedTestSocket.startDownload(String.format("http://ovh.net/files/%dGb.dat", size));
    }

    private final static Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    @Override
    public final boolean execute(CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("systeminfo.commands.speedtest")) {
            if (strings.length == 0) {
                performSpeedtest(commandSender, 1);
                return true;
            } else if (strings.length == 1) {
                final String fSize = strings[0];
                final Matcher matcher = NUMBER_PATTERN.matcher(fSize);
                if (matcher.matches()) {
                    final int fSizeNumber = Integer.parseInt(fSize);
                    if (fSizeNumber == 1 || fSizeNumber == 10 || fSizeNumber == 100) {
                        performSpeedtest(commandSender, fSizeNumber);
                    } else {
                        commandSender.sendMessage(Utils.color("&7» &cYou used a file size that is too big or invalid!"));
                        commandSender.sendMessage(Utils.color("  &7The only available file sizes are:"));
                        for (int i = 0; i < 3; i++) {
                            commandSender.sendMessage(Utils.color(String.format("  &7- &c%d&7GB", (int) Math.pow(10, i))));
                        }
                    }
                } else {
                    commandSender.sendMessage(Utils.color("&7» &cYou did not use a number as download size argument!"));
                    return true;
                }
            }
        }
        return false;
    }
}
