package com.mi.zhou.common.core.utils;

import com.mi.zhou.common.core.constant.Charsets;
import com.mi.zhou.common.core.exception.Exceptions;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * 完全兼容微信所使用的AES加密方式。
 * aes的key必须是256byte长（比如32个字符），可以使用AesKit.genAesKey()来生成一组key
 * <p>
 * 参考自：jFinal AESKit，优化，方便使用
 *
 * @author Mi
 */
public class AesUtil {
	public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

	public static String genAesKey() {
		return StringUtil.random(32);
	}

	/**
	 * 转换成mysql aes
	 *
	 * @param key key
	 * @return SecretKeySpec
	 */
	public static SecretKeySpec genMySqlAesKey(final byte[] key) {
		final byte[] finalKey = new byte[16];
		int i = 0;
		for (byte b : key) {
			finalKey[i++ % 16] ^= b;
		}
		return new SecretKeySpec(finalKey, "AES");
	}

	/**
	 * 转换成mysql aes
	 *
	 * @param key key
	 * @return SecretKeySpec
	 */
	public static SecretKeySpec genMySqlAesKey(final String key) {
		return genMySqlAesKey(key.getBytes(DEFAULT_CHARSET));
	}

	public static String encryptToHex(String content, String aesTextKey) {
		return HexUtil.encodeToString(encrypt(content, aesTextKey));
	}

	public static String encryptToHex(byte[] content, String aesTextKey) {
		return HexUtil.encodeToString(encrypt(content, aesTextKey));
	}

	public static String encryptToBase64(String content, String aesTextKey) {
		return Base64Util.encodeToString(encrypt(content, aesTextKey));
	}

	public static String encryptToBase64(byte[] content, String aesTextKey) {
		return Base64Util.encodeToString(encrypt(content, aesTextKey));
	}

	public static byte[] encrypt(String content, String aesTextKey) {
		return encrypt(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	public static byte[] encrypt(String content, Charset charset, String aesTextKey) {
		return encrypt(content.getBytes(charset), aesTextKey);
	}

	public static byte[] encrypt(byte[] content, String aesTextKey) {
		return encrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}

	@Nullable
	public static String decryptFormHexToString(@Nullable String content, String aesTextKey) {
		byte[] hexBytes = decryptFormHex(content, aesTextKey);
		if (hexBytes == null) {
			return null;
		}
		return new String(hexBytes, DEFAULT_CHARSET);
	}

	@Nullable
	public static byte[] decryptFormHex(@Nullable String content, String aesTextKey) {
		if (StringUtil.isBlank(content)) {
			return null;
		}
		return decryptFormHex(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	public static byte[] decryptFormHex(byte[] content, String aesTextKey) {
		return decrypt(HexUtil.decode(content), aesTextKey);
	}

	@Nullable
	public static String decryptFormBase64ToString(@Nullable String content, String aesTextKey) {
		byte[] hexBytes = decryptFormBase64(content, aesTextKey);
		if (hexBytes == null) {
			return null;
		}
		return new String(hexBytes, DEFAULT_CHARSET);
	}

	@Nullable
	public static byte[] decryptFormBase64(@Nullable String content, String aesTextKey) {
		if (StringUtil.isBlank(content)) {
			return null;
		}
		return decryptFormBase64(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	public static byte[] decryptFormBase64(byte[] content, String aesTextKey) {
		return decrypt(Base64Util.decode(content), aesTextKey);
	}

	public static String decryptToString(byte[] content, String aesTextKey) {
		return new String(decrypt(content, aesTextKey), DEFAULT_CHARSET);
	}

	public static byte[] decrypt(byte[] content, String aesTextKey) {
		return decrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}

	public static byte[] encrypt(byte[] content, byte[] aesKey) {
		return aes(Pkcs7Encoder.encode(content), aesKey, Cipher.ENCRYPT_MODE);
	}

	public static byte[] decrypt(byte[] encrypted, byte[] aesKey) {
		return Pkcs7Encoder.decode(aes(encrypted, aesKey, Cipher.DECRYPT_MODE));
	}

	private static byte[] aes(byte[] encrypted, byte[] aesKey, int mode) {
		Assert.isTrue(aesKey.length == 32, "IllegalAesKey, aesKey's length must be 32");
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(mode, keySpec, iv);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}


	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input  input
	 * @param aesKey aesKey
	 * @return byte array
	 */
	public static byte[] encryptMysql(String input, String aesKey) {
		return encryptMysql(input, aesKey, Function.identity());
	}

	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input  input
	 * @param aesKey aesKey
	 * @param <T>    泛型标记
	 * @return T 泛型对象
	 */
	public static <T> T encryptMysql(String input, String aesKey, Function<byte[], T> mapper) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, genMySqlAesKey(aesKey));
			byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
			return mapper.apply(bytes);
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input  input
	 * @param aesKey aesKey
	 * @return byte 数组
	 */
	public static byte[] decryptMysql(String input, String aesKey) {
		return decryptMysql(input, txt -> txt.getBytes(DEFAULT_CHARSET), aesKey);
	}

	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input  input
	 * @param aesKey aesKey
	 * @return byte 数组
	 */
	public static byte[] decryptMysql(String input, Function<String, byte[]> inputMapper, String aesKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, genMySqlAesKey(aesKey));
			return cipher.doFinal(inputMapper.apply(input));
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input       input
	 * @param inputMapper Function
	 * @param aesKey      aesKey
	 * @return 字符串
	 */
	public static String decryptMysqlToString(String input, Function<String, byte[]> inputMapper, String aesKey) {
		return new String(decryptMysql(input, inputMapper, aesKey), DEFAULT_CHARSET);
	}

	/**
	 * 兼容 mysql 的 aes 加密
	 *
	 * @param input  input
	 * @param aesKey aesKey
	 * @return 字符串
	 */
	public static String decryptMysqlToString(String input, String aesKey) {
		return decryptMysqlToString(input, txt -> txt.getBytes(DEFAULT_CHARSET), aesKey);
	}

}
