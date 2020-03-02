package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommandBenchmark extends Command {

    public CommandBenchmark() {
        super("benchmark",
                "make a benchmark of your CPU",
                "/<command>",
                new ArrayList<>());
    }

    private final int THREADS = Runtime.getRuntime().availableProcessors();

    private void createThreads(CommandSender commandSender) {
        int k = 175_000_000 / THREADS;
        for (int i = 0; i < THREADS; i++) {
            Calculator calc = new Calculator(k * i, k * (i + 1), i + 1, commandSender);
            calc.start();
        }
    }

    private void message(CommandSender sender) {
        sender.sendMessage(Utils.color("&7Starting calculation ..."));
        sender.sendMessage(Utils.color(String.format("&7Using &a%d THREADS", THREADS)));
        createThreads(sender);
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {

        if (args.length == 0) {
            if (sender.hasPermission("systeminfo.commands.cpuload")) {
                message(sender);
                return true;
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        } else {
            Messages.OUT_OF_ARGS.value(true);
        }

        return false;
    }

    static final class Calculator extends Thread {

        private final int min;
        private final int max;
        private final Set<Integer> numbers = new HashSet<>();
        private final CommandSender commandSender;
        private final int id;

        private void fillSetRange() {
            for (int w = min; w <= max; w += 2) {
                numbers.add(w);
            }
        }

        private void removeNonPrimes() {
            for (int k = 3; k <= Math.sqrt(max); k += 2) {
                for (int j = k * 3, q = 3; j <= max; j = q * k, q += 2) {
                    numbers.remove(j);
                }
            }
        }

        public Calculator(int min, int max, int id, CommandSender commandSender) {
            this.min = min % 2 == 0 ? min + 1 : min;
            this.max = max % 2 == 0 ? max - 1 : max;
            this.commandSender = commandSender;
            this.id = id;
        }

        @Override
        public void run() {
            fillSetRange();
            removeNonPrimes();
            commandSender.sendMessage(Utils.color(String.format("&2» &7THREAD ID: &8[&a%d&8]", id)));
            commandSender.sendMessage(Utils.color(String.format("&7Calculation &a%d-%d &7Finished.", min, max)));
        }
    }
}
