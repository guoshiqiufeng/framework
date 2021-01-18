package io.github.guoshiqiufeng.framework.boot.oss.exception;

import java.io.Serializable;

/**
 * oss 异常
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-18 10:15
 * @email fubluesky@foxmail.com
 */
public class OssException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 8020660430328063125L;

	public OssException() {
	}

	public OssException(String message) {
		super(message);
	}

	public OssException(String message, Throwable e) {
		super(message, e);
	}

}
