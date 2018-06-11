package com.duolebo.appbase.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Environment;
import android.os.StatFs;

public class StorageUtils extends Activity {
	
    private static final String TAG = "StorageUtils";
    
    public static final String PREFS_NAME = "com.duolebo.prefs.storage";
    public static final String ROOT ="root";
    public static final String DOWNLOAD = "Download";
    
    
    public static class StorageInfo {
    	public final static int FLASH=0;
    	public final static int INTERNAL_SD_CARD=1;
    	public final static int SD_CARD =2;
    	public final static int U_DISK=3;
    	
        public final String path;
        public final boolean readonly;
        public final boolean removable;     
        public final int number;
        public final int id;
        public final int type;
        
        public StorageInfo(String path, boolean readonly, boolean removable,
				int number, int id,int type ) {
			super();
			this.path = path;
			this.readonly = readonly;
			this.removable = removable;
			this.number = number;
			this.id = id;
			this.type = type;
		}

		public String getDisplayName() {
            StringBuilder res = new StringBuilder();
            switch(type){
            case FLASH:
            	res.append("设备存储");
            	break;
            case INTERNAL_SD_CARD:
            	res.append("内置SD卡");
            	break;
            case SD_CARD:
            	res.append("外置SD卡");
            	break;
            case U_DISK:
            	res.append("U盘");
            }
            /*
            if (!removable) {
                res.append("内置 SD card");
            } else if (number > 1) {
                res.append("U盘 " + number);
            } else {
                res.append("SD card");
            }
            */
            if (readonly) {
                res.append(" (Read only)");
            }
            
            return res.toString();
        }
		public String getTotalSize(){
        	return getHumanSize(StorageUtils.getTotalSize(path));
        }
        public String getAvailSize(){
        	return getHumanSize(StorageUtils.getAvailSize(path));
        }
        public boolean isFlash(){
        	return type == FLASH;
        }
        
    }

    @SuppressLint("NewApi")
	public static List<StorageInfo> getStorageList() {

        List<StorageInfo> list = new ArrayList<StorageInfo>();
        
        String self_path =Environment.getDataDirectory().getPath();
        
        String def_path = Environment.getExternalStorageDirectory().getPath();
        boolean def_path_removable = Environment.isExternalStorageRemovable();
        String def_path_state = Environment.getExternalStorageState();
        boolean def_path_available = def_path_state.equals(Environment.MEDIA_MOUNTED)
                                    || def_path_state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean def_path_readonly = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);

        HashSet<String> paths = new HashSet<String>();
        int cur_removable_number = 1;
        int id =1000;
        //设备存储
    	paths.add(self_path);
    	if(isDirectoryWriteable(new File(self_path))){
    		list.add(0,new StorageInfo(self_path,false,false,0,id++,StorageInfo.FLASH));
    	}
        //
    	if (def_path_available && isDirectoryWriteable(new File(def_path))) {
            paths.add(def_path);
            int number = def_path_removable ? cur_removable_number++ : -1;
            int type = def_path_removable ? StorageInfo.SD_CARD: StorageInfo.INTERNAL_SD_CARD;
            list.add(new StorageInfo(def_path, def_path_readonly, def_path_removable, number,id++,type));
        }

        BufferedReader buf_reader = null;
        try {
            buf_reader = new BufferedReader(new FileReader("/proc/mounts"));
            String line;
            Log.d(TAG, "/proc/mounts");
            while ((line = buf_reader.readLine()) != null) {
                Log.d(TAG, line);
                if (line.contains("vfat") || line.contains("/mnt")) {
                    StringTokenizer tokens = new StringTokenizer(line, " ");
                    String unused = tokens.nextToken(); //device
                    String mount_point = tokens.nextToken(); //mount point
                    if (paths.contains(mount_point)) {
                        continue;
                    }
                    unused = tokens.nextToken(); //file system
                    List<String> flags = Arrays.asList(tokens.nextToken().split(",")); //flags
                    boolean readonly = flags.contains("ro");

                    if (line.contains("/dev/block/vold")) {
                        if (!line.contains("/mnt/secure")
                            && !line.contains("/mnt/asec")
                            && !line.contains("/mnt/obb")
                            && !line.contains("/dev/mapper")
                            && !line.contains("tmpfs")) {
                            paths.add(mount_point);
                            int type = cur_removable_number > 1 ? StorageInfo.U_DISK : StorageInfo.SD_CARD;
                            if(isDirectoryWriteable(new File(mount_point))){
                            	list.add(new StorageInfo(mount_point, readonly, true, cur_removable_number++,id++,type));
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException ex) {}
            }
        }
        return list;
    }

    public static File findWriteableExternalStorageDirectory() {
        File dir;
        dir = Environment.getExternalStorageDirectory();
        if (isDirectoryWriteable(dir)) {
            return dir;
        }
        
        dir = new File("/storage/emulated/legacy");
        if (isDirectoryWriteable(dir)) {
            return dir;
        }
        
        return null;
    }

    public static boolean isDirectoryWriteable(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            String dirStr = dir.getAbsolutePath();
            File check = new File(dirStr + File.separator + ".dlbCheck");
            if (check.exists()) return true;
            if (check.mkdir()) {
                check.delete();
                return true;
            }
        }
        return false;
    }
    
    /**
	 * 计算剩余空间
	 * 
	 * @param path
	 * @return B
	 */
	private static long getAvailSize(String path) {
		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
	}

	/**
	 * 计算总空间
	 * 
	 * @param path
	 * @return B
	 */
	private static long getTotalSize(String path) {
		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getBlockCount() * fileStats.getBlockSize();
	}
	
	/**
	 * 获取SD卡的总空间
	 * 
	 * @return  -1： 未插入SD卡
	 */
	public static long getSDTotalSize() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return getTotalSize(Environment.getExternalStorageDirectory().getPath());
		}

		return -1;
	}
	
	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getSDAvailSize() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return getAvailSize(Environment.getExternalStorageDirectory()
					.getPath());
		}

		return 0;
	}
	/**
	 * 获取系统可读写的总空间
	 * 
	 * @return
	 */
	public static long getDataTotalSize() {
		return getTotalSize(Environment.getDataDirectory().getPath());
	}

	/**
	 * 计算系统的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getDataAvailSize() {
		// context.getFilesDir().getAbsolutePath();
		return getAvailSize(Environment.getDataDirectory().getPath());
	}

	/**
	 * 是否有足够的空间
	 * 
	 * @param filePath
	 *            文件路径，不是目录的路径
	 * @return
	 */
	public static boolean hasEnoughMemory(String filePath) {
		File file = new File(filePath);
		long length = file.length();
		if (filePath.startsWith("/sdcard")
				|| filePath.startsWith("/mnt/sdcard")) {
			return getSDAvailSize() > length;
		} else {
			return getDataAvailSize() > length;
		}

	}
	
    public static boolean isEnoughForDownload(long downloadSize) {

        if(isSdCardWrittenable()){
        	return getSDAvailSize() > downloadSize;
        }else{
        	return getDataAvailSize() > downloadSize;
        }
    }
    
   //SDcard 操作  
  	public static boolean isSdCardWrittenable() {
  	    if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
  		return true;
  	    }
  	    return false;
  	}
	
	public static String getDataUsedHumanSize(){
		return "存储已用：  "+getHumanSize(getDataTotalSize()-getDataAvailSize());
	}
	
	public static String getDataAvailHumanSize(){
		return "存储可用：  "+getHumanSize(getDataAvailSize());
	}
	
	public static String getSDUsedHumanSize(){
		if(getSDTotalSize()>0){
			return "SD卡已用：  "+getHumanSize(getSDTotalSize() - getSDAvailSize());
		}else{
			return "";
		}
	}
	
	public static String getSDAvailHumanSize(){
		if(getSDTotalSize()>0){
			return "SD卡可用：  "+getHumanSize(getSDAvailSize());
		}else{
			return "";
		}
	}
	
	public static String getHumanSize(long size){
		double d = size/1024/1024;
		if(d>1024){
			return String.valueOf(round(d/1024,2)) + " GB";
		}else{
			return ((int)d)+ " MB";
			
		}
	}
	
	public static double round(Double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}