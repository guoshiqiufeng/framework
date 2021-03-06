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
package io.github.guoshiqiufeng.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用数据响应结果
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-04 14:45
 */
@Data
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = -7114120755921784203L;

	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 异常
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String throwable;

	/**
	 * 需要返回的数据对象
	 */
	private Object data;

	public ResponseResult() {
		super();
	}

	/**
	 * 一般的响应结果，不返回数据
	 * @param code {@link BaseResponseCode}
	 */
	public ResponseResult(BaseResponseCode code) {
		super();
		this.code = code.code();
		this.message = code.message();
	}

	/**
	 * 一般的响应结果，不返回数据，自定义消息
	 * @param code {@link BaseResponseCode}
	 * @param message {@code String} 自定义消息
	 */
	public ResponseResult(BaseResponseCode code, String message) {
		super();
		this.code = code.code();
		this.message = message;
	}

	/**
	 * 一般的响应结果，包含数据
	 * @param code {@link BaseResponseCode}
	 * @param data {@code Object}
	 */
	public ResponseResult(BaseResponseCode code, Object data) {
		super();
		this.code = code.code();
		this.message = code.message();
		this.data = data;
	}

	/**
	 * 一般的响应结果，自定义消息并包含数据
	 * @param code {@link BaseResponseCode}
	 * @param message {@link String}
	 * @param data {@code Object}
	 */
	public ResponseResult(BaseResponseCode code, String message, Object data) {
		super();
		this.code = code.code();
		this.message = message;
		this.data = data;
	}

	/**
	 * 异常的响应结果，返回的对象会包含 {@code throwable} 字段
	 * @param code {@link BaseResponseCode}
	 * @param throwable {@link Throwable}
	 */
	public ResponseResult(BaseResponseCode code, Throwable throwable) {
		super();
		this.code = code.code();
		this.message = code.message();
		this.throwable = throwable.getMessage();
	}

	/**
	 * 请求成功
	 * @return this
	 */
	public static ResponseResult success() {
		return new ResponseResult(BaseResponseCode.SUCCESS);
	}

	/**
	 * 请求成功
	 * @param message {@code String} 自定义消息
	 * @return this
	 */
	public static ResponseResult success(String message) {
		return new ResponseResult(BaseResponseCode.SUCCESS, message);
	}

	/**
	 * 请求成功，包含数据部分
	 * @param data {@code Object}
	 * @return this
	 */
	public static ResponseResult success(Object data) {
		return new ResponseResult(BaseResponseCode.SUCCESS, data);
	}

	/**
	 * 请求成功，自定义状态码，包含数据
	 * @param code {@link BaseResponseCode} 自定义状态码
	 * @param data {@code Object}
	 * @return this
	 */
	public static ResponseResult success(BaseResponseCode code, Object data) {
		return new ResponseResult(code, data);
	}

	/**
	 * 请求成功，自定义消息并包含数据
	 * @param message {@link String} 自定义消息
	 * @param data {@code Object}
	 * @return this
	 */
	public static ResponseResult success(String message, Object data) {
		return new ResponseResult(BaseResponseCode.SUCCESS, message, data);
	}

	/**
	 * 请求失败
	 * @return this
	 */
	public static ResponseResult failure() {
		return new ResponseResult(BaseResponseCode.FAILURE);
	}

	/**
	 * 请求失败，自定义消息
	 * @param message {@code String} 消息
	 * @return this
	 */
	public static ResponseResult failure(String message) {
		return new ResponseResult(BaseResponseCode.FAILURE, message);
	}

	/**
	 * 请求失败，自定义状态码
	 * @param code {@link BaseResponseCode} 自定义状态码
	 * @return this
	 */
	public static ResponseResult failure(BaseResponseCode code) {
		return new ResponseResult(code);
	}

	/**
	 * 请求失败，自定义状态码，包含异常消息
	 * @param code {@link BaseResponseCode} 自定义状态码
	 * @param throwable {@link Throwable}
	 * @return this
	 */
	public static ResponseResult failure(BaseResponseCode code, Throwable throwable) {
		return new ResponseResult(code, throwable);
	}

	/**
	 * 请求失败，自定义状态码，包含数据
	 * @param code {@link BaseResponseCode} 自定义状态码
	 * @param data {@code Object}
	 * @return this
	 */
	public static ResponseResult failure(BaseResponseCode code, Object data) {
		return new ResponseResult(code, data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ResponseResult other = (ResponseResult) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		}
		else if (!data.equals(other.data)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		}
		else if (!message.equals(other.message)) {
			return false;
		}
		if (code == null) {
			return other.code == null;
		}
		else {
			return code.equals(other.code);
		}
	}

}
