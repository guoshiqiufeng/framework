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
package io.github.guoshiqiufeng.framework.boot.oss.ali;

import com.aliyun.oss.OSSClient;
import io.github.guoshiqiufeng.framework.boot.oss.OssSource;
import io.github.guoshiqiufeng.framework.boot.oss.autoconfigure.OssProperties;
import io.github.guoshiqiufeng.framework.boot.oss.exception.OssException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云 oss
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-18 10:01
 * @email fubluesky@foxmail.com
 */
public class AliOssSource implements OssSource {

	@Autowired
	private OssProperties ossProperties;

	private OSSClient client;

	private void init() {
		if (!ossProperties.getEnabled()) {
			throw new OssException("当前未启用oss");
		}
		client = new OSSClient(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
	}

	/**
	 * 获取http路径前缀
	 * @return http路径前缀
	 */
	@Override
	public String getHttpPrefix() {
		return ossProperties.getDomain();
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	@Override
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	@Override
	public String uploadSuffix(byte[] data, String suffix) {
		return upload(data, getPath(ossProperties.getPrefix(), suffix));
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	@Override
	public String uploadSuffix(byte[] data, String prefix, String suffix) {
		return upload(data, getPath(ossProperties.getPrefix() + prefix, suffix));
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
		try {
			// 初始化
			init();
			client.putObject(ossProperties.getBucketName(), path, inputStream);
		}
		catch (Exception e) {
			throw new OssException("上传文件失败，请检查配置信息");
		}
		finally {
			client.shutdown();
		}

		return "/" + path;
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	@Override
	public String uploadSuffix(InputStream inputStream, String suffix) {
		return upload(inputStream, getPath(ossProperties.getPrefix(), suffix));
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回http地址
	 */
	@Override
	public String uploadSuffix(InputStream inputStream, String prefix, String suffix) {
		return upload(inputStream, getPath(ossProperties.getPrefix() + prefix, suffix));
	}

}
