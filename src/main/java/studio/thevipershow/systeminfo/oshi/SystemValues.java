package studio.thevipershow.systeminfo.oshi;

import java.util.List;
import java.util.logging.Logger;
import studio.thevipershow.systeminfo.utils.Utils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.hardware.VirtualMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.UsbDevice;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public final class SystemValues {

    private OperatingSystem operatingSystem;
    private HardwareAbstractionLayer hardwareAbstractionLayer;
    private CentralProcessor centralProcessor;
    private Sensors sensors;
    private GlobalMemory memory;
    private CentralProcessor.ProcessorIdentifier processorIdentifier;
    private VirtualMemory virtualMemory;
    private OperatingSystem.OSVersionInfo osVersionInfo;

    private SystemValues() {
        updateValues();
    }

    private static final SystemValues instance = new SystemValues();

    public static SystemValues getInstance() {
        return instance;
    }

    public void updateValues() {
        Logger logger = studio.thevipershow.systeminfo.SystemInfo.getInstance().getLogger();
        SystemInfo systemInfo;
        try {
            systemInfo = new SystemInfo();
        } catch (Exception e) {
            logger.warning("Could not create System Information instance. Plugin will not work properly!");
            logger.warning(e.getLocalizedMessage());
            return;
        }

        try {
            operatingSystem = systemInfo.getOperatingSystem();
        } catch (SecurityException e) {
            logger.warning("Could not obtain OS info");
            logger.warning(e.getLocalizedMessage());

        }
        try {
            hardwareAbstractionLayer = systemInfo.getHardware();
        } catch (SecurityException e) {
            logger.warning("Could not obtain HAL info");
            logger.warning(e.getLocalizedMessage());

        }

        if (hardwareAbstractionLayer != null) {
            try {
                centralProcessor = hardwareAbstractionLayer.getProcessor();
            } catch (SecurityException e) {
                logger.warning("Could not obtain CPU info");
                logger.warning(e.getLocalizedMessage());

            }
            try {
                sensors = hardwareAbstractionLayer.getSensors();
            } catch (SecurityException e) {
                logger.warning("Could not obtain sensors info");
                logger.warning(e.getLocalizedMessage());

            }
            try {
                memory = hardwareAbstractionLayer.getMemory();
            } catch (SecurityException e) {
                logger.warning("Could not obtain memory info");
                logger.warning(e.getLocalizedMessage());

            }
            try {
                processorIdentifier = centralProcessor.getProcessorIdentifier();
            } catch (SecurityException e) {
                logger.warning("Could not obtain processor identifier");
                logger.warning(e.getLocalizedMessage());

            }
            try {
                virtualMemory = memory.getVirtualMemory();
            } catch (SecurityException e) {
                logger.warning("Could not obtain virtual memory info");
                logger.warning(e.getLocalizedMessage());

            }
            try {
                osVersionInfo = operatingSystem.getVersionInfo();
            } catch (SecurityException e) {
                logger.warning("Could not obtain OS Version info");
                logger.warning(e.getLocalizedMessage());
            }
        }
    }

    /**
     * @return Returns the formatted value of the system current maximum memory
     */
    public String getMaxMemory() {
        return Utils.formatData(memory.getTotal());
    }

    /**
     * @return Returns the value of the system current maximum memory in bytes.
     */
    public long getMaxMemory2() {
        return memory.getTotal();
    }

    /**
     * @return Returns the formatted value of the system currently available memory.
     */
    public String getAvailableMemory() {
        return Utils.formatData(memory.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system currently used memory.
     */
    public String getUsedMemory() {
        return Utils.formatData(memory.getTotal() - memory.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system current maximum swap memory.
     */
    public String getTotalSwap() {
        return Utils.formatData(virtualMemory.getSwapTotal());
    }

    /**
     * @return Returns the formatted value of the system currently used swap memory.
     */
    public String getUsedSwap() {
        return Utils.formatData(virtualMemory.getSwapUsed());
    }

    /**
     * @return Returns the cpu voltage with unit of measure if available, else return "Unavailable".
     */
    public String getCpuVoltage() {
        return sensors.getCpuVoltage() != 0 ? sensors.getCpuVoltage() + "V" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure if available, else return "Unavailable".
     */
    public String getCpuTemperature() {
        return sensors.getCpuTemperature() != 0f ? sensors.getCpuTemperature() + "C°" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure and status, else return "Unavailable"
     * 0.0-50.0 is "Idle" status
     * 50.0-75.0 is "Load" status
     * 75.0-90.0 is "Overload" status
     * else is "Not available"
     */
    public String getCpuTemperatureStatus() {
        double degrees = sensors.getCpuTemperature();
        if (degrees > 0.0) {
            if (degrees <= 50.0) {
                return (String.format("&a%.1fC° &2Idle", degrees));
            } else if (degrees <= 75.0) {
                return (String.format("&6%.1fC° &6Load", degrees));
            } else if (degrees <= 90.0) {
                return (String.format("&c%.1fC° &cOverload", degrees));
            }
        }
        return "&cNot available";
    }

    /**
     * @return Returns fans speed in RPM separated by a blank space.
     */
    public String getFansRPM() {
        StringBuilder rpm = new StringBuilder();
        int[] speeds = sensors.getFanSpeeds();
        for (int speed : speeds)
            rpm.append(speed).append(" ");
        return rpm.toString();
    }

    /**
     * @return Get the current operating system family name.
     */
    public String getOSFamily() {
        return operatingSystem.getFamily();
    }

    /**
     * @return Get the current operating system manufacturer name.
     */
    public String getOSManufacturer() {
        return operatingSystem.getManufacturer();
    }

    /**
     * @return Get the current operating system version name.
     */
    public String getOSVersion() {
        return osVersionInfo.getVersion();
    }

    /**
     * @return Get the name of the current cpu vendor.
     */
    public String getCpuVendor() {
        return processorIdentifier.getVendor();
    }

    /**
     * @return Get the model of the current cpu.
     */
    public String getCpuModel() {
        return processorIdentifier.getModel();
    }

    /**
     * @return Get the model name of the current cpu.
     */
    public String getCpuModelName() {
        return processorIdentifier.getName();
    }

    /**
     * @return Get the max standard frequency of the current CPU with 2 decimals in GHz.
     */
    public String getCpuMaxFrequency() {
        return String.format("%.2f", centralProcessor.getMaxFreq() / 1E9F);
    }

    public String getCpuStepping() {
        return processorIdentifier.getStepping();
    }

    /**
     * @return Get the amount of physical cores in the CPU
     */
    public String getCpuCores() {
        return String.valueOf(centralProcessor.getPhysicalProcessorCount());
    }

    /**
     * @return Get the amount of logical cores in the CPU
     */
    public String getCpuThreads() {
        return String.valueOf(centralProcessor.getLogicalProcessorCount());
    }

    /**
     * @return returns an array of processes
     */
    public List<OSProcess> getOSProcesses() {
        return operatingSystem.getProcesses();
    }

    /**
     * @return get the amount of total active processes
     */
    public int getRunningProcesses() {
        return operatingSystem.getProcessCount();
    }

    /**
     * @return get the amount of total threads running
     */
    public int getThreadCount() {
        return operatingSystem.getThreadCount();
    }

    /**
     * @return get an array of HWDiskStore which represents a NVM
     * @see <a href=https://en.wikipedia.org/wiki/Non-volatile_memory>"Wikipedia Non-volatile memory</a>
     */
    public List<HWDiskStore> getDiskStores() {
        return hardwareAbstractionLayer.getDiskStores();
    }

    /**
     * @return get an array of USB devices attached to the machine
     * @see <a href=https://en.wikipedia.org/wiki/USB>Wikipedia USB</a>
     */
    public List<UsbDevice> getUsbDevices() {
        return hardwareAbstractionLayer.getUsbDevices(true);
    }

    /**
     * @return Get the system cpu load ticks
     */
    public long[] getSystemCpuLoadTicks() {
        return centralProcessor.getSystemCpuLoadTicks();
    }

    /**
     * @return get the processor cpu load ticks
     */
    public long[][] getProcessorCpuLoadTicks() {
        return centralProcessor.getProcessorCpuLoadTicks();
    }

    /**
     * @param oldTicks this is the previous value which will be compared to the current
     * @return this will return a double value representing the current average processor load
     */
    public double getSystemCpuLoadBetweenTicks(long[] oldTicks) {
        return centralProcessor.getSystemCpuLoadBetweenTicks(oldTicks);
    }

    /**
     * @param oldTicks this is the previous value which will be compared to the current
     * @return returns an array of values which represent a load for each processing unit of the CPU.
     */
    public double[] getProcessorCpuLoadBetweenTicks(long[][] oldTicks) {
        return centralProcessor.getProcessorCpuLoadBetweenTicks(oldTicks);
    }
}
