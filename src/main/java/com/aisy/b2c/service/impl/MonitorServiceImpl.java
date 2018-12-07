package com.aisy.b2c.service.impl;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.LineNumberReader;  
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;  
import java.lang.management.ManagementFactory;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.dao.OrderHeaderMapper;
import com.aisy.b2c.pojo.MonitorInfo;
import com.aisy.b2c.service.IMonitorService;
import com.aisy.b2c.util.Bytes;
import com.aisy.b2c.util.SYConst;

/** 
 * 获得当前的运行信息. 
 * 
 * @author YanqingLiu 
 */ 
@Service
public class MonitorServiceImpl implements IMonitorService {
	
	@Resource
	OrderHeaderMapper orderHeaderMapper;
	
	@Resource
	CommonCache COMMON_CACHE;
	
	private static final int CPUTIME = 30;  
    private static final int PERCENT = 100;  
    private static final int FAULTLENGTH = 10;  
    private static String linuxVersion = null;  
    
    /** 
     * 获得当前的监控对象. 
     *  
     * @return 返回构造好的监控对象 
     * @throws Exception 
     * @author YanqingLiu 
     */  
    public MonitorInfo getMonitorInfoBean() throws Exception {  
        int mb = 1048576;  
        // 可使用内存  
        long totalMemory = Runtime.getRuntime().totalMemory() / mb;  
        // 剩余内存  
        long freeMemory = Runtime.getRuntime().freeMemory() / mb;  
        // 最大可使用内存  
        long maxMemory = Runtime.getRuntime().maxMemory() / mb;  
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();  
        // 操作系统  
        String osName = System.getProperty("os.name");  
        // 总的物理内存  
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / mb;  
        // 剩余的物理内存  
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / mb;  
        // 已使用的物理内存  
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / mb;  
        // 获得线程总数  
        ThreadGroup parentThread;  
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent());  
        int totalThread = parentThread.activeCount();  
        double cpuRatio = 0;  
        if (osName.toLowerCase().startsWith("windows")) {  
            cpuRatio = this.getCpuRatioForWindows();  
        } else {  
            cpuRatio = getCpuRateForLinux();  
        }  
        //int totalOnlineClient = 
        
        
        // 构造返回对象  
        MonitorInfo infoBean = new MonitorInfo();  
        infoBean.setFreeMemory(freeMemory);  
        infoBean.setFreePhysicalMemorySize(freePhysicalMemorySize);  
        infoBean.setMaxMemory(maxMemory);  
        infoBean.setOsName(osName);  
        infoBean.setTotalMemory(totalMemory);  
        infoBean.setTotalMemorySize(totalMemorySize);  
        infoBean.setTotalThread(totalThread);  
        infoBean.setUsedMemory(usedMemory);  
        infoBean.setCpuRatio(cpuRatio);  
        infoBean.setTotalOrderSum(COMMON_CACHE.MONITORINFO_CACHE.get(SYConst.MONITORINFO.TOTAL_ORDER_SUM));
        infoBean.setSalesVolume(COMMON_CACHE.MONITORINFO_CACHE.get(SYConst.MONITORINFO.SALES_VOLUME));
        return infoBean;  
    }  

    /** 
     * 获得CPU使用率. 
     * Linux
     * @return 返回cpu使用率 
     * @author YanqingLiu 
     */
    private static double getCpuRateForLinux() {  
        InputStream is = null;  
        InputStreamReader isr = null;  
        BufferedReader brStat = null;  
        StringTokenizer tokenStat = null;  
        try {  
            System.out.println(SYConst.MONITORINFO.LINUX_VERSION + linuxVersion);  
            Process process = Runtime.getRuntime().exec(SYConst.MONITORINFO.LINUX_ORDER);  
            is = process.getInputStream();  
            isr = new InputStreamReader(is);  
            brStat = new BufferedReader(isr);  
            if (linuxVersion.equals("2.4")) {  
                brStat.readLine();  
                brStat.readLine();  
                brStat.readLine();  
                brStat.readLine();  
                tokenStat = new StringTokenizer(brStat.readLine());  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                String user = tokenStat.nextToken();  
                tokenStat.nextToken();  
                String system = tokenStat.nextToken();  
                tokenStat.nextToken();  
                String nice = tokenStat.nextToken();  
                System.out.println(user + " , " + system + " , " + nice);  
                user = user.substring(0, user.indexOf(SYConst.SYMBOL.PERCENT));  
                system = system.substring(0, system.indexOf(SYConst.SYMBOL.PERCENT));  
                nice = nice.substring(0, nice.indexOf(SYConst.SYMBOL.PERCENT));  
                float userUsage = new Float(user).floatValue();  
                float systemUsage = new Float(system).floatValue();  
                float niceUsage = new Float(nice).floatValue();  
                return (userUsage + systemUsage + niceUsage) / 100;  
            } else {  
                brStat.readLine();  
                brStat.readLine();  
                tokenStat = new StringTokenizer(brStat.readLine());  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                tokenStat.nextToken();  
                String cpuUsage = tokenStat.nextToken();  
                System.out.println("CPU idle : " + cpuUsage);  
                Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf(SYConst.SYMBOL.PERCENT)));  
                return (1 - usage.floatValue() / 100);  
            }  
        } catch (IOException ioe) {  
            System.out.println(ioe.getMessage());  
            freeResource(is, isr, brStat);  
            return 1;  
        } finally {  
            freeResource(is, isr, brStat);  
        }  
    }  
    private static void freeResource(InputStream is, InputStreamReader isr,  
            BufferedReader br) {  
        try {  
            if (is != null)  
                is.close();  
            if (isr != null)  
                isr.close();  
            if (br != null)  
                br.close();  
        } catch (IOException ioe) {  
            System.out.println(ioe.getMessage());  
        }  
    }  
    
    /** 
     * 获得CPU使用率. 
     * Windows
     * @return 返回cpu使用率 
     * @author YanqingLiu 
     */  
    private double getCpuRatioForWindows() {  
        try {  
            String procCmd = System.getenv(SYConst.MONITORINFO.WIN_DIR) + SYConst.MONITORINFO.WIN_DIR_PATH;  
            // 取进程信息  
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));  
            Thread.sleep(CPUTIME);  
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));  
            if (c0 != null && c1 != null) {  
                long idletime = c1[0] - c0[0];  
                long busytime = c1[1] - c0[1];  
                return Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();  
            } else {  
                return 0.0;  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return 0.0;  
        }  
    }  
    /** 
     * 读取CPU信息. 
     * @param proc 
     * @return 
     * @author YanqingLiu 
     */  
    private long[] readCpu(final Process proc) {  
        long[] retn = new long[2];  
        try {  
            proc.getOutputStream().close();  
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
            String line = input.readLine();  
            if (line == null || line.length() < FAULTLENGTH) {  
                return null;  
            }  
            int capidx = line.indexOf(SYConst.MONITORINFO.CAPTION);  
            int cmdidx = line.indexOf(SYConst.MONITORINFO.COMMANDLINE);  
            int rocidx = line.indexOf(SYConst.MONITORINFO.READOPERATIONCOUNT);  
            int umtidx = line.indexOf(SYConst.MONITORINFO.USERMODETIME);  
            int kmtidx = line.indexOf(SYConst.MONITORINFO.KERNELMODETIME);  
            int wocidx = line.indexOf(SYConst.MONITORINFO.WRITEOPERATIONCOUNT);  
            long idletime = 0;  
            long kneltime = 0;  
            long usertime = 0;  
            while ((line = input.readLine()) != null) {  
                if (line.length() < wocidx) {  
                    continue;  
                }  
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,  
                // ThreadCount,UserModeTime,WriteOperation  
                String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();  
                String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();  
                if (cmd.indexOf(SYConst.MONITORINFO.WMIC_EXE) >= 0) {  
                    continue;  
                }  
                String s1 = Bytes.substring(line, kmtidx, rocidx - 1).trim();  
                String s2 = Bytes.substring(line, umtidx, wocidx - 1).trim();  
                if (caption.equals(SYConst.MONITORINFO.SYSTEM_IDLE_PROCESS) || caption.equals(SYConst.MONITORINFO.SYSTEM)) {  
                    if (s1.length() > 0)  
                        idletime += Long.valueOf(s1).longValue();  
                    if (s2.length() > 0)  
                        idletime += Long.valueOf(s2).longValue();  
                    continue;  
                }  
                if (s1.length() > 0)  
                    kneltime += Long.valueOf(s1).longValue();  
                if (s2.length() > 0)  
                    usertime += Long.valueOf(s2).longValue();  
            }  
            retn[0] = idletime;  
            retn[1] = kneltime + usertime;  
            return retn;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            try {  
                proc.getInputStream().close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
}
