package com.mi.zhou.common.core.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 受检的 Callable
 *
 * @author Mi
 */
@FunctionalInterface
public interface CheckedCallable<T> extends Serializable {

	/**
	 * Run this callable.
	 *
	 * @return result
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T call() throws Throwable;
}
