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
package io.github.guoshiqiufeng.commons.response;

/**
 * 统一返回状态码
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-04 14:42
 */
public enum ResponseCode {

	/**
	 * 未知错误
	 */
	UNKNOWN(-1, "未知错误"),
	/**
	 * 请求成功
	 */
	SUCCESS(20000, "成功"),
	/**
	 * 请求失败
	 */
	FAILURE(-1, "失败"),

	// ------------------------------------------------------- 参数错误：10001-19999 Start

	/**
	 * 参数无效
	 */
	PARAM_IS_INVALID(10001, "参数无效"),
	/**
	 * 参数为空
	 */
	PARAM_IS_BLANK(10002, "参数为空"),
	/**
	 * 参数类型错误
	 */
	PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
	/**
	 * 参数缺失
	 */
	PARAM_NOT_COMPLETE(10004, "参数缺失"),

	// ------------------------------------------------------- 参数错误：10001-19999 End

	// ------------------------------------------------------- 用户错误：20001-29999 Start

	/**
	 * 用户未登录
	 */
	USER_NOT_LOGGED_IN(20001, "用户未登录"),
	/**
	 * 刷新token
	 */
	USER_LOGIN_REFRESH_TOKEN(20002, "刷新token"),
	/**
	 * 账号不存在或密码错误
	 */
	USER_LOGIN_ERROR(21002, "账号不存在或密码错误"),
	/**
	 * 账号已被禁用
	 */
	USER_ACCOUNT_FORBIDDEN(21003, "账号已被禁用"),
	/**
	 * 用户不存在
	 */
	USER_NOT_EXIST(21004, "用户不存在"),
	/**
	 * 用户已存在
	 */
	USER_HAS_EXISTED(21005, "用户已存在"),

	/**
	 * 用户未激活
	 */
	USER_UN_ACTIVE(21006, "用户未激活"),

	/**
	 * 手机号
	 */
	MOBILE_HAS_EXISTED(21007, "手机号已存在"),

	USER_PASSWORD_ERROR(21010, "账号原密码错误"),
	/**
	 * 角色
	 */
	ROLE_HAS_EXCEPTION(22000, "角色存在异常"),

	// ------------------------------------------------------- 用户错误：20001-29999 End

	// ------------------------------------------------------- 业务错误：30001-39999 Start

	/**
	 * 业务系统中用户不存在
	 */
	SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "业务系统中用户不存在"),

	ACCOUNT_QUESTIONED_NOT_EXIST(30001, "账号库用户账号不足，请联系管理员"),

	// ------------------------------------------------------- 业务错误：30001-39999 End

	// ------------------------------------------------------- 系统错误：40001-49999 Start

	/**
	 * 系统繁忙，请稍后重试
	 */
	SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

	// ------------------------------------------------------- 系统错误：40001-49999 End

	// ------------------------------------------------------- 数据错误：50001-599999 Start

	/**
	 * 数据未找到
	 */
	RESULT_DATA_NONE(50001, "数据未找到"),
	/**
	 * 数据有误
	 */
	DATA_IS_WRONG(50002, "数据有误"),
	/**
	 * 数据已存在
	 */
	DATA_ALREADY_EXISTED(50003, "数据已存在"),

	// ------------------------------------------------------- 数据错误：50001-599999 End

	// ------------------------------------------------------- 接口错误：60001-69999 Start

	/**
	 * 内部系统接口调用异常
	 */
	INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
	/**
	 * 外部系统接口调用异常
	 */
	INTERFACE_OUTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
	/**
	 * 该接口禁止访问
	 */
	INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
	/**
	 * 接口地址无效
	 */
	INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
	/**
	 * 接口请求超时
	 */
	INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
	/**
	 * 接口负载过高
	 */
	INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

	// ------------------------------------------------------- 接口错误：60001-69999 End

	// ------------------------------------------------------- 权限错误：70001-79999 Start

	/**
	 * 无访问权限
	 */
	PERMISSION_NO_ACCESS(70001, "无访问权限");

	// ------------------------------------------------------- 权限错误：70001-79999 End

	private final Integer code;

	private final String message;

	ResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

	public static String getMessage(String name) {
		for (ResponseCode item : ResponseCode.values()) {
			if (item.name().equals(name)) {
				return item.message;
			}
		}
		return name;
	}

	public static Integer getCode(String name) {
		for (ResponseCode item : ResponseCode.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
