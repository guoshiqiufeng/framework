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
package io.github.guoshiqiufeng.framework.boot.core.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.guoshiqiufeng.framework.boot.core.constants.CoreConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.sql.DataSource;

/**
 * mysql 配置自动注入
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-03-12 10:56
 */
@Data
@Slf4j
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Configuration
@ConfigurationProperties(prefix = "mysql.core")
@PropertySources({ @PropertySource("classpath:mysql-core-jdbc.properties"),
		@PropertySource(value = { "classpath:app.properties" }, ignoreResourceNotFound = true),
		@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true),
		@PropertySource(value = { "classpath:application.yml" }, ignoreResourceNotFound = true) })
@ConditionalOnProperty(prefix = "mysql.core", name = "enabled", havingValue = "true")
public class MysqlCoreConfiguration {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 地址
	 */
	private String jdbcUrl;

	/**
	 * 用户名
	 */
	private String jdbcUserName;

	/**
	 * 密码
	 */
	private String jdbcPassword;

	/**
	 * 反转密码
	 */
	private Boolean reversePassword = false;

	/**
	 * 驱动程序
	 */
	private String jdbcDriver;

	/**
	 * 最大连接数
	 */
	private Integer maximumPoolSize = 50;

	/**
	 * 最小连接数
	 */
	private Integer minimumIdle = 5;

	/**
	 * 一个连接idle状态的最大时长（毫秒），超时则被释放
	 */
	private long idleTimeout = 600000;

	/**
	 * 一个连接的生命时长（毫秒），超时而且没被使用则被释放
	 */
	private long maxLifetime = 1800000;

	/**
	 * 等待连接池分配连接的最大时长（毫秒）
	 */
	private long connectionTimeout = 30000;

	/**
	 * 测试查询sql
	 */
	private String connectionTestQuery = "SELECT 1";

	/**
	 * 设置一个数据库的连接池
	 */
	@Bean("dataSource")
	public DataSource mysqlCoreDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername(this.getJdbcUserName());
		if (this.getReversePassword()) {
			dataSource.setPassword(this.getRealPassword());
		}
		else {
			dataSource.setPassword(this.getJdbcPassword());
		}
		dataSource.setJdbcUrl(this.getJdbcUrl());
		dataSource.setDriverClassName(this.getJdbcDriver());
		// 最大连接数
		dataSource.setMaximumPoolSize(this.getMaximumPoolSize());
		// 最小连接数
		dataSource.setMinimumIdle(this.getMinimumIdle());
		dataSource.setIdleTimeout(this.getIdleTimeout());
		dataSource.setPoolName(CoreConstants.MYSQL_POOL_NAME);
		dataSource.setMaxLifetime(this.getMaxLifetime());
		dataSource.setConnectionTimeout(this.getConnectionTimeout());
		dataSource.setConnectionTestQuery(this.getConnectionTestQuery());
		log.info("dataSource init success !!!");
		return dataSource;
	}

	/**
	 * 密码反转操作
	 */
	public String getRealPassword() {
		String jdbcPassword = this.getJdbcPassword();
		return StringUtils.reverse(jdbcPassword);
	}

}
