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

    /**
     * Returns the current operating system from OSHI Api.
     *
     * @return the operating system.
     */
    public static OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Returns the current hardware abstraction layer from OSHI Api.
     *
     * @return the hardware abstraction layer.
     */
    public static HardwareAbstractionLayer getHardwareAbstractionLayer() {
        return hardwareAbstractionLayer;
    }

    /**
     * Returns the current central processor from OSHI Api.
     *
     * @return the central processor.
     */
    public static CentralProcessor getCentralProcessor() {
        return centralProcessor;
    }

    /**
     * Returns the current central processor from OSHI Api.
     *
     * @return the sensors.
     */
    public static Sensors getSensors() {
        return sensors;
    }

    /**
     * Returns the global memory from OSHI Api.
     *
     * @return the global memory.
     */
    public static GlobalMemory getMemory() {
        return memory;
    }

    /**
     * Returns an array of the current HWDiskStores from OSHI Api.
     * @return an array of HWDiskStore
     */
    public static HWDiskStore[] getDiskStores() {
        return diskStores;
    }

    /**
     * Returns the computer system from OSHI Api.
     * @return the computer system
     */
    public static ComputerSystem getComputerSystem() {
        return computerSystem;
    }

    /**
     * Returns the processor identifier from OSHI Api.
     * @return returns the processor identifier.
     */
    public static CentralProcessor.ProcessorIdentifier getCpuIdentifier() {
        return cpuIdentifier;
    }
}
