package com.mi.zhou.common.core.context;

import org.springframework.lang.Nullable;

import java.util.function.Function;

/**
 * mi 微服务 上下文
 *
 * @author Mi
 */
public interface IMiContext {

	/**
	 * 获取 请求 id
	 *
	 * @return 请求id
	 */
	@Nullable
	String getRequestId();

	/**
	 * 获取租户id
	 *
	 * @return 租户id
	 */
	@Nullable
	default String getTenantId() {
		return null;
	}

	/**
	 * 账号id
	 *
	 * @return 账号id
	 */
	@Nullable
	String getAccountId();

	/**
	 * 微服务版本号，用于灰度
	 *
	 * @return 账号id
	 */
	@Nullable
	default String getVersion() {
		return null;
	}

	/**
	 * 获取上下文中的数据
	 *
	 * @param ctxKey 上下文中的key
	 * @return 返回对象
	 */
	@Nullable
	String get(String ctxKey);

	/**
	 * 获取上下文中的数据
	 *
	 * @param ctxKey   上下文中的key
	 * @param function 函数式
	 * @param <T>      泛型对象
	 * @return 返回对象
	 */
	@Nullable
	<T> T get(String ctxKey, Function<String, T> function);

}
