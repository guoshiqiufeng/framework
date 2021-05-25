package io.github.guoshiqiufeng.framework.boot.file.utils;

import io.github.guoshiqiufeng.framework.boot.ftp.FtpSource;
import io.github.guoshiqiufeng.framework.boot.oss.OssSource;
import io.github.guoshiqiufeng.framework.boot.oss.ali.AliOssSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件上传
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-02-26 13:43
 */
@Component
public class UploadUtils {

	private static OssSource ossSource;

	private static FtpSource ftpSource;

	private static Boolean ossEnabled;

	@Value("${oss.enabled:false}")
	public void setOssEnabled(Boolean ossEnabled) {
		UploadUtils.ossEnabled = ossEnabled;
	}

	private static Boolean ftpEnabled;

	@Value("${ftp.enabled:false}")
	public void setFtpEnabled(Boolean ftpEnabled) {
		UploadUtils.ftpEnabled = ftpEnabled;
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public static String uploadSuffix(byte[] data, String prefix, String suffix) throws Exception {
		String url = "";
		if (ossEnabled) {
			ossSource = SpringContextUtils.getBean(AliOssSource.class);
			url = ossSource.getHttpPrefix() + ossSource.uploadSuffix(data, prefix, suffix);
		}
		if (ftpEnabled) {
			ftpSource = SpringContextUtils.getBean(FtpSource.class);
			url = ftpSource.getHttpPrefix() + ftpSource.uploadSuffix(data, prefix, suffix);
		}
		return url;
	}

	/**
	 * 获取文件上传访问地址前缀
	 * @return 文件访问地址前缀
	 */
	public static String getHttpPrefix() {
		String httpPrefix = "";
		if (ossEnabled) {
			OssSource ossSource = SpringContextUtils.getBean(AliOssSource.class);
			httpPrefix = ossSource.getHttpPrefix();
		}
		if (ftpEnabled) {
			FtpSource ftpSource = SpringContextUtils.getBean(FtpSource.class);
			httpPrefix = ftpSource.getHttpPrefix();
		}
		return httpPrefix;
	}

}
