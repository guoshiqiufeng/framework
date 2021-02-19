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
package io.github.guoshiqiufeng.framework.boot.jwt.utils;

import io.github.guoshiqiufeng.framework.boot.jwt.autoconfigure.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * jwt 工具
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-19 10:26
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtUtils {

	private final JwtProperties jwtProperties;

	/**
	 * 生成JWT
	 * @param id id
	 * @param subject 载荷内容
	 * @return
	 */
	public String createJwt(String id, String subject) {
		long nowMillis = System.currentTimeMillis();
		Date nowDate = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject).setIssuedAt(nowDate)
				.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret());
		if (jwtProperties.getExpire() > 0) {
			Date expireDate = new Date(nowDate.getTime() + jwtProperties.getExpire() * 1000);
			builder.setExpiration(expireDate);
		}
		return builder.compact();
	}

	/**
	 * 解析JWT 过期会抛异常
	 * @param jwtStr token
	 * @return Claims
	 */
	public Claims parseJwt(String jwtStr) {
		return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(jwtStr).getBody();
	}

	/**
	 * 获取id
	 * @param jwtStr token
	 * @return id
	 */
	public String getId(String jwtStr) {
		try {
			Claims claims = parseJwt(jwtStr);
			return claims.getId();
		}
		catch (Exception exception) {
			return null;
		}
	}

	/**
	 * 不管是否过期，解析token中的内容
	 * @param jwtStr token
	 * @return 解析后内容
	 */
	public String getClaims(String jwtStr) {
		int start = jwtStr.indexOf(".");
		int end = jwtStr.lastIndexOf(".");
		jwtStr = jwtStr.substring(start + 1, end);
		byte[] bytes = Base64.getDecoder().decode(jwtStr);
		return new String(bytes);
	}

	/**
	 * 校验是否有效
	 * @param jwtStr token
	 * @return Boolean
	 */
	public Boolean verify(String jwtStr) {
		try {
			parseJwt(jwtStr);
			return true;
		}
		catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获取过期时长
	 * @return 过期时长
	 */
	public long getExpire() {
		return jwtProperties.getExpire();
	}

	/**
	 * 获取刷新时间
	 * @return 刷新时间
	 */
	public int getRefresh() {
		return jwtProperties.getRefresh();
	}
}
