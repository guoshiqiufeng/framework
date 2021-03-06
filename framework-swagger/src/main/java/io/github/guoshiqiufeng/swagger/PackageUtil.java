package io.github.guoshiqiufeng.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-01-15 13:36
 */
@SuppressWarnings("all")
public class PackageUtil {

	/**
	 * 重写basePackage方法，使能够实现多包访问，复制贴上去
	 */
	public static Predicate<RequestHandler> basePackage(final List<String> basePackages) {
		return input -> declaringClass(input).transform(handlerPackage(basePackages)).or(true);
	}

	private static Function<Class<?>, Boolean> handlerPackage(final List<String> basePackages) {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackages) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

}
