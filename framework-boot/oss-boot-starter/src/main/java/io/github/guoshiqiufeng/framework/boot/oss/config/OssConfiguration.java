package io.github.guoshiqiufeng.framework.boot.oss.config;

import io.github.guoshiqiufeng.framework.boot.oss.OssSource;
import io.github.guoshiqiufeng.framework.boot.oss.autoconfigure.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * oss 自动配置
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-18 10:45
 * @email fubluesky@foxmail.com
 */
@Configuration
@ConditionalOnClass(OssSource.class)
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(prefix = "oss.enabled", value = "true", matchIfMissing = true)
public class OssConfiguration {

}
