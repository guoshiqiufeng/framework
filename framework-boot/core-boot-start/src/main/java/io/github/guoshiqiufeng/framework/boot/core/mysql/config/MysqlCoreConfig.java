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
import io.github.guoshiqiufeng.framework.boot.core.mysql.autoconfigure.MysqlCoreProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * mysql 配置自动注入
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-03-12 10:56
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MysqlCoreProperties.class)
@ConditionalOnProperty(prefix = "mysql", name = "enabled", havingValue = "true")
public class MysqlCoreConfig {

	private final MysqlCoreProperties properties;

	/**
	 * 设置一个数据库的连接池
	 */
	@Bean("mysqlCoreDataSource")
	public DataSource mysqlCoreDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername(properties.getJdbcUserName());
		if (properties.getReversePassword()) {
			dataSource.setPassword(this.getRealPassword());
		}
		else {
			dataSource.setPassword(properties.getJdbcPassword());
		}
		dataSource.setJdbcUrl(properties.getJdbcUrl());
		dataSource.setDriverClassName(properties.getJdbcDriver());
		// 最大连接数
		dataSource.setMaximumPoolSize(properties.getMaximumPoolSize());
		// 最小连接数
		dataSource.setMinimumIdle(properties.getMinimumIdle());
		dataSource.setIdleTimeout(properties.getIdleTimeout());
		dataSource.setPoolName(CoreConstants.MYSQL_POOL_NAME);
		dataSource.setMaxLifetime(properties.getMaxLifetime());
		dataSource.setConnectionTimeout(properties.getConnectionTimeout());
		dataSource.setConnectionTestQuery(properties.getConnectionTestQuery());
		return dataSource;
	}

	/**
	 * 密码反转操作
	 */
	public String getRealPassword() {
		String jdbcPassword = properties.getJdbcPassword();
		return StringUtils.reverse(jdbcPassword);
	}

}
