

package com.mi.zhou.common.core.retry;

import com.mi.zhou.common.core.function.CheckedCallable;

/**
 * 重试接口
 *
 * @author Mi
 */
public interface IRetry {

	/**
	 * Execute the supplied {@link CheckedCallable} with the configured retry semantics. See
	 * implementations for configuration details.
	 *
	 * @param <T>           the return value
	 * @param retryCallback the {@link CheckedCallable}
	 * @param <E>           the exception thrown
	 * @return T the return value
	 * @throws E the exception thrown
	 */
	<T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E;

}
