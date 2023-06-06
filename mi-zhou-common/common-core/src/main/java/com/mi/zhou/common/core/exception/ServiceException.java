package com.mi.zhou.common.core.exception;

import com.mi.zhou.common.core.result.IResultCode;
import com.mi.zhou.common.core.result.R;
import org.springframework.lang.Nullable;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author Mi
 */
public class ServiceException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 2359767895161832954L;

	@Nullable
	private final R<?> result;

	public ServiceException(R<?> result) {
		super(result.getMsg());
		this.result = result;
	}

	public ServiceException(IResultCode rCode) {
		this(rCode, rCode.getMsg());
	}

	public ServiceException(IResultCode rCode, String message) {
		super(message);
		this.result = R.fail(rCode, message);
	}

	public ServiceException(String message) {
		super(message);
		this.result = null;
	}

	public ServiceException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		doFillInStackTrace();
		this.result = null;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T> R<T> getResult() {
		return (R<T>) result;
	}

	/**
	 * 提高性能
	 * @return Throwable
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}
