

package com.mi.zhou.common.core.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 受检的 Supplier
 *
 * @author Mi
 */
@FunctionalInterface
public interface CheckedSupplier<T> extends Serializable {

	/**
	 * Run the Supplier
	 *
	 * @return T
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T get() throws Throwable;

}
