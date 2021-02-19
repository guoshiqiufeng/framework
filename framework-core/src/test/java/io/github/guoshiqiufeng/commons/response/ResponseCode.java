package io.github.guoshiqiufeng.commons.response;

/**
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-04 16:16
 */
public class ResponseCode extends BaseResponseCode {

	/**
	 * 未知错误
	 */
	public final static ResponseCode TEST = new ResponseCode(-2, "测试错误");

	ResponseCode(Integer code, String message) {
		super(code, message);
	}

}
