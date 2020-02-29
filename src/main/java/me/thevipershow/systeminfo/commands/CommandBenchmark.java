package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

public class CommandBenchmark implements Command {

    private final int THREADS = Runtime.getRuntime().availableProcessors();

    private void createThreads(CommandSender commandSender) {
        int k = 200000000 / THREADS;
        for (int i = 0; i < THREADS; i++) {
            new Calculator(k * i, k * (i + 1), i + 1, commandSender);
        }
    }

    private void message(CommandSender sender) {
        sender.sendMessage(Utils.color("&7Starting calculation ..."));
        sender.sendMessage(Utils.color(String.format("&7Using &a%d THREADS", THREADS)));
        createThreads(sender);
    }


    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("benchmark")) {
            if (args.length == 0) {
                if (sender.hasPermission("systeminfo.commands.cpuload")) {
                    message(sender);
                } else {
                    sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
                }
            } else {
                Messages.OUT_OF_ARGS.value(true);
            }
        }
    }

    final class Calculator implements Runnable {

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
            fillSetRange();
        }

        @Override
        public void run() {
            removeNonPrimes();
            commandSender.sendMessage(Utils.color(String.format("&2» &7THREAD ID: &8[&a%d&8]", id)));
            commandSender.sendMessage(Utils.color(String.format("&7Calculation &a%d-%d &7Finished.", min, max)));
        }
    }
}
