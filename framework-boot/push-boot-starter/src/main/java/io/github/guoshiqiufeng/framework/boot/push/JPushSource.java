package io.github.guoshiqiufeng.framework.boot.push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.GroupPushClient;
import cn.jpush.api.push.GroupPushResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import io.github.guoshiqiufeng.framework.boot.push.autoconfigure.PushProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 极光推送实现
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-25 9:37
 */
@Slf4j
@SuppressWarnings("all")
public class JPushSource implements PushSource {

	/**
	 * 一次推送最大数量 (极光限制1000)
	 */
	private static final int MAX_SIZE = 800;

	private GroupPushClient groupPushClient = null;

	private JPushClient jPushClient = null;

	@Autowired
	private PushProperties pushProperties;

	private void init() {
		// 初始化
		try {
			if (StringUtils.isNotBlank(pushProperties.getGroupAppKey())) {
				groupPushClient = new GroupPushClient(pushProperties.getGroupSecret(), pushProperties.getGroupAppKey());
			}
			else {
				jPushClient = new JPushClient(pushProperties.getSecret(), pushProperties.getAppKey());
			}
		}
		catch (Exception e) {
			log.error("初始化推送服务失败。error:{}", e.getMessage());
		}
	}

	/**
	 * 推送消息
	 * @param accountNameList 账号列表
	 * @param pushData 推送内容
	 */
	@Override
	public void pushMessage(List<String> accountNameList, String pushData) {
		String[] strArr = new String[accountNameList.size()];
		accountNameList.toArray(strArr);
		this.pushMessage(pushData, strArr);
	}

	/**
	 * 根据别名推送消息
	 * @param accountNameList 别名列表
	 * @param pushData 推送内容
	 */
	@Override
	public void pushMessageByAlias(List<String> accountNameList, String pushData) {
		String[] strArr = new String[accountNameList.size()];
		accountNameList.toArray(strArr);

		this.pushMessageByAlias(pushData, strArr);
	}

	/**
	 * 发送消息
	 * @param msgContent 内容
	 * @param alias 别名
	 * @return 是否成功
	 */
	private boolean pushMessageByAlias(String msgContent, String... alias) {
		return sendPush(PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
				.setMessage(Message.content(msgContent)).build());
	}

	/**
	 * 发送消息
	 * @param msgContent 内容
	 * @param registrationIds 注册id
	 * @return 是否成功
	 */
	private boolean pushMessage(String msgContent, String... registrationIds) {
		return sendPush(PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.registrationId(registrationIds)).setMessage(Message.content(msgContent)).build());
	}

	/**
	 * 发送推送
	 * @param pushPayload 载体
	 * @return 是否成功
	 */
	private boolean sendPush(PushPayload pushPayload) {
		log.info("发送极光推送请求: {}", pushPayload);
		try {
			if (groupPushClient != null) {
				GroupPushResult result = groupPushClient.sendGroupPush(pushPayload);
				for (Map.Entry<String, PushResult> entry : result.getAppResultMap().entrySet()) {
					PushResult pushResult = entry.getValue();
					PushResult.Error error = pushResult.error;
					if (error != null) {
						log.info("AppKey: " + entry.getKey() + " error code : " + error.getCode() + " error message: "
								+ error.getMessage());
					}
					else {
						log.info("AppKey: " + entry.getKey() + " sendno: " + pushResult.sendno + " msg_id:"
								+ pushResult.msg_id);
					}
				}
			}
			else {
				PushResult result = jPushClient.sendPush(pushPayload);
				if (result != null && result.isResultOK()) {
					log.info("极光推送请求成功: {}", result);
					return true;
				}
				else {
					log.info("极光推送请求失败: {}", result);
					return false;
				}
			}
		}
		catch (APIConnectionException e) {
			log.error("极光推送连接异常: ", e);
		}
		catch (APIRequestException e) {
			log.error("极光推送请求异常: ", e);
		}
		return false;
	}

}
