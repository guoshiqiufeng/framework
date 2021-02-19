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
package io.github.guoshiqiufeng.commons.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.guoshiqiufeng.commons.xss.SQLFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询参数
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-05 13:52
 */
public class QueryUtils<T> {

	public Page<T> getPage(Integer current, Integer size) {
		return this.getPage(current, size, "id", false);
	}

	public Page<T> getPage(Integer current, Integer size, String defaultOrderField, boolean isAsc) {
		// 分页参数
		long curPage = 1;
		long limit = 10;

		if (current != null) {
			curPage = current;
		}
		if (size != null) {
			limit = size;
		}

		// 分页对象
		Page<T> page = new Page<>(curPage, limit);

		// 排序字段
		// 防止SQL注入（因为order是通过拼接SQL实现排序的，会有SQL注入风险）
		String orderField = SQLFilter.sqlInject(defaultOrderField);

		// 没有排序字段，则不排序
		if (StringUtils.isBlank(defaultOrderField)) {
			return page;
		}

		// 默认排序
		if (isAsc) {
			page.addOrder(OrderItem.asc(orderField));
		}
		else {
			page.addOrder(OrderItem.desc(orderField));
		}

		return page;
	}

}
