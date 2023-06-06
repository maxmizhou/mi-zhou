package com.mi.zhou.common.core.constant;


/**
 * mi 常量
 *
 * @author Mi
 */
public interface MiConstant {

	/**
	 * Spring 应用名 prop key
	 */
	String SPRING_APP_NAME_KEY = "spring.application.name";

	/**
	 * The "active profiles" property name.
	 */
	String ACTIVE_PROFILES_PROPERTY = "spring.profiles.active";

	/**
	 * mi env key
	 */
	String MI_ENV_KEY = "mi.env";

	/**
	 * 判断是否开发环境的 key
	 */
	String MI_IS_LOCAL_KEY = "mi.is-local";

	/**
	 * mdc request id key
	 */
	String MDC_REQUEST_ID_KEY = "requestId";

	/**
	 * mdc account id key
	 */
	String MDC_ACCOUNT_ID_KEY = "accountId";

	/**
	 * request id key
	 */
	String REQUEST_ID_KEY = "mi_request_id";

	/**
	 * 请求开始时间 key
	 */
	String REQUEST_START_TIME = "mi_request_start_time";

}
