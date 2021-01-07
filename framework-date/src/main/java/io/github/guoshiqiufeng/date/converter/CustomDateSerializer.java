package io.github.guoshiqiufeng.date.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间序列化
 * @author yanghq
 */
public class CustomDateSerializer {

	public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

		@Override
		public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException {
			if (localDate != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localDate.atStartOfDay(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

	public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

		@Override
		public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
				SerializerProvider serializerProvider) throws IOException {
			if (localDateTime != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localDateTime.atZone(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

	public static class DateSerializer extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializers)
				throws IOException {
			if (value != null) {
				jsonGenerator.writeNumber(value.getTime());
			}
		}

	}

}
