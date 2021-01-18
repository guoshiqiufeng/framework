package io.github.guoshiqiufeng.framework.boot.oss.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * 配置文件
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-16 16:28
 * @email fubluesky@foxmail.com
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled;

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
