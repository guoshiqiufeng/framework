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
package io.github.guoshiqiufeng.framework.boot.ftp.service.impl;

import io.github.guoshiqiufeng.framework.boot.ftp.constants.FtpClientConstants;
import io.github.guoshiqiufeng.framework.boot.ftp.service.FtpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * ftp 上传 实现
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-23 11:49
 */
@Slf4j
public class FtpClientServiceImpl implements FtpClientService {

	private static final String BACKSLASHES = "/";

	/**
	 * 常量
	 */
	private final FtpClientConstants ftpClientConstants;

	private final ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<>();

	public FtpClientServiceImpl(FtpClientConstants ftpClientConstants) {
		this.ftpClientConstants = ftpClientConstants;
	}

	/**
	 * 链接ftp服务器
	 * @throws SocketException
	 * @throws IOException
	 */
	private void openConnect() throws SocketException, IOException {
		// 重复请求链次数
		int reply;
		if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {
			return;
		}
		// 初始化
		FTPClient ftpClient = new FTPClient();
		// ip
		ftpClient.connect(ftpClientConstants.getHost());
		// 端口
		ftpClient.setDefaultPort(Integer.parseInt(ftpClientConstants.getPort()));
		// 编码
		ftpClient.setControlEncoding("UTF-8");

		reply = ftpClient.getReplyCode();

		// ftp 服务拒绝链接
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}

		// 登陆用户
		if (!ftpClient.login(ftpClientConstants.getUsername(), ftpClientConstants.getPassword())) {
			disConnect();
		}
		// 设置传输超时时间为60秒
		ftpClient.setDataTimeout(60000);
		/*
		 * ftpClient.setConnectTimeout(60000); //连接超时为60秒
		 */
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setBufferSize(1024 * 1024 * 10);
		if (ftpClientConstants.getIsPassive()) {
			ftpClient.enterLocalPassiveMode();
		}
		else {
			ftpClient.enterLocalActiveMode();
		}
		ftpClientThreadLocal.set(ftpClient);
	}

	/**
	 * 关闭链接
	 */
	private void disConnect() {
		FTPClient ftpClient = ftpClientThreadLocal.get();
		if (ftpClient != null) {
			try {
				ftpClient.logout();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		ftpClientThreadLocal.remove();
	}

	/**
	 * 获取远程服务器文件
	 * @param filePath 文件路径
	 * @param output 输出流
	 */
	@Override
	public void findByFile(String filePath, OutputStream output) {
		try {
			// 打开链接
			openConnect();

			FTPClient ftpClient = ftpClientThreadLocal.get();

			if (StringUtils.isNotBlank(filePath)) {
				this.createDir(filePath, ftpClient);
			}

			String saveFileName = getFileNameByPath(filePath);
			ftpClient.retrieveFile(saveFileName, output);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnect();
		}
	}

	/**
	 * 保存到远程服务器
	 * @param file 文件
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	@Override
	public boolean saveFile(File file, String filePath) {
		boolean flag = true;
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();
			this.createDir(filePath, ftpClient);
			BufferedInputStream fiStream = new BufferedInputStream(new FileInputStream(file));
			String fileName = getFileNameByPath(filePath);
			flag = ftpClient.storeFile(fileName, fiStream);
			fiStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnect();
		}
		return flag;
	}

	/**
	 * 保存到远程服务器
	 * @param inputStream 文件流
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	@Override
	public boolean saveFileAsStream(InputStream inputStream, String filePath) {
		boolean flag = true;
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();
			this.createDir(filePath, ftpClient);
			String fileName = getFileNameByPath(filePath);
			flag = ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnect();
		}
		return flag;
	}

	/**
	 * 删除远程服务器文件
	 * @param filePath 文件路径
	 * @return 是否成功
	 */
	@Override
	public boolean deleteFile(String filePath) {
		boolean flag = true;
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();
			this.createDir(filePath, ftpClient);
			String fileName = getFileNameByPath(filePath);
			flag = ftpClient.deleteFile(fileName);
		}
		catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		finally {
			disConnect();
		}
		return flag;
	}

	/**
	 * 下载附件
	 * @param filePath 文件路径
	 * @return 文件流
	 */
	@Override
	public InputStream findAttach(String filePath) {
		InputStream input = null;
		BufferedOutputStream output = null;
		try {
			// 打开链接
			openConnect();
			FTPClient ftpClient = ftpClientThreadLocal.get();
			String fileName = getFileNameByPath(filePath);
			File localFile = new File(ftpClientConstants.getTempDir(), fileName);
			if (!localFile.exists()) {
				output = new BufferedOutputStream(new FileOutputStream(localFile));
				if (StringUtils.isNotBlank(filePath)) {
					this.createDir(filePath, ftpClient);
				}
				ftpClient.retrieveFile(fileName, output);
				output.flush();
				output.close();
			}
			input = new FileInputStream(localFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnect();
		}
		return input;
	}

	private String getFileNameByPath(String filePath) {
		String fileName = "";
		try {
			fileName = filePath.substring(filePath.lastIndexOf(BACKSLASHES) + 1);
		}
		catch (Exception e) {
			log.error("获取文件名失败");
		}
		return fileName;
	}

	private boolean createDir(String remote, FTPClient ftpClient) {
		String directory = remote.substring(0, remote.lastIndexOf(BACKSLASHES) + 1);
		try {
			if (!BACKSLASHES.equalsIgnoreCase(directory)
					&& !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {
				int start = 0;
				int end = 0;
				if (directory.startsWith(BACKSLASHES)) {
					start = 1;
				}
				else {
					start = 0;
				}
				end = directory.indexOf(BACKSLASHES, start);
				do {
					String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (!ftpClient.makeDirectory(subDirectory)) {
							log.error("创建目录" + subDirectory + "失败");
							return false;
						}
						ftpClient.changeWorkingDirectory(subDirectory);
					}
					start = end + 1;
					end = directory.indexOf(BACKSLASHES, start);
				}
				while (end > start);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
