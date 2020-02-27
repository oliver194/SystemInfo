package me.thevipershow.systeminfo.oshi;

import me.thevipershow.systeminfo.utils.Utils;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public final class SystemValues {

    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    private static final OperatingSystem OPERATING_SYSTEM = SYSTEM_INFO.getOperatingSystem();
    private static final HardwareAbstractionLayer HARDWARE_ABSTRACTION_LAYER = SYSTEM_INFO.getHardware();
    private static final CentralProcessor CENTRAL_PROCESSOR = HARDWARE_ABSTRACTION_LAYER.getProcessor();
    private static final Sensors SENSORS = HARDWARE_ABSTRACTION_LAYER.getSensors();
    private static final GlobalMemory MEMORY = HARDWARE_ABSTRACTION_LAYER.getMemory();
    private static final CentralProcessor.ProcessorIdentifier PROCESSOR_IDENTIFIER = CENTRAL_PROCESSOR.getProcessorIdentifier();
    private static final VirtualMemory VIRTUAL_MEMORY = MEMORY.getVirtualMemory();
    private static final OperatingSystem.OSVersionInfo OS_VERSION_INFO = OPERATING_SYSTEM.getVersionInfo();

    private SystemValues() {
    }

    /**
     * @return Returns the formatted value of the system current maximum memory
     */
    public static String getMaxMemory() {
        return Utils.formatData(MEMORY.getTotal());
    }

    /**
     * @return Returns the value of the system current maximum memory in bytes.
     */
    public static long getMaxMemory2() {
        return MEMORY.getTotal();
    }

    /**
     * @return Returns the formatted value of the system currently available memory.
     */
    public static String getAvailableMemory() {
        return Utils.formatData(MEMORY.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system currently used memory.
     */
    public static String getUsedMemory() {
        return Utils.formatData(MEMORY.getTotal() - MEMORY.getAvailable());
    }

    /**
     * @return Returns the formatted value of the system current maximum swap memory.
     */
    public static String getTotalSwap() {
        return Utils.formatData(VIRTUAL_MEMORY.getSwapTotal());
    }

    /**
     * @return Returns the formatted value of the system currently used swap memory.
     */
    public static String getUsedSwap() {
        return Utils.formatData(VIRTUAL_MEMORY.getSwapUsed());
    }

    /**
     * @return Returns the cpu voltage with unit of measure if available, else return "Unavailable".
     */
    public static String getCpuVoltage() {
        return SENSORS.getCpuVoltage() != 0 ? SENSORS.getCpuVoltage() + "V" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure if available, else return "Unavailable".
     */
    public static String getCpuTemperature() {
        return SENSORS.getCpuTemperature() != 0f ? SENSORS.getCpuTemperature() + "C°" : "Unavailable";
    }

    /**
     * @return Returns the cpu temperature with unit of measure and status, else return "Unavailable"
     * 0.0-50.0 is "Idle" status
     * 50.0-75.0 is "Load" status
     * 75.0-90.0 is "Overload" status
     * else is "Not available"
     */
    public static String getCpuTemperatureStatus() {
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
    public static String getFansRPM() {
        String rpm = "";
        int[] speeds = SENSORS.getFanSpeeds();
        for (int speed : speeds)
            rpm = rpm.concat(speed + " ");
        return rpm;
    }

    /**
     * @return Get the current operating system family name.
     */
    public static String getOSFamily() {
        return OPERATING_SYSTEM.getFamily();
    }

    /**
     * @return Get the current operating system manufacturer name.
     */
    public static String getOSManufacturer() {
        return OPERATING_SYSTEM.getManufacturer();
    }

    /**
     * @return Get the current operating system version name.
     */
    public static String getOSVersion() {
        return OS_VERSION_INFO.getVersion();
    }

    /**
     * @return Get the name of the current cpu vendor.
     */
    public static String getCpuVendor() {
        return PROCESSOR_IDENTIFIER.getVendor();
    }

    /**
     * @return Get the model of the current cpu.
     */
    public static String getCpuModel() {
        return PROCESSOR_IDENTIFIER.getModel();
    }

    /**
     * @return Get the model name of the current cpu.
     */
    public static String getCpuModelName() {
        return PROCESSOR_IDENTIFIER.getName();
    }

    /**
     * @return Get the max standard frequency of the current CPU with 2 decimals in GHz.
     */
    public static String getCpuMaxFrequency() {
        return String.format("%.2f", CENTRAL_PROCESSOR.getMaxFreq() / 1E9F);
    }

    public static String getCpuStepping() {
        return PROCESSOR_IDENTIFIER.getStepping();
    }

    /**
     * @return
     */
    public static String getCpuCores() {
        return String.valueOf(CENTRAL_PROCESSOR.getPhysicalProcessorCount());
    }

    /**
     * @return
     */
    public static String getCpuThreads() {
        return String.valueOf(CENTRAL_PROCESSOR.getLogicalProcessorCount());
    }

    /**
     * @param pids
     * @param processSort
     * @return
     */
    public static OSProcess[] getOSProcesses(int pids, OperatingSystem.ProcessSort processSort) {
        return OPERATING_SYSTEM.getProcesses(pids, processSort);
    }

    /**
     * @return
     */
    public static int getRunningProcesses() {
        return OPERATING_SYSTEM.getProcessCount();
    }

    /**
     * @return
     */
    public static int getThreadCount() {
        return OPERATING_SYSTEM.getThreadCount();
    }

    /**
     * @return
     */
    public static HWDiskStore[] getDiskStores() {
        return HARDWARE_ABSTRACTION_LAYER.getDiskStores();
    }

    /**
     * @return
     */
    public static UsbDevice[] getUsbDevices() {
        return HARDWARE_ABSTRACTION_LAYER.getUsbDevices(true);
    }

    /**
     * @return
     */
    public static long[] getSystemCpuLoadTicks() {
        return CENTRAL_PROCESSOR.getSystemCpuLoadTicks();
    }

    /**
     * @return
     */
    public static long[][] getProcessorCpuLoadTicks() {
        return CENTRAL_PROCESSOR.getProcessorCpuLoadTicks();
    }

    /**
     * @param oldTicks
     * @return
     */
    public static double getSystemCpuLoadBetweenTicks(long[] oldTicks) {
        return CENTRAL_PROCESSOR.getSystemCpuLoadBetweenTicks(oldTicks);
    }

    /**
     * @param oldTicks
     * @return
     */
    public static double[] getProcessorCpuLoadBetweenTicks(long[][] oldTicks) {
        return CENTRAL_PROCESSOR.getProcessorCpuLoadBetweenTicks(oldTicks);
    }

}
