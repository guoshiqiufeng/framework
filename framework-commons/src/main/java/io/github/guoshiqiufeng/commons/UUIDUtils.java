package io.github.guoshiqiufeng.commons;

import java.util.UUID;

/**
 * @author yanghq
 */
public class UUIDUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
