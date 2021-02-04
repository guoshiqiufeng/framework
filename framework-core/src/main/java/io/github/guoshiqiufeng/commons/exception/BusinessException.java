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

package io.github.guoshiqiufeng.commons.exception;

import io.github.guoshiqiufeng.commons.response.ResponseCode;

import java.io.Serializable;

/**
 * 全局业务异常
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-04 14:40
 */
public class BusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -5007706930421883255L;

	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
		this.code = -1;
	}

	public BusinessException(ResponseCode status) {
		super(status.message());
		this.code = status.code();
	}

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

}
