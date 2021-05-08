/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.common.util;

/**
 * 授权码规则
 * @author Lenovo
 *
 */
public class EncryptUtil {

	/** 将二进制转换成16进制 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/** 将16进制转换为二进制 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 使用异或进行加密
	 * 
	 * @param res
	 *            需要加密的密文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String XORencode(String res, String key) {
		byte[] bs = res.getBytes();
		for (int i = 0; i < bs.length; i++) {
			bs[i] = (byte) ((bs[i]) ^ key.hashCode());
		}
		return parseByte2HexStr(bs);
	}

	/**
	 * 使用异或进行解密
	 * 
	 * @param res
	 *            需要解密的密文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String XORdecode(String res, String key) {
		byte[] bs = parseHexStr2Byte(res);
		for (int i = 0; i < bs.length; i++) {
			bs[i] = (byte) ((bs[i]) ^ key.hashCode());
		}
		return new String(bs);
	}

	public static void main(String[] args) {
		String ur = XORencode("30", "郑州密钥");
		System.out.println(ur);
		System.out.println(XORdecode(ur, "郑州密钥"));
	}
}
