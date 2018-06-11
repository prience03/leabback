package com.duolebo.appbase.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


public class ThreeDESCode {

	private static String charSet = "utf8";

	/**
	 * 加密
	 * @param src 明文
	 * @param key 密钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String src, String key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes(charSet));
		return byte2Hex(b);
	}

	
	/**
	 * 解密
	 * @param src 密文
	 * @param key 密钥
	 * @return 明文
	 * @throws Exception
	 */
	public static String decryptThreeDESECB(String src, String key) throws Exception {
		byte[] bytesrc = hex2Byte(src);
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(bytesrc);
		return new String(b, charSet);
	}

	
	/**
	 * 二进制转十六进制
	 * @param b  二进制
	 * @return
	 */
	public static String byte2Hex(byte[] b) {
		char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] s = new char[b.length * 2];
		for (int i = 0; i < b.length; i++) {
			s[2 * i] = hex[(b[i] & 0xf0) >> 4];
			s[2 * i + 1] = hex[b[i] & 0xf];
		}
		return new String(s);
	}


	/**
	 * 十六进制转二进制
 	 * @param s 十六进制(String型)
	 * @return
	 */
	public static byte[] hex2Byte(String s) {
		int len = s.length();
		if (len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		for (int i = 0; i < len / 2; i++) {
	int j = 0;
			Integer t;
			char ch1 = s.charAt(i * 2);
			char ch2 = s.charAt(i * 2 + 1);
			if ((ch1 >= 'A') && (ch1 <= 'F'))
				j = (ch1 - 'A' + 10) * 16;
			if ((ch1 >= '0') && (ch1 <= '9'))
				j = (ch1 - '0') * 16;
			if ((ch2 >= 'A') && (ch2 <= 'F'))
				j = j + (ch2 - 'A' + 10);
			if ((ch2 >= '0') && (ch2 <= '9'))
				j = j + (ch2 - '0');
			if ((((ch2 >= 'A') && (ch2 <= 'F')) || ((ch2 >= '0') && (ch2 <= '9'))) != true)
				return null;
			if ((((ch1 >= 'A') && (ch1 <= 'F')) || ((ch1 >= '0') && (ch1 <= '9'))) != true)
				return null;
			t = new Integer(j);
			b[i] = t.byteValue();
		}
		return b;
	}

}