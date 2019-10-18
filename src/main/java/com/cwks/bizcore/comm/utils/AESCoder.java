package com.cwks.bizcore.comm.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created
 */
public class AESCoder {
	// 密钥算法
	public static final String KEY_ALGORITHM = "AES";
	public static final String DEF_PASS = "CSSNJ";

	/**
	 * AES加密字符串
	 * 
	 * @param content
	 *            需要被加密的字符串
	 * @param password
	 *            加密需要的密码
	 * @return 密文
	 */
	public static String encrypt(String content, String password) throws Exception{
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);// 创建AES的Key生产者
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);// 利用用户密码作为随机数初始化出 // 128位的key生产者
		// 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
		SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
		byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);// 转换为AES专用密钥
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 创建密码器
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
		byte[] result = cipher.doFinal(byteContent);// 加密
		return parseByte2HexStr(result);
	}

	/**
	 * 解密AES加密过的字符串
	 * 
	 * @param cont
	 *            AES加密过过的内容
	 * @param password
	 *            加密时的密码
	 * @return 明文
	 */
	public static String decrypt(String cont, String password)  throws Exception{
		byte[] content = parseHexStr2Byte(cont);
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);// 创建AES的Key生产者
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
		byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);// 转换为AES专用密钥
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
		byte[] result = cipher.doFinal(content);
		return new String(result); // 明文
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
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

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
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

	public static void main(String[] args) throws Exception {
		String content = "测试123";
        System.out.println("加密之前：" + content);

        // 加密
        String encrypt = encrypt(content, DEF_PASS);
        System.out.println("加密后的内容：" + encrypt);
        
        // 解密
        String decrypt = decrypt(encrypt, DEF_PASS);
        System.out.println("解密后的内容：" + decrypt);        
	}
}
