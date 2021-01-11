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
