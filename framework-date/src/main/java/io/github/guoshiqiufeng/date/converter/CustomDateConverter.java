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
package io.github.guoshiqiufeng.date.converter;

import io.github.guoshiqiufeng.date.utils.LocalDateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * convert 转化配置
 *
 * @author yanghq
 */
public class CustomDateConverter {

	public static class LocalDateConvert implements Converter<String, LocalDate> {

		@Override
		public LocalDate convert(String timestamp) {
			return LocalDateUtil.timestampToLocalDateTime(timestamp).toLocalDate();
		}

	}

}
