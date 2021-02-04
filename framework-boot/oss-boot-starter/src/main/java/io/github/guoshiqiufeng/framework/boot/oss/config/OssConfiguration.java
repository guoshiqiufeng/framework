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
package io.github.guoshiqiufeng.framework.boot.oss.config;

import io.github.guoshiqiufeng.framework.boot.oss.OssSource;
import io.github.guoshiqiufeng.framework.boot.oss.autoconfigure.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oss 自动配置
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-01-18 10:45
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(prefix = "oss.enabled", value = "true", matchIfMissing = true)
public class OssConfiguration {

	private final OssProperties properties;

	@Bean
	@ConditionalOnMissingBean(OssSource.class)
	public OssSource ossSource() throws IllegalAccessException, InstantiationException {
		if (properties.getType() == null) {
			throw new RuntimeException("oss init fail, type is null !");
		}
		return properties.getType().newInstance();
	}

}
