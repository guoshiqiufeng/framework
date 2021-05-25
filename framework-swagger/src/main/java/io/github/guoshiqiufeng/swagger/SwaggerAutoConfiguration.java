package io.github.guoshiqiufeng.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.github.guoshiqiufeng.core.utils.ListUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author yanghq
 */
@Configuration(proxyBeanMethods = false)
@EnableSwagger2
@Profile({ "!prod" })
@EnableKnife4j
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

	private static final String DEFAULT_MAPPING_PATH = "/";

	private static final String DEFAULT_BASE_PATH = "/**";

	private static final String DEFAULT_EXCLUDE_PATH = "/error";

	@Bean
	@ConditionalOnMissingBean
	public SwaggerProperties swaggerProperties() {
		return new SwaggerProperties();
	}

	@Bean
	public Docket api(SwaggerProperties swaggerProperties) {

		if (ListUtils.isEmpty(swaggerProperties.getBasePath())) {
			swaggerProperties.getBasePath().add(DEFAULT_BASE_PATH);
		}

		if (ListUtils.isEmpty(swaggerProperties.getExcludePath())) {
			swaggerProperties.getExcludePath().add(DEFAULT_EXCLUDE_PATH);
		}

		ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2).host(swaggerProperties.getHost())
				.apiInfo(apiInfo(swaggerProperties)).select()
				.apis(PackageUtil.basePackage(swaggerProperties.getBasePackages()));

		swaggerProperties.getBasePath().forEach(p -> apis.paths(PathSelectors.ant(p)));
		swaggerProperties.getExcludePath().forEach(p -> apis.paths(PathSelectors.ant(p).negate()));

		return apis.build().pathMapping(DEFAULT_MAPPING_PATH);
	}

	private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
		return new ApiInfoBuilder().title(swaggerProperties.getTitle()).license(swaggerProperties.getLicense())
				.licenseUrl(swaggerProperties.getLicenseUrl())
				.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
				.contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(),
						swaggerProperties.getContact().getEmail()))
				.version(swaggerProperties.getVersion()).description(swaggerProperties.getDescription()).build();
	}

}
