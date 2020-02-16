package me.thevipershow.systeminfo.oshi;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

public class SystemValues {

    private static final SystemInfo systemInfo = new SystemInfo();
    private static final OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
    private static final HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private static final CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
    private static final Sensors sensors = hardwareAbstractionLayer.getSensors();
    private static final GlobalMemory memory = hardwareAbstractionLayer.getMemory();
    private static final HWDiskStore[] diskStores = hardwareAbstractionLayer.getDiskStores();
    private static final ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
    private static final CentralProcessor.ProcessorIdentifier cpuIdentifier = centralProcessor.getProcessorIdentifier();

    public static SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public static OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public static HardwareAbstractionLayer getHardwareAbstractionLayer() {
        return hardwareAbstractionLayer;
    }

    public static CentralProcessor getCentralProcessor() {
        return centralProcessor;
    }

    public static Sensors getSensors() {
        return sensors;
    }

    public static GlobalMemory getMemory() {
        return memory;
    }

    public static HWDiskStore[] getDiskStores() {
        return diskStores;
    }

    public static ComputerSystem getComputerSystem() {
        return computerSystem;
    }

    public static CentralProcessor.ProcessorIdentifier getCpuIdentifier() {
        return cpuIdentifier;
    }
}
