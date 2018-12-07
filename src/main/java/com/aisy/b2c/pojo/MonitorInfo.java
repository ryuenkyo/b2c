package com.aisy.b2c.pojo;

/** 
 * 获得当前的运行信息. 
 * 
 * @author YanqingLiu 
 */ 
public class MonitorInfo {
    /** 可使用内存. */
    private long totalMemory;
    
    /** 剩余内存. */
    private long freeMemory;
    
    /** 最大可使用内存. */
    private long maxMemory;
    
    /** 操作系统. */
    private String osName;
    
    /** 总的物理内存. */
    private long totalMemorySize;
    
    /** 剩余的物理内存. */
    private long freePhysicalMemorySize;
    
    /** 已使用的物理内存. */
    private long usedMemory;
    
    /** 线程总数. */
    private int totalThread;
    
    /** cpu使用率. */
    private double cpuRatio;

    /** 订单总数. */
    private int totalOrderSum;
    
    /** 在线会员数. */
    private int totalOnlineClient;
    
    /** 销售额. */
    private int salesVolume;
    
    
    public int getTotalOrderSum() {
		return totalOrderSum;
	}

	public void setTotalOrderSum(int totalOrderSum) {
		this.totalOrderSum = totalOrderSum;
	}

	public int getTotalOnlineClient() {
		return totalOnlineClient;
	}

	public void setTotalOnlineClient(int totalOnlineClient) {
		this.totalOnlineClient = totalOnlineClient;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getTotalMemorySize() {
        return totalMemorySize;
    }

    public void setTotalMemorySize(long totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }
}
