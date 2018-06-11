package com.duolebo.appbase.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5sum {
	
	private final static String TAG = MD5sum.class.getName();
   
   public static String md5sum(byte[] src) throws Exception {
      MessageDigest alg = MessageDigest.getInstance("MD5");
      return StringTool.toHex(alg.digest(src));
    }
   public static String md5sum(String fileName)
   {
      return md5sum(new File(fileName));
   }

   public static String md5sum(File file)
   {
      Log.d(TAG, "");
      String md5 = null;
      try {
         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
         byte[] buf = new byte[10240];
         int iCount = 0;
         MessageDigest alg = MessageDigest.getInstance("MD5");
         while ((iCount=bis.read(buf))!=-1)
         {
            alg.update(buf, 0, iCount);
         }
         bis.close();
         byte[] ret = alg.digest();
         md5 = StringTool.toHex(ret);
      } catch (FileNotFoundException e) {
    	  Log.w(TAG, e.toString());
      } catch (NoSuchAlgorithmException e) {
    	  Log.w(TAG, e.toString());
      } catch (IOException e) {
    	  Log.w(TAG, e.toString());
      }
      finally
      {
         Log.d(TAG, "md5=" + md5);
      }
      return md5;
   }
}
