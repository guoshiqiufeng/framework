# oss-ali-boot-starter

#### 介绍
通用快速配置框架

#### 模块

| 名称         | 模块     |
| ------------ | -------- |
| 云文件服务核心配置 | OssConfiguration |

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>io.github.guoshiqiufeng</groupId>
    <artifactId>oss-ali-boot-starter</artifactId>
    <version>1.3.0</version>
</dependency>
```

#### 使用说明

##### **OssConfiguration**

注意：

本模块会根据配置自动装配dataSource,配置文件可通过```application.properties```、```application.yml```配置文件进行配置



```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
oss:
  enabled: true
  type: io.github.guoshiqiufeng.framework.boot.oss.ali.AliOssSource
  bucket-name: bucket
  prefix: prefix
  access-key: access
  secret-key: secret
  domain: http://domain.oss-cn-shenzhen.aliyuncs.com
  end-point: http://oss-cn-shenzhen.aliyuncs.com
```

配置文件可配置参数

| 参数            | 名称             | 备注                                                         |
| --------------- | ---------------- | ------------------------------------------------------------ |
| oss.enabled     | 是否启用自动装配 | 默认为false                                                  |
| oss.type        | oss 类别         | 需要配置为io.github.guoshiqiufeng.framework.boot.oss.ali.AliOssSource |
| oss.domain      | 域名             |                                                              |
| oss.prefix      | 路径前缀         |                                                              |
| oss.access-key  | ACCESS_KEY       |                                                              |
| oss.secret-key  | SECRET_KEY       |                                                              |
| oss.bucket-name | 存储空间名       |                                                              |
| oss.end-point   | 存储空间名       |                                                              |

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

