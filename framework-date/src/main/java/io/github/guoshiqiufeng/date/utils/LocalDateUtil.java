package io.github.guoshiqiufeng.date.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间转化工具
 *
 * @author yanghq
 */
public class LocalDateUtil {

	/**
	 * 时间戳转LocalDateTime
	 * @param timestamp 时间戳
	 * @return LocalDateTime
	 */
	public static LocalDateTime timestampToLocalDateTime(String timestamp) {
		Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

}
