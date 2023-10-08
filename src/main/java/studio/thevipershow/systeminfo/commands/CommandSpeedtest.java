package studio.thevipershow.systeminfo.commands;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The `CommandSpeedtest` class is a Spigot command that allows players with the appropriate permission to perform
 * a network speedtest using the "/speedtest" command.
 */
public final class CommandSpeedtest extends SystemInfoCommand {

    private final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

    /**
     * Initializes a new instance of the `CommandSpeedtest` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this command.
     */
    public CommandSpeedtest(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "speedtest",
                "Perform a network speedtest",
                "/<command> <GBs>",
                Collections.emptyList());
    }

    /**
     * A custom listener for handling speedtest events.
     */
    public final static class CustomSpeedtestListener implements ISpeedTestListener {
        private final CommandSender sender;
        private final SpeedTestSocket speedTestSocket;

        private CustomSpeedtestListener(CommandSender sender, SpeedTestSocket speedTestSocket) {
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

    /**
     * Performs a network speedtest and provides results to the sender.
     *
     * @param sender The command sender.
     * @param size   The size of the test file to download (in GBs).
     */
    private void performSpeedtest(CommandSender sender, int size) {
        sender.sendMessage(Utils.color("&2« &7Speedtest &2»"));
        ISpeedTestListener cListener = new CustomSpeedtestListener(sender, speedTestSocket);
        speedTestSocket.addSpeedTestListener(cListener);
        speedTestSocket.startDownload(String.format("https://ovh.net/files/%dGb.dat", size));
    }

    /**
     * A regular expression pattern to match numbers.
     */
    private final static Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    /**
     * Executes the "/speedtest" command with the provided arguments.
     *
     * @param commandSender The command sender.
     * @param s             The command name.
     * @param strings       The command arguments.
     * @return True if the command was executed successfully; otherwise, false.
     */
    @Override
    public boolean execute(CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
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
                    commandSender.sendMessage(Utils.color("&7» &cYou did not use a number as the download size argument!"));
                    return true;
                }
            }
        }
        return false;
    }
}
