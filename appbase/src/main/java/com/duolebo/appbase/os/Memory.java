package com.duolebo.appbase.os;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.duolebo.appbase.app.AppManager;

import java.io.FileInputStream;
import java.util.List;

public class Memory {
    
    public static enum Type {
        
        MemTotal("MemTotal"),
        MemFree("MemFree"),
        Buffers("Buffers"),
        Cached("Cached"),
        SwapCached("SwapCached"),
        Active("Active"),
        Inactive("Inactive"),
        ActiveAnon("Active(anon)"),
        InactiveAnon("Inactive(anon)"),
        ActiveFile("Active(file)"),
        InactiveFile("Inactive(file)"),
        Unevictable("Unevictable"),
        Mlocked("Mlocked"),
        HighTotal("HighTotal"),
        HighFree("HighFree"),
        LowTotal("LowTotal"),
        LowFree("LowFree"),
        SwapTotal("SwapTotal"),
        SwapFree("SwapFree"),
        Dirty("Dirty"),
        Writeback("Writeback"),
        AnonPages("AnonPages"),
        Mapped("Mapped"),
        Shmem("Shmem"),
        Slab("Slab"),
        SReclaimable("SReclaimable"),
        SUnreclaim("SUnreclaim"),
        KernelStack("KernelStack"),
        PageTables("PageTables"),
        NFS_Unstable("NFS_Unstable"),
        Bounce("Bounce"),
        WritebackTmp("WritebackTmp"),
        CommitLimit("CommitLimit"),
        Committed_AS("Committed_AS"),
        VmallocTotal("VmallocTotal"),
        VmallocUsed("VmallocUsed"),
        VmallocChunk("VmallocChunk"),
        Unknown("Unknown");
        
        private String typeString;

        Type(String typeString) {
            this.typeString = typeString;
        }

        public String toString() {
            return this.typeString;
        }

        public static Type fromString(String typeString) {
            for (Type type : Type.values()) {
                if (type.typeString.equalsIgnoreCase(typeString)) {
                    return type;
                }
            }
            return Unknown;
        }
    }

    public long getSize(Type type) {
        try {
            FileInputStream is = new FileInputStream("/proc/meminfo");
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            is.close();
            for (int i=0; i<len; i++) {
                if (matchText(buffer, i, type.toString())) {
                    i += type.toString().length();
                    return extractMemValue(buffer, i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1l;
    }
    
    public void boost(Context context, List<String> whiteList) {
        List<ApplicationInfo> applications = AppManager.getRunningAppInfo(context);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ApplicationInfo application : applications) {
            // Do not kill system app, as it'd be restarted by system, may
            // even take more memory.
            if (0 != (application.flags & ApplicationInfo.FLAG_SYSTEM))
                continue;

            // Do not kill yourself, you idiot.
            if (application.packageName.equals(context.getPackageName()))
                continue;
            
            // Do not kill those in white list.
            if (hasStringEntry(whiteList, application.packageName))
                continue;
            
            // Let's kill it.
            activityManager.killBackgroundProcesses(application.processName);
        }
    }
    
    private boolean hasStringEntry(List<String> strings, String string) {
        if (null == strings || null == string) return false;
        return strings.contains(string);
    }

    private boolean matchText(byte[] buffer, int index, String text) {
        int N = text.length();
        if ((index+N) >= buffer.length) {
            return false;
        }
        for (int i=0; i<N; i++) {
            if (buffer[index+i] != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private long extractMemValue(byte[] buffer, int index) {
        while (index < buffer.length && buffer[index] != '\n') {
            if (buffer[index] >= '0' && buffer[index] <= '9') {
                int start = index;
                index++;
                while (index < buffer.length && buffer[index] >= '0'
                    && buffer[index] <= '9') {
                    index++;
                }
                String str = new String(buffer, start, index-start);
                return ((long)Integer.parseInt(str)) * 1024;
            }
            index++;
        }
        return 0;
    }
}
