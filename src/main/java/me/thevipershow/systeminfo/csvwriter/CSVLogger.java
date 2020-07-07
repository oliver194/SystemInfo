package me.thevipershow.systeminfo.csvwriter;

import com.opencsv.CSVWriter;
import me.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import oshi.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public final class CSVLogger {
    private static CSVLogger instance = null;
    private SystemValues systemValues;
    private Plugin plugin;
    private File logFile;
    private String startTime;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    private final static String[] head = {"Cpu_Load", "Used_Memory", "Players", "Entities", "Chunks"};
    private LinkedList<String[]> rows = new LinkedList<>();
    private long[] previousTicks;

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
        startTime = LocalDateTime.now().format(formatter);
        previousTicks = systemValues.getSystemCpuLoadTicks();
        Util.sleep(1000);
        rows.push(new String[]{String.valueOf(systemValues.getSystemCpuLoadBetweenTicks(previousTicks) * 100),
                systemValues.getUsedMemory(),
                systemValues.getUsedMemory2(),
                String.valueOf(Bukkit.getOnlinePlayers().size()),
                String.valueOf(Bukkit.getWorlds().stream().mapToInt(World::getEntityCount).sum()),
                String.valueOf(Bukkit.getWorlds().stream().mapToInt(w -> w.getLoadedChunks().length).sum())});
    }

    public void stopLogging() {
        logFile = new File(plugin.getDataFolder(), "data-" + startTime + ".csv");
        try {
            logFile.createNewFile();
            FileWriter fileWriter = new FileWriter(logFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            rows.forEach(csvWriter::writeNext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
