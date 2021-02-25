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
package io.github.guoshiqiufeng.framework.boot.push;

import java.util.List;

/**
 * push扫描接口 所有实现类都要实现此接口
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-25 9:27
 */
public interface PushSource {

	/**
	 * 推送消息
	 * @param accountNameList 账号列表
	 * @param pushData 推送内容
	 */
	public abstract void pushMessage(List<String> accountNameList, String pushData);

	/**
	 * 根据别名推送消息
	 * @param accountNameList 别名列表
	 * @param pushData 推送内容
	 */
	public abstract void pushMessageByAlias(List<String> accountNameList, String pushData);

}
