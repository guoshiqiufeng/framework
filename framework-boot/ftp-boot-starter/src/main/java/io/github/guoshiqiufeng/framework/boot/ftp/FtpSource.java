/*
 * Copyright 2020-2021 fubluesky.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.guoshiqiufeng.framework.boot.ftp;

import io.github.guoshiqiufeng.framework.boot.ftp.autoconfigure.FtpProperties;
import io.github.guoshiqiufeng.framework.boot.ftp.constants.FtpClientConstants;
import io.github.guoshiqiufeng.framework.boot.ftp.exception.FtpException;
import io.github.guoshiqiufeng.framework.boot.ftp.service.FtpClientService;
import io.github.guoshiqiufeng.framework.boot.ftp.service.impl.FtpClientServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * ftp 文件上传封装
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 11:49
 */
public class FtpSource {

	@Autowired
	private FtpProperties ftpProperties;

	private static FtpClientService ftpClientService;

	private void init() {
		if (!ftpProperties.getEnabled()) {
			throw new FtpException("当前未启用ftp");
		}
		FtpClientConstants ftpClientConstants = new FtpClientConstants();
		ftpClientConstants.setHost(ftpProperties.getFtpIp());
		ftpClientConstants.setPort(ftpProperties.getFtpPort());
		ftpClientConstants.setUsername(ftpProperties.getUserName());
		ftpClientConstants.setPassword(ftpProperties.getPassword());
		ftpClientConstants.setIsPassive(ftpProperties.getIsPassive());
		ftpClientConstants.setTempDir(ftpProperties.getTempDir());
		ftpClientService = new FtpClientServiceImpl(ftpClientConstants);
	}

	/**
	 * 获取外网访问地址前缀
	 * @return 外网访问地址前缀
	 */
	public String getHttpPrefix() {
		return ftpProperties.getHttpPrefix();
	}

	/**
	 * 文件路径
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回上传路径
	 */
	public String getPath(String prefix, String suffix) {
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
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public String uploadSuffix(byte[] data, String suffix) {
		return upload(new ByteArrayInputStream(data), getPath(ftpProperties.getPrefix(), suffix));
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public String uploadSuffix(byte[] data, String prefix, String suffix) {
		return upload(new ByteArrayInputStream(data), getPath(ftpProperties.getPrefix() + prefix, suffix));
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	public String upload(InputStream inputStream, String path) {
		try {
			// 初始化
			init();
			boolean fileFlag = ftpClientService.saveFileAsStream(inputStream, path);
			if (fileFlag) {
				return "/" + path;
			}
		}
		catch (Exception e) {
			throw new FtpException("上传文件失败，请检查配置信息");
		}
		throw new FtpException("上传文件失败，请检查配置信息");
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public String uploadSuffix(InputStream inputStream, String suffix) {
		return this.upload(inputStream, getPath(ftpProperties.getPrefix(), suffix));
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	public String uploadSuffix(InputStream inputStream, String prefix, String suffix) {
		return this.upload(inputStream, getPath(ftpProperties.getPrefix() + prefix, suffix));
	}

}
