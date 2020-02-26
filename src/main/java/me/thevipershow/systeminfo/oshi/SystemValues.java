package me.thevipershow.systeminfo.oshi;

import me.thevipershow.systeminfo.utils.Utils;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public final class SystemValues {

    private static SystemValues instance = null;

    private SystemValues() {
    }

    public static SystemValues getInstance() {
        return instance == null ? new SystemValues() : instance;
    }

    private final SystemInfo systemInfo = new SystemInfo();
    private final OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
    private final HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private final CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
    private final Sensors sensors = hardwareAbstractionLayer.getSensors();
    private final GlobalMemory memory = hardwareAbstractionLayer.getMemory();
    private final CentralProcessor.ProcessorIdentifier cpuIdentifier = centralProcessor.getProcessorIdentifier();
    private final VirtualMemory virtualMemory = memory.getVirtualMemory();
    private final OperatingSystem.OSVersionInfo osVersionInfo = operatingSystem.getVersionInfo();

    public String getMaxMemory() {
        return Utils.formatData(memory.getTotal());
    }

    public long getMaxMemory2() {
        return memory.getTotal();
    }

    public String getAvailableMemory() {
        return Utils.formatData(memory.getAvailable());
    }

    public String getUsedMemory() {
        return Utils.formatData(memory.getTotal() - memory.getAvailable());
    }

    public String getTotalSwap() {
        return Utils.formatData(virtualMemory.getSwapTotal());
    }

    public String getUsedSwap() {
        return Utils.formatData(virtualMemory.getSwapUsed());
    }

    public String getAvailableSwap() {
        return Utils.formatData(virtualMemory.getSwapTotal() - virtualMemory.getSwapUsed());
    }

    public String getCpuVoltage() {
        return sensors.getCpuVoltage() != 0f ? String.valueOf(sensors.getCpuVoltage()) : "Unavailable";
    }

    public String getCpuTemperature() {
        return sensors.getCpuTemperature() != 0f ? String.valueOf(sensors.getCpuTemperature()) : "Unavailable";
    }

    public String getCpuTemperatureStatus() {
        double degrees = sensors.getCpuTemperature();
        if (degrees > 0d) {
            if (degrees <= 50.0d) {
                return (String.format("&a%.1f &2Idle", degrees));
            } else if (degrees <= 75.0d) {
                return (String.format("&6%.1f &6Load", degrees));
            } else {
                return (String.format("&c%.1f &cOverload", degrees));
            }
        } else {
            return "&cNot available";
        }
    }

    public String getFansRPM() {
        String rpm = "";
        int[] speeds = sensors.getFanSpeeds();
        for (int speed : speeds) rpm.concat(speed + " ");
        return rpm;
    }

    public String getOSFamily() {
        return operatingSystem.getFamily();
    }

    public String getOSManufacturer() {
        return operatingSystem.getManufacturer();
    }

    public String getOSVersion() {
        return osVersionInfo.getVersion();
    }

    public String getCpuVendor() {
        return cpuIdentifier.getVendor();
    }

    public String getCpuModel() {
        return cpuIdentifier.getModel();
    }

    public String getCpuModelName() {
        return cpuIdentifier.getName();
    }

    public String getCpuMaxFrequency() {
        return String.format("%.2f", centralProcessor.getMaxFreq()/1E9f);
    }

    public String getCpuStepping() {
        return cpuIdentifier.getStepping();
    }

    public String getCpuCores() {
        return String.valueOf(centralProcessor.getPhysicalProcessorCount());
    }

    public String getCpuThreads() {
        return String.valueOf(centralProcessor.getLogicalProcessorCount());
    }

    public OSProcess[] getOSProcesses(int pids, OperatingSystem.ProcessSort processSort) {
        return operatingSystem.getProcesses(pids, processSort);
    }

    public int getRunningProcesses() {
        return operatingSystem.getProcessCount();
    }

    public int getThreadCount() {
        return operatingSystem.getThreadCount();
    }

    public HWDiskStore[] getDiskStores() {
        return hardwareAbstractionLayer.getDiskStores();
    }

    public UsbDevice[] getUsbDevices() {
        return hardwareAbstractionLayer.getUsbDevices(true);
    }

    public long[] getSystemCpuLoadTicks() {
        return centralProcessor.getSystemCpuLoadTicks();
    }

    public long[][] getProcessorCpuLoadTicks() {
        return centralProcessor.getProcessorCpuLoadTicks();
    }

    public double getSystemCpuLoadBetweenTicks(long[] oldTicks) {
        return centralProcessor.getSystemCpuLoadBetweenTicks(oldTicks);
    }

    public double[] getProcessorCpuLoadBetweenTicks(long[][] oldTicks) {
        return centralProcessor.getProcessorCpuLoadBetweenTicks(oldTicks);
    }

}
