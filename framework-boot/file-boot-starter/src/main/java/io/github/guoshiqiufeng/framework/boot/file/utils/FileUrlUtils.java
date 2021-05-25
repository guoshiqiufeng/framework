package io.github.guoshiqiufeng.framework.boot.file.utils;

import io.github.guoshiqiufeng.framework.boot.file.constant.FileGlobal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文件地址工具类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
@Component
public class FileUrlUtils {

	/**
	 * 获取相对文件地址
	 * @param url
	 * @return {String}
	 */
	public static String getRelativeAddress(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		String httpPrefix = UploadUtils.getHttpPrefix();
		if (httpPrefix == null) {
			return "";
		}
		// 不为空 已当前前缀开头的替换为空
		if (StringUtils.isNotBlank(url) && url.indexOf(httpPrefix) == 0) {
			url = url.replace(httpPrefix, "");
		}
		return url;
	}

	/**
	 * 获取完整文件地址
	 * @param url
	 * @return {String}
	 */
	public static String getFullAddress(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		String httpPrefix = UploadUtils.getHttpPrefix();
		if (httpPrefix == null) {
			return "";
		}
		// 不为空 并且不是http开头的 添加文件前缀
		if (StringUtils.isNotBlank(url) && url.indexOf(FileGlobal.Network.HTTP) != 0) {
			url = httpPrefix + url;
		}
		return url;
	}

}
