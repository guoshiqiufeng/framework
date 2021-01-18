# framework

#### 介绍
通用框架

#### 项目源码

|        | 源码地址                                       |      |
| ------ | ---------------------------------------------- | ---- |
| github | https://github.com/guoshiqiufeng/framework.git |      |
| gitee  | https://gitee.com/fubluesky/framework.git      |      |

#### 软件架构
|              | 技术                              | 版本          |
| ------------ | --------------------------------- | ------------- |
| 核心框架     | Spring Boot                       | 2.3.6.RELEASE |

#### 模块

| 名称         | 模块     |  |
| ------------ | -------- | ------ |
| 阿里云oss | oss-ali-boot-starter | |
| 通用依赖         | framework-commons  |    |
| 时间序列化配置         | framework-date  |    |



#### 安装教程
只需要localDate序列化、反序列化
```xml
<dependency>
    <groupId>io.github.guoshiqiufeng</groupId>
    <artifactId>framework-date</artifactId>
    <version>1.1.0</version>
</dependency>
```
还需要使用其他util
```xml
<dependency>
    <groupId>io.github.guoshiqiufeng</groupId>
    <artifactId>framework-core</artifactId>
    <version>1.1.0</version>
</dependency>
```
#### 使用说明

##### **阿里云OSS**

pom添加依赖

```xml
<dependency>
    <groupId>io.github.guoshiqiufeng</groupId>
    <artifactId>oss-ali-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```



application.yml添加配置

```yml
oss:
  enabled: true
  bucket-name: bucket
  prefix: prefix
  access-key: access
  secret-key: secret
  domain: http://domain.oss-cn-shenzhen.aliyuncs.com
  end-point: http://oss-cn-shenzhen.aliyuncs.com
```

Application启动类添加

```java
	@Bean
	public AliOssSource ossSource() {
		return new AliOssSource();
	}
```

需要使用上传的地方

```java
@Autowired
	private OssSource ossSource;

	@PostMapping("")
	public ResponseResult upload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		if (file == null || file.isEmpty()) {
			throw new BusinessException("上传文件不能为空");
		}
		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = ossSource.uploadSuffix(file.getBytes(), "/1", suffix);
		Map<String, String> result = Maps.newHashMap();
		result.put("url", url);
		result.put("prefix", ossSource.getHttpPrefix());
		return ResponseResult.success(result);
	}
```

输出到前端的json

```json
{
    "code": 200,
    "message": "成功",
    "data": {
        "prefix": "http://domain.oss-cn-shenzhen.aliyuncs.com",
        "url": "/prefix/1/20210118/1d9767ccd97743e9a1109eaba15888ce.jpg"
    }
}
```



##### spring boot localDateTime 序列化 反序列化

```java
package io.github.guoshiqiufeng.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.guoshiqiufeng.date.converter.CustomDateArgumentResolverHandler;
import io.github.guoshiqiufeng.date.converter.CustomDateConverter;
import io.github.guoshiqiufeng.date.converter.CustomDateDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;

/**
 * 时间序列化配置
 * @author yanghq
 */
@Configuration
public class CustomDateConfig implements WebMvcConfigurer {

	@Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
	private String pattern;

	@Bean
	public LocalDateTimeSerializer localDateTimeDeserializer() {
		return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
	}

	@Bean
	public LocalDateSerializer localDateDeserializer() {
		return new LocalDateSerializer(DateTimeFormatter.ofPattern(pattern));
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CustomDateArgumentResolverHandler.LocalDateArgumentResolverHandler());
	}

	@Bean
	public Converter<String, LocalDate> localDateConverter() {
		// 此处不能替换为lambda表达式
		return new CustomDateConverter.LocalDateConvert();
	}

	/**
	 * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 不显示为null的字段
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 忽略不能转移的字符
		objectMapper.readerFor(Map.class).withFeatures(ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
		// 过滤对象的null属性.
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 忽略transient
		objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

		objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

		// LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		// LocalDateTime
		javaTimeModule.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)));
		javaTimeModule.addDeserializer(LocalDateTime.class, new CustomDateDeserializer.LocalDateTimeDeserializer());
		// LocalDate
		javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(pattern)));
		javaTimeModule.addDeserializer(LocalDate.class, new CustomDateDeserializer.LocalDateDeserializer());

		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}

}

```




#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
