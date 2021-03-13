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
package io.github.guoshiqiufeng.framework.boot.ftp.config;

import io.github.guoshiqiufeng.framework.boot.ftp.FtpSource;
import io.github.guoshiqiufeng.framework.boot.ftp.autoconfigure.FtpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ftp 自动配置
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 13:32
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FtpProperties.class)
@ConditionalOnProperty(prefix = "ftp", name = "enabled", havingValue = "true")
public class FtpConfiguration {

	private final FtpProperties properties;

	@Bean
	@ConditionalOnMissingBean(FtpSource.class)
	public FtpSource ftpSource() throws IllegalAccessException, InstantiationException {
		return new FtpSource();
	}

}
