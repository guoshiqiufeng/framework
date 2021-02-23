/*
 * Copyright 2020-2021 fubluesky.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.guoshiqiufeng.framework.boot.ftp.exception;

import java.io.Serializable;

/**
 * ftp 异常
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 13:15
 */
public class FtpException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 5592169548004496002L;

	public FtpException() {
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(String message, Throwable e) {
		super(message, e);
	}

}
