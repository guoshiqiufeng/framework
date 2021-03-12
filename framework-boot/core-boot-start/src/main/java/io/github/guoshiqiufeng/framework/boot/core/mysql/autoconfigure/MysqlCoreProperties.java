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
package io.github.guoshiqiufeng.framework.boot.core.mysql.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * mysql 配置参数
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-03-12 13:27
 */
@Data
@ConfigurationProperties(prefix = "mysql.core")
@PropertySources({ @PropertySource("classpath:mysql-core-jdbc.properties"),
		@PropertySource(value = "classpath:app.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true) })
public class MysqlCoreProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 地址
	 */
	String jdbcUrl;

	/**
	 * 用户名
	 */
	String jdbcUserName;

	/**
	 * 密码
	 */
	String jdbcPassword;

	/**
	 * 反转密码
	 */
	Boolean reversePassword = false;

	/**
	 * 驱动程序
	 */
	String jdbcDriver;

	/**
	 * 最大连接数
	 */
	Integer maximumPoolSize = 50;

	/**
	 * 最小连接数
	 */
	Integer minimumIdle = 5;

	/**
	 * 个连接idle状态的最大时长（毫秒），超时则被释放
	 */
	long idleTimeout = 600000;

	/**
	 * 一个连接的生命时长（毫秒），超时而且没被使用则被释放
	 */
	long maxLifetime = 1800000;

	/**
	 * 等待连接池分配连接的最大时长（毫秒）
	 */
	long connectionTimeout = 30000;

	/**
	 * 测试查询sql
	 */
	String connectionTestQuery = "SELECT 1";

}
