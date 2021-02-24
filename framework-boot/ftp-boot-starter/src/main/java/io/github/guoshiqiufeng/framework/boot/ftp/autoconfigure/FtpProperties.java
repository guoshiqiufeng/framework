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
package io.github.guoshiqiufeng.framework.boot.ftp.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * 配置文件
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 13:28
 */
@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled;

	/**
	 * ftp外网访问地址前缀
	 */
	private String httpPrefix;

	/**
	 * ftp Ip
	 */
	private String ftpIp;

	/**
	 * ftp Ip
	 */
	private String ftpPort;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 超时时间
	 */
	private String connectionTimeout;

	/**
	 * 是否私有模式
	 */
	private Boolean isPassive;

	/**
	 * 临时目录
	 */
	private String tempDir;

	/**
	 * 路径前缀
	 */
	private String prefix;

	/**
	 * 自定义参数
	 */
	private LinkedCaseInsensitiveMap<Object> args;

}
