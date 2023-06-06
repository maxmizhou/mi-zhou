
package com.mi.zhou.common.core.function;

import java.io.Serializable;

/**
 * 受检的 runnable
 *
 * @author Mi
 */
@FunctionalInterface
public interface CheckedRunnable extends Serializable {

	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;

}
