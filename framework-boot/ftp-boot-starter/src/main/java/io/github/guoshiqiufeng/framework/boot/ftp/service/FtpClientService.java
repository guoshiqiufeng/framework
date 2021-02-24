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
package io.github.guoshiqiufeng.framework.boot.ftp.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ftp 上传 接口
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 11:45
 */
public interface FtpClientService {

	/**
	 * 获取远程服务器文件
	 * @param filePath 文件路径
	 * @param output 输出流
	 */
	public void findByFile(String filePath, OutputStream output);

	/**
	 * 保存到远程服务器
	 * @param file 文件
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	public boolean saveFile(File file, String filePath);

	/**
	 * 保存到远程服务器
	 * @param inputStream 文件流
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	public boolean saveFileAsStream(InputStream inputStream, String filePath);

	/**
	 * 删除远程服务器文件
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	public boolean deleteFile(String filePath);

	/**
	 * 下载附件
	 * @param filePath 文件路径
	 * @return 文件流
	 */
	public InputStream findAttach(String filePath);

}
