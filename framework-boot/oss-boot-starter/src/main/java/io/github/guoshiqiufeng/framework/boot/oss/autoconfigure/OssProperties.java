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
package io.github.guoshiqiufeng.framework.boot.oss.autoconfigure;

import io.github.guoshiqiufeng.framework.boot.oss.OssSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * 配置文件
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-01-16 16:28
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 类别
	 */
	private Class<? extends OssSource> type;

	/**
	 * 域名
	 */
	private String domain;

	/**
	 * 路径前缀
	 */
	private String prefix;

	/**
	 * ACCESS_KEY
	 */
	private String accessKey;

	/**
	 * SECRET_KEY
	 */
	private String secretKey;

	/**
	 * 存储空间名
	 */
	private String bucketName;

	/**
	 * EndPoint
	 */
	private String endPoint;

	/**
	 * AppId
	 */
	private Integer appId;

	/**
	 * 腾讯云COS所属地区
	 */
	private String region;

	/**
	 * 自定义参数
	 */
	private LinkedCaseInsensitiveMap<Object> args;

}
