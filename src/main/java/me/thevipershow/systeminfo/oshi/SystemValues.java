package me.thevipershow.systeminfo.oshi;

import me.thevipershow.systeminfo.utils.Utils;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public final class SystemValues {

    private SystemInfo SYSTEM_INFO;
    private OperatingSystem OPERATING_SYSTEM;
    private HardwareAbstractionLayer HARDWARE_ABSTRACTION_LAYER;
    private CentralProcessor CENTRAL_PROCESSOR;
    private Sensors SENSORS;
    private GlobalMemory MEMORY;
    private CentralProcessor.ProcessorIdentifier PROCESSOR_IDENTIFIER;
    private VirtualMemory VIRTUAL_MEMORY;
    private OperatingSystem.OSVersionInfo OS_VERSION_INFO;

    private SystemValues() {
        updateValues();
    }

    private static final SystemValues instance = new SystemValues();

    public static SystemValues getInstance() {
        return instance;
    }

    public void updateValues() {
        SYSTEM_INFO = new SystemInfo();
        OPERATING_SYSTEM = SYSTEM_INFO.getOperatingSystem();
        HARDWARE_ABSTRACTION_LAYER = SYSTEM_INFO.getHardware();
        CENTRAL_PROCESSOR = HARDWARE_ABSTRACTION_LAYER.getProcessor();
        SENSORS = HARDWARE_ABSTRACTION_LAYER.getSensors();
        MEMORY = HARDWARE_ABSTRACTION_LAYER.getMemory();
        PROCESSOR_IDENTIFIER = CENTRAL_PROCESSOR.getProcessorIdentifier();
        VIRTUAL_MEMORY = MEMORY.getVirtualMemory();
        OS_VERSION_INFO = OPERATING_SYSTEM.getVersionInfo();
    }

    /**
     * @return Returns the formatted value of the system current maximum memory
     */
    public String getMaxMemory() {
        return Utils.formatData(MEMORY.getTotal());
    }

    /**
     * @return Returns the value of the system current maximum memory in bytes.
     */
    public long getMaxMemory2() {
        return MEMORY.getTotal();
    }

    /**
     * @return Returns the formatted value of the system currently available memory.
     */
    public String getAvailableMemory() {
        return Utils.formatData(MEMORY.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system currently used memory.
     */
    public String getUsedMemory() {
        return Utils.formatData(MEMORY.getTotal() - MEMORY.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system current maximum swap memory.
     */
    public String getTotalSwap() {
        return Utils.formatData(VIRTUAL_MEMORY.getSwapTotal());
    }

    /**
     * @return Returns the formatted value of the system currently used swap memory.
     */
    public String getUsedSwap() {
        return Utils.formatData(VIRTUAL_MEMORY.getSwapUsed());
    }

    /**
     * @return Returns the cpu voltage with unit of measure if available, else return "Unavailable".
     */
    public String getCpuVoltage() {
        return SENSORS.getCpuVoltage() != 0 ? SENSORS.getCpuVoltage() + "V" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure if available, else return "Unavailable".
     */
    public String getCpuTemperature() {
        return SENSORS.getCpuTemperature() != 0f ? SENSORS.getCpuTemperature() + "C°" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure and status, else return "Unavailable"
     * 0.0-50.0 is "Idle" status
     * 50.0-75.0 is "Load" status
     * 75.0-90.0 is "Overload" status
     * else is "Not available"
     */
    public String getCpuTemperatureStatus() {
        double degrees = SENSORS.getCpuTemperature();
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
        String rpm = "";
        int[] speeds = SENSORS.getFanSpeeds();
        for (int speed : speeds)
            rpm = rpm.concat(speed + " ");
        return rpm;
    }

    /**
     * @return Get the current operating system family name.
     */
    public String getOSFamily() {
        return OPERATING_SYSTEM.getFamily();
    }

    /**
     * @return Get the current operating system manufacturer name.
     */
    public String getOSManufacturer() {
        return OPERATING_SYSTEM.getManufacturer();
    }

    /**
     * @return Get the current operating system version name.
     */
    public String getOSVersion() {
        return OS_VERSION_INFO.getVersion();
    }

    /**
     * @return Get the name of the current cpu vendor.
     */
    public String getCpuVendor() {
        return PROCESSOR_IDENTIFIER.getVendor();
    }

    /**
     * @return Get the model of the current cpu.
     */
    public String getCpuModel() {
        return PROCESSOR_IDENTIFIER.getModel();
    }

    /**
     * @return Get the model name of the current cpu.
     */
    public String getCpuModelName() {
        return PROCESSOR_IDENTIFIER.getName();
    }

    /**
     * @return Get the max standard frequency of the current CPU with 2 decimals in GHz.
     */
    public String getCpuMaxFrequency() {
        return String.format("%.2f", CENTRAL_PROCESSOR.getMaxFreq() / 1E9F);
    }

    public String getCpuStepping() {
        return PROCESSOR_IDENTIFIER.getStepping();
    }

    /**
     * @return Get the amount of physical cores in the CPU
     */
    public String getCpuCores() {
        return String.valueOf(CENTRAL_PROCESSOR.getPhysicalProcessorCount());
    }

    /**
     * @return Get the amount of logical cores in the CPU
     */
    public String getCpuThreads() {
        return String.valueOf(CENTRAL_PROCESSOR.getLogicalProcessorCount());
    }

    /**
     * @param pids        the number of processes to return
     * @param processSort the type of resource to sort processes by
     * @return returns an array of processes
     */
    public OSProcess[] getOSProcesses(int pids, OperatingSystem.ProcessSort processSort) {
        return OPERATING_SYSTEM.getProcesses(pids, processSort);
    }

    /**
     * @return get the amount of total active processes
     */
    public int getRunningProcesses() {
        return OPERATING_SYSTEM.getProcessCount();
    }

    /**
     * @return get the amount of total threads running
     */
    public int getThreadCount() {
        return OPERATING_SYSTEM.getThreadCount();
    }

    /**
     * @return get an array of HWDiskStore which represents a NVM
     * @see <a href=https://en.wikipedia.org/wiki/Non-volatile_memory>"Wikipedia Non-volatile memory</a>
     */
    public HWDiskStore[] getDiskStores() {
        return HARDWARE_ABSTRACTION_LAYER.getDiskStores();
    }

    /**
     * @return get an array of USB devices attached to the machine
     * @see <a href=https://en.wikipedia.org/wiki/USB>Wikipedia USB</a>
     */
    public UsbDevice[] getUsbDevices() {
        return HARDWARE_ABSTRACTION_LAYER.getUsbDevices(true);
    }

    /**
     * @return Get the system cpu load ticks
     */
    public long[] getSystemCpuLoadTicks() {
        return CENTRAL_PROCESSOR.getSystemCpuLoadTicks();
    }

    /**
     * @return get the processor cpu load ticks
     */
    public long[][] getProcessorCpuLoadTicks() {
        return CENTRAL_PROCESSOR.getProcessorCpuLoadTicks();
    }

    /**
     * @param oldTicks this is the previous value which will be compared to the current
     * @return this will return a double value representing the current average processor load
     */
    public double getSystemCpuLoadBetweenTicks(long[] oldTicks) {
        return CENTRAL_PROCESSOR.getSystemCpuLoadBetweenTicks(oldTicks);
    }

    /**
     * @param oldTicks this is the previous value which will be compared to the current
     * @return returns an array of values which represent a load for each processing unit of the CPU.
     */
    public double[] getProcessorCpuLoadBetweenTicks(long[][] oldTicks) {
        return CENTRAL_PROCESSOR.getProcessorCpuLoadBetweenTicks(oldTicks);
    }
}
