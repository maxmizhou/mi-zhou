package com.mi.zhou.common.core.context;

import java.util.function.Function;

/**
 * 上下文，默认给个空的
 *
 * @author Mi
 */
public class DefaultMiContext implements IMiContext {

	@Override
	public String getRequestId() {
		return null;
	}

	@Override
	public String getAccountId() {
		return null;
	}

	@Override
	public String get(String ctxKey) {
		return null;
	}

	@Override
	public <T> T get(String ctxKey, Function<String, T> function) {
		return null;
	}

}
