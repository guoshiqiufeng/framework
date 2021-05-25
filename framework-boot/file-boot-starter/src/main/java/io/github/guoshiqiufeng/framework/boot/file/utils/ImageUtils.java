package io.github.guoshiqiufeng.framework.boot.file.utils;

import io.github.guoshiqiufeng.framework.boot.file.constant.FileGlobal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 头像地址工具类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
@Component
public class ImageUtils {

	static String defaultUserIcon = "";

	/**
	 * 获取完整用户头像地址
	 * @param userIcon 用户头像
	 * @return 完整用户头像地址
	 */
	public static String getUserIcon(String userIcon) {
		if (StringUtils.isBlank(userIcon)) {
			return defaultUserIcon;
		}
		// 不为空 并且不是http开头的 添加文件前缀
		if (StringUtils.isNotBlank(userIcon) && userIcon.indexOf(FileGlobal.Network.HTTP) != 0) {
			return UploadUtils.getHttpPrefix() + userIcon;
		}
		return userIcon;
	}

	@Value("${user.default.icon:''}")
	public void setUserIcon(String defaultUserIcon) {
		ImageUtils.defaultUserIcon = defaultUserIcon;
	}

}
