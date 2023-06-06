

package com.mi.zhou.common.core.spel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * ExpressionRootObject
 *
 * @author Mi
 */
@Getter
@RequiredArgsConstructor
public class MiExpressionRootObject {
	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;

	private final Method targetMethod;
}
