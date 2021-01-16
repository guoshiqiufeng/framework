package io.github.guoshiqiufeng.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerProperties
 *
 * @author yanghq
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

	/**
	 * swagger会解析的包路径
	 **/
	private List<String> basePackages = new ArrayList<>();

	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();

	/**
	 * 排除的url
	 **/
	private List<String> excludePath = new ArrayList<>();

	/**
	 * 标题
	 **/
	private String title = "接口文档系统";

	/**
	 * 描述
	 **/
	private String description = "接口文档系统";

	/**
	 * 版本
	 **/
	private String version = "1.0.0";

	/**
	 * 许可
	 **/
	private String license = "";

	/**
	 * 许可
	 **/
	private String licenseUrl = "";

	/**
	 * 服务条款
	 **/
	private String termsOfServiceUrl = "";

	/**
	 * host
	 **/
	private String host = "";

	/**
	 * 联系人
	 */
	private Contact contact = new Contact();

	@Data
	public static class Contact {

		/**
		 * 姓名
		 */
		private String name = "";

		private String url = "";

		/**
		 * 邮箱
		 */
		private String email = "";

	}

}
