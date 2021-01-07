package io.github.guoshiqiufeng.date.converter;

import io.github.guoshiqiufeng.date.utils.LocalDateUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author yanghq
 */
public class CustomDateArgumentResolverHandler {

	public static class LocalDateArgumentResolverHandler implements HandlerMethodArgumentResolver {

		@Override
		public boolean supportsParameter(MethodParameter parameter) {
			return parameter.getParameterType().equals(LocalDate.class);
		}

		@Override
		public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
				NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

			String timestamp = webRequest.getParameter(parameter.getParameterName());
			LocalDateTime localDateTime = LocalDateUtil.timestampToLocalDateTime(timestamp);
			return localDateTime.toLocalDate();
		}

	}

}
