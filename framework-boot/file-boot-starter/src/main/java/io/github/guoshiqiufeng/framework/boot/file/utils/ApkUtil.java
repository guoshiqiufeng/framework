package io.github.guoshiqiufeng.framework.boot.file.utils;

import io.github.guoshiqiufeng.framework.boot.file.domain.ApkInfo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 获取apk文件中的信息
 */
@SuppressWarnings("all")
public class ApkUtil {

	private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

	@SuppressWarnings("unchecked")
	public static ApkInfo getApkInfo(String apkPath) {
		ApkInfo apkInfo = new ApkInfo();
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(getXmlInputStream(apkPath));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();// 跟节点-->manifest
		apkInfo.setVersionCode(root.getAttributeValue("versionCode", NS));
		apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
		apkInfo.setApkPackage(root.getAttributeValue("package", NS));
		Element elemUseSdk = root.getChild("uses-sdk");// 子节点-->uses-sdk
		apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
		List listPermission = root.getChildren("uses-permission");// 子节点是个集合
		List permissions = new ArrayList();
		for (Object object : listPermission) {
			String permission = ((Element) object).getAttributeValue("name", NS);
			permissions.add(permission);
		}
		apkInfo.setUses_permission(permissions);

		String s = root.getAttributes().toString();
		String[] c = s.split(",");
		String versionCode = null;
		String versionName = null;
		String packageName = null;
		for (String a : c) {
			if (a.contains("versionCode")) {
				versionCode = a.substring(a.indexOf("versionCode=\"") + 13, a.lastIndexOf("\""));
			}
			if (a.contains("versionName")) {
				versionName = a.substring(a.indexOf("versionName=\"") + 13, a.lastIndexOf("\""));
			}
			if (a.contains("package")) {
				packageName = a.substring(a.indexOf("package=\"") + 9, a.lastIndexOf("\""));
			}
		}
		System.out.println("\n版本号:" + versionCode + "\n版本名:" + versionName + "\n包名:" + packageName);
		ApkInfo info = new ApkInfo();
		info.setApkName("");
		info.setVersionCode(versionCode);
		info.setApkPackage(packageName);
		info.setVersionName(versionName);
		return info;
	}

	private static InputStream getXmlInputStream(String apkPath) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(apkPath);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
		}
		catch (IOException e) {
			e.printStackTrace();
			try {
				inputStream.close();
				zipFile.close();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return xmlInputStream;
	}

}
