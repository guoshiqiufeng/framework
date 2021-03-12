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
package io.github.guoshiqiufeng.framework.boot.push.autoconfigure;

import io.github.guoshiqiufeng.framework.boot.push.PushSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 推送配置文件
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-25 9:25
 */
@Data
@ConfigurationProperties(prefix = "push")
public class PushProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 类别
	 */
	private Class<? extends PushSource> type;

	/**
	 * secret
	 */
	private String secret;

	/**
	 * appKey 设置 groupAppKey 后, appKey会忽略
	 */
	private String appKey;

	/**
	 * groupSecret
	 */
	private String groupSecret;

	/**
	 * groupAppKey 设置 groupAppKey 后, appKey会忽略
	 */
	private String groupAppKey;

}
