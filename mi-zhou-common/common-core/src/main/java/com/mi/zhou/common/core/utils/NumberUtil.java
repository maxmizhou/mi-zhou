

package com.mi.zhou.common.core.utils;

import com.mi.zhou.common.core.constant.Charsets;
import org.springframework.lang.Nullable;

/**
 * 数字类型工具类
 *
 * @author Mi
 */
public class NumberUtil extends org.springframework.util.NumberUtils {

	/**
	 * <p>Convert a <code>String</code> to an <code>int</code>, returning
	 * <code>zero</code> if the conversion fails.</p>
	 *
	 * <p>If the string is <code>null</code>, <code>zero</code> is returned.</p>
	 *
	 * <pre>
	 *   NumberUtil.toInt(null) = 0
	 *   NumberUtil.toInt("")   = 0
	 *   NumberUtil.toInt("1")  = 1
	 * </pre>
	 *
	 * @param str the string to convert, may be null
	 * @return the int represented by the string, or <code>zero</code> if
	 * conversion fails
	 */
	public static int toInt(final String str) {
		return ObjectUtil.toInt(str, 0);
	}

	/**
	 * <p>Convert a <code>String</code> to an <code>int</code>, returning a
	 * default value if the conversion fails.</p>
	 *
	 * <p>If the string is <code>null</code>, the default value is returned.</p>
	 *
	 * <pre>
	 *   NumberUtil.toInt(null, 1) = 1
	 *   NumberUtil.toInt("", 1)   = 1
	 *   NumberUtil.toInt("1", 0)  = 1
	 * </pre>
	 *
	 * @param str          the string to convert, may be null
	 * @param defaultValue the default value
	 * @return the int represented by the string, or the default if conversion fails
	 */
	public static int toInt(@Nullable final String str, final int defaultValue) {
		return ObjectUtil.toInt(str, defaultValue);
	}

	/**
	 * <p>Convert a <code>String</code> to a <code>long</code>, returning
	 * <code>zero</code> if the conversion fails.</p>
	 *
	 * <p>If the string is <code>null</code>, <code>zero</code> is returned.</p>
	 *
	 * <pre>
	 *   NumberUtil.toLong(null) = 0L
	 *   NumberUtil.toLong("")   = 0L
	 *   NumberUtil.toLong("1")  = 1L
	 * </pre>
	 *
	 * @param str the string to convert, may be null
	 * @return the long represented by the string, or <code>0</code> if
	 * conversion fails
	 */
	public static long toLong(final String str) {
		return ObjectUtil.toLong(str, 0L);
	}

	/**
	 * <p>Convert a <code>String</code> to a <code>long</code>, returning a
	 * default value if the conversion fails.</p>
	 *
	 * <p>If the string is <code>null</code>, the default value is returned.</p>
	 *
	 * <pre>
	 *   NumberUtil.toLong(null, 1L) = 1L
	 *   NumberUtil.toLong("", 1L)   = 1L
	 *   NumberUtil.toLong("1", 0L)  = 1L
	 * </pre>
	 *
	 * @param str          the string to convert, may be null
	 * @param defaultValue the default value
	 * @return the long represented by the string, or the default if conversion fails
	 */
	public static long toLong(@Nullable final String str, final long defaultValue) {
		return ObjectUtil.toLong(str, defaultValue);
	}

	/**
	 * All possible chars for representing a number as a String
	 */
	static final byte[] DIGITS = {
		'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b',
		'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P', 'Q', 'R',
		'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', '_', '-'
	};

	/**
	 * 将 long 转短字符串 为 62 进制
	 *
	 * @param i 数字
	 * @return 短字符串
	 */
	public static String to62Str(long i) {
		int radix = 62;
		byte[] buf = new byte[65];
		int charPos = 64;
		i = -i;
		while (i <= -radix) {
			buf[charPos--] = DIGITS[(int) (-(i % radix))];
			i = i / radix;
		}
		buf[charPos] = DIGITS[(int) (-i)];
		return new String(buf, charPos, (65 - charPos), Charsets.UTF_8);
	}

	/**
	 * 将 62 进制字符串转为数字
	 *
	 * @param s 字符串
	 * @return 数字
	 */
	public static long form62Str(String s) {
		byte[] bytes = s.getBytes(Charsets.UTF_8);
		byte b;
		int idx;
		long res = 0;
		int len = bytes.length;
		int lenIdx = len - 1;
		for (int i = 0; i < len; i++) {
			b = bytes[i];
			// 将字符转换为对应的数字
			if (b >= 'A' && b <= 'Z') {
				idx = b - 29;
			} else if (b >= 'a' && b <= 'z') {
				idx = b - 87;
			} else {
				idx = b - 48;
			}
			res += idx * Math.pow(62, lenIdx - i);
		}
		return res;
	}

}
