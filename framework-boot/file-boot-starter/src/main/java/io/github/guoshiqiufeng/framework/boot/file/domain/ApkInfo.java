package io.github.guoshiqiufeng.framework.boot.file.domain;

import lombok.Data;

import java.util.List;

/**
 * apk实体（获取apk信息使用）
 */
@SuppressWarnings("all")
@Data
public class ApkInfo {

	private String versionCode;

	private String versionName;

	private String apkPackage;

	private String minSdkVersion;

	private String apkName;

	private List uses_permission;

	private String url;

	public ApkInfo() {
		versionCode = null;
		versionName = null;
		apkPackage = null;
		minSdkVersion = null;
		apkName = null;
		uses_permission = null;
	}

	@Override
	public String toString() {
		return "ApkInfo{" + "versionCode='" + versionCode + '\'' + ", versionName='" + versionName + '\''
				+ ", apkPackage='" + apkPackage + '\'' + ", minSdkVersion='" + minSdkVersion + '\'' + ", apkName='"
				+ apkName + '\'' + ", uses_permission=" + uses_permission + '}';
	}

}
