package com.duolebo.appbase.utils;

public class StringTool {

   public static String null2Empty(String inStr)
   {
      return inStr == null ? "" : inStr;
   }
   
   /**
    * Turns an array of bytes into a String representing each byte as an
    * unsigned hex number.
    * <p>
    * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
    * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
    * Distributed under LGPL.
    *
    * @param hash an rray of bytes to convert to a hex-string
    * @return generated hex string
    */
   public static final String toHex (byte hash[]) {
       StringBuffer buf = new StringBuffer(hash.length * 2);
       int i;

       for (i = 0; i < hash.length; i++) {
           if (((int) hash[i] & 0xff) < 0x10) {
               buf.append("0");
           }
           buf.append(Long.toString((int) hash[i] & 0xff, 16));
       }
       return buf.toString().toUpperCase();
   }


   public static final byte[] toByte (String strHex) {
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      
      int posStart = 0;
      int posEnd = 0;
      while (posStart < strHex.length())
      {
          posEnd = posStart + 2;
        String str = strHex.substring(posStart, posEnd);
          baos.write(Integer.parseInt(str, 16)& 0xff);
          posStart = posEnd;

      }
      
      return baos.toByteArray();
   }
}
