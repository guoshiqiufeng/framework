package io.github.guoshiqiufeng.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址
 * @author yanghq
 */
@Slf4j
public class IpUtils {

	private static final String UNKNOWN = "unknown";

	private static final String X_FORWARDED_HEADER = "x-forwarded-for";

	private static final String PROXY_CLIENT_HEADER = "Proxy-Client-IP";

	private static final String WL_PROXY_CLIENT_HEADER = "WL-Proxy-Client-IP";

	private static final String HTTP_CLIENT_HEADER = "HTTP_CLIENT_IP";

	private static final String HTTP_X_FORWARDED_HEADER = "HTTP_X_FORWARDED_FOR";

	private static final String ERROR_MSG = "IPUtils ERROR ";
	/**
	 * 获取IP地址
	 *
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader(X_FORWARDED_HEADER);
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(PROXY_CLIENT_HEADER);
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(WL_PROXY_CLIENT_HEADER);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(HTTP_CLIENT_HEADER);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(HTTP_X_FORWARDED_HEADER);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		catch (Exception e) {
			log.error(ERROR_MSG, e);
		}

		return ip;
	}

}
