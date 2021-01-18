package io.github.guoshiqiufeng.framework.boot.oss;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Oss扫描接口 所有实现都要实现此接口
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-18 9:30
 * @email fubluesky@foxmail.com
 */
public interface OssSource {

	/**
	 * 获取http路径前缀
	 * @return http路径前缀
	 */
	default public String getHttpPrefix() {
		return "";
	}

	/**
	 * 文件路径
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回上传路径
	 */
	default public String getPath(String prefix, String suffix) {
		// 生成uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 年月日
		String day = FastDateFormat.getInstance("yyyyMMdd").format(new Date());
		// 文件路径
		String path = day + "/" + uuid;

		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + "/" + path;
		}

		return path + suffix;
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	public abstract String upload(byte[] data, String path);

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(byte[] data, String suffix);

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(byte[] data, String prefix, String suffix);

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	public abstract String upload(InputStream inputStream, String path);

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(InputStream inputStream, String suffix);

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public abstract String uploadSuffix(InputStream inputStream, String prefix, String suffix);

}
