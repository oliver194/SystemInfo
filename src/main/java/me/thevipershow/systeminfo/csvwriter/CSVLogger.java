package me.thevipershow.systeminfo.csvwriter;

import com.opencsv.CSVWriter;
import java.time.temporal.TemporalAccessor;
import me.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import oshi.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@SuppressWarnings("ResultOfMethodCallIgnored")
public final class CSVLogger {
    private static CSVLogger instance = null;
    private SystemValues systemValues;
    private Plugin plugin;
    private File logFile;
    private String startTime;
    private final static DateTimeFormatter fileTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private final static DateTimeFormatter csvTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final static String[] head = {"Time", "Cpu_Load", "Used_Memory", "Used_Swap", "Players", "LivingEntities", "Chunks"};
    private final LinkedList<String[]> rows = new LinkedList<>();
    private long[] previousTicks;
    private BukkitTask bukkitTask = null;


    private CSVLogger(SystemValues systemValues, Plugin plugin) {
        this.systemValues = systemValues;
        this.plugin = plugin;
    }

    public static CSVLogger getInstance(SystemValues values, Plugin plugin) {
        if (instance != null) return instance;
        instance = new CSVLogger(values, plugin);
        return instance;
    }

    public void startLogging() {
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            startTime = LocalDateTime.now().format(fileTimeFormatter);
            previousTicks = systemValues.getSystemCpuLoadTicks();
            Util.sleep(1000);
            rows.push(
                    new String[]{
                            csvTimeFormatter.format(LocalDateTime.now()),
                            String.format("%.2f", systemValues.getSystemCpuLoadBetweenTicks(previousTicks) * 100),
                            String.valueOf(systemValues.getMemory().getTotal() - systemValues.getMemory().getAvailable()),
                            String.valueOf(systemValues.getMemory().getVirtualMemory().getSwapUsed()),
                            String.valueOf(Bukkit.getOnlinePlayers().size()),
                            String.valueOf(Bukkit.getWorlds().stream().mapToInt(w -> w.getLivingEntities().size()).sum()),
                            String.valueOf(Bukkit.getWorlds().stream().mapToInt(w -> w.getLoadedChunks().length).sum())
                    }
            );
        }, 1L, 20L * 10L);
    }

    public void stopLogging() {
        bukkitTask.cancel();
        plugin.getDataFolder().mkdirs();
        final File csvLogsFolder = new File(plugin.getDataFolder(), "csv-logs");
        if (!csvLogsFolder.exists()) {
            csvLogsFolder.mkdir();
        }
        final String fileName = "data-" + startTime + ".csv";
        logFile = new File(csvLogsFolder, fileName);
        try {
            logFile.createNewFile();
            FileWriter fileWriter = new FileWriter(logFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            rows.forEach(csvWriter::writeNext);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
