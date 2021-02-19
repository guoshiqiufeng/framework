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

/**
 * 统一返回状态码
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-04 14:42
 */
public class BaseResponseCode {

	protected final Integer code;

	protected final String message;

	/**
	 * 未知错误
	 */
	public final static BaseResponseCode UNKNOWN = new BaseResponseCode(-1, "未知错误");

	/**
	 * 请求成功
	 */
	public final static BaseResponseCode SUCCESS = new BaseResponseCode(20000, "成功");

	/**
	 * 请求失败
	 */
	public final static BaseResponseCode FAILURE = new BaseResponseCode(-1, "失败");

	// ------------------------------------------------------- 参数错误：10001-19999 Start

	/**
	 * 参数无效
	 */
	public final static BaseResponseCode PARAM_IS_INVALID = new BaseResponseCode(10001, "参数无效");

	/**
	 * 参数为空
	 */
	public final static BaseResponseCode PARAM_IS_BLANK = new BaseResponseCode(10002, "参数为空");

	/**
	 * 参数类型错误
	 */
	public final static BaseResponseCode PARAM_TYPE_BIND_ERROR = new BaseResponseCode(10003, "参数类型错误");

	/**
	 * 参数缺失
	 */
	public final static BaseResponseCode PARAM_NOT_COMPLETE = new BaseResponseCode(10004, "参数缺失");

	// ------------------------------------------------------- 参数错误：10001-19999 End

	// ------------------------------------------------------- 用户错误：20001-29999 Start

	/**
	 * 用户未登录
	 */
	public final static BaseResponseCode USER_NOT_LOGGED_IN = new BaseResponseCode(20001, "用户未登录");

	/**
	 * 刷新token
	 */
	public final static BaseResponseCode USER_LOGIN_REFRESH_TOKEN = new BaseResponseCode(20002, "刷新token");

	/**
	 * 账号不存在或密码错误
	 */
	public final static BaseResponseCode USER_LOGIN_ERROR = new BaseResponseCode(21002, "账号不存在或密码错误");

	/**
	 * 账号已被禁用
	 */
	public final static BaseResponseCode USER_ACCOUNT_FORBIDDEN = new BaseResponseCode(21003, "账号已被禁用");

	/**
	 * 用户不存在
	 */
	public final static BaseResponseCode USER_NOT_EXIST = new BaseResponseCode(21004, "用户不存在");

	/**
	 * 用户已存在
	 */
	public final static BaseResponseCode USER_HAS_EXISTED = new BaseResponseCode(21005, "用户已存在");

	// ------------------------------------------------------- 用户错误：20001-29999 End

	// ------------------------------------------------------- 业务错误：30001-39999 Start

	/**
	 * 业务系统中用户不存在
	 */
	public final static BaseResponseCode SPECIFIED_QUESTIONED_USER_NOT_EXIST = new BaseResponseCode(30001,
			"业务系统中用户不存在");

	public final static BaseResponseCode ACCOUNT_QUESTIONED_NOT_EXIST = new BaseResponseCode(30001, "账号库用户账号不足，请联系管理员");

	// ------------------------------------------------------- 业务错误：30001-39999 End

	// ------------------------------------------------------- 系统错误：40001-49999 Start

	/**
	 * 系统繁忙，请稍后重试
	 */
	public final static BaseResponseCode SYSTEM_INNER_ERROR = new BaseResponseCode(40001, "系统繁忙，请稍后重试");

	// ------------------------------------------------------- 系统错误：40001-49999 End

	// ------------------------------------------------------- 数据错误：50001-599999 Start

	/**
	 * 数据未找到
	 */
	public final static BaseResponseCode RESULT_DATA_NONE = new BaseResponseCode(50001, "数据未找到");

	/**
	 * 数据有误
	 */
	public final static BaseResponseCode DATA_IS_WRONG = new BaseResponseCode(50002, "数据有误");

	/**
	 * 数据已存在
	 */
	public final static BaseResponseCode DATA_ALREADY_EXISTED = new BaseResponseCode(50003, "数据已存在");

	// ------------------------------------------------------- 数据错误：50001-599999 End

	// ------------------------------------------------------- 接口错误：60001-69999 Start

	/**
	 * 内部系统接口调用异常
	 */
	public final static BaseResponseCode INTERFACE_INNER_INVOKE_ERROR = new BaseResponseCode(60001, "内部系统接口调用异常");

	/**
	 * 外部系统接口调用异常
	 */
	public final static BaseResponseCode INTERFACE_OUTER_INVOKE_ERROR = new BaseResponseCode(60002, "外部系统接口调用异常");

	/**
	 * 该接口禁止访问
	 */
	public final static BaseResponseCode INTERFACE_FORBID_VISIT = new BaseResponseCode(60003, "该接口禁止访问");

	/**
	 * 接口地址无效
	 */
	public final static BaseResponseCode INTERFACE_ADDRESS_INVALID = new BaseResponseCode(60004, "接口地址无效");

	/**
	 * 接口请求超时
	 */
	public final static BaseResponseCode INTERFACE_REQUEST_TIMEOUT = new BaseResponseCode(60005, "接口请求超时");

	/**
	 * 接口负载过高
	 */
	public final static BaseResponseCode INTERFACE_EXCEED_LOAD = new BaseResponseCode(60006, "接口负载过高");

	// ------------------------------------------------------- 接口错误：60001-69999 End

	// ------------------------------------------------------- 权限错误：70001-79999 Start

	/**
	 * 无访问权限
	 */
	public final static BaseResponseCode PERMISSION_NO_ACCESS = new BaseResponseCode(70001, "无访问权限");

	// ------------------------------------------------------- 权限错误：70001-79999 End

	protected BaseResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

}
