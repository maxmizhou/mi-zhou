package com.mi.zhou.common.core.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 受检的 Consumer
 *
 * @author Mi
 */
@FunctionalInterface
public interface CheckedConsumer<T> extends Serializable {

	/**
	 * Run the Consumer
	 *
	 * @param t T
	 * @throws Throwable UncheckedException
	 */
	void accept(@Nullable T t) throws Throwable;

}
