package studio.thevipershow.systeminfo.plugin;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import studio.thevipershow.systeminfo.api.SystemInfoPlaceholderExtension;
import studio.thevipershow.systeminfo.commands.register.CommandManager;
import studio.thevipershow.systeminfo.gui.GuiClickListener;
import studio.thevipershow.systeminfo.gui.SystemInfoGui;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.utils.Utils;

import java.time.LocalDateTime;

public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime sT = LocalDateTime.now();
    private final PluginManager pM = Bukkit.getPluginManager();
    private CommandManager cM;
    private SystemInfoPlaceholderExtension pH;
    private SystemValues sV;
    private BukkitLibraryManager blm;
    private Library lb;
    private SystemInfoGui sig;

    private void loadGui() {
        this.sig = new SystemInfoGui(this);
    }

    private void loadValues() {
        sV = new SystemValues(getLogger());
        sV.updateValues();
    }

    private void loadAPI() {
        if (pM.getPlugin("PlaceholderAPI") != null) {
            pH = new SystemInfoPlaceholderExtension(this);
            pH.register();
        } else {
            this.getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
        }
    }

    private void loadCommands() {
        try {
            cM = new CommandManager(Utils.getCommandMap(), this);
            cM.createInstances();
            cM.registerAll();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().warning("Could not get command map for SystemInfo!");
            this.getLogger().warning("Disabling plugin . . .");
            pM.disablePlugin(this);
        }
    }

    private void registerListener() {
        pM.registerEvents(new GuiClickListener(), this);
    }

    private void loadLibraries() {
        blm = new BukkitLibraryManager(this);
        lb = Library.builder()
                .groupId("com{}github{}oshi") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
                .artifactId("oshi-core")
                .version("6.4.6") // The following are optional
                .id("oshi-core") // Sets an id for the library
                .relocate("com{}github{}oshi", "studio{}thevipershow{}libs") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
                .isolatedLoad(true)
                .build();
        blm.addMavenCentral();
        blm.loadLibrary(lb);
    }

    @Override
    public void onEnable() {
        loadLibraries();
        loadValues();
        loadAPI();
        loadCommands();
        loadGui();
        registerListener();
    }

    public LocalDateTime getsT() {
        return sT;
    }

    public PluginManager getpM() {
        return pM;
    }

    public CommandManager getcM() {
        return cM;
    }

    public SystemInfoPlaceholderExtension getpH() {
        return pH;
    }

    public SystemValues getsV() {
        return sV;
    }

    public BukkitLibraryManager getBlm() {
        return blm;
    }

    public Library getLb() {
        return lb;
    }

    public SystemInfoGui getSig() {
        return sig;
    }
}