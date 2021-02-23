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
package io.github.guoshiqiufeng.framework.boot.ftp.constants;

import lombok.Data;

/**
 * ftp 参数
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 13:10
 */
@Data
public class FtpClientConstants {

	/**
	 * ip地址
	 */
	private String host;

	/**
	 * 端口号
	 */
	private String port;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/***
	 * 盘符
	 */
	private String tempDir;

	/**
	 * 是否开启私有模式
	 */
	private Boolean isPassive;

	/**
	 * 命名空间
	 */
	private String spaceName = "test";

}
