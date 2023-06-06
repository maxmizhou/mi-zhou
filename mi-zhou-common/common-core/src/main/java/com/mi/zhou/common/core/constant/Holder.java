

package com.mi.zhou.common.core.constant;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 一些常用的单利对象
 *
 * @author Mi
 */
public interface Holder {

	/**
	 * RANDOM
	 */
	Random RANDOM = new Random();

	/**
	 * SECURE_RANDOM
	 */
	SecureRandom SECURE_RANDOM = new SecureRandom();

}
