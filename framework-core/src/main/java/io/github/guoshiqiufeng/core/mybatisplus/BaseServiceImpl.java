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
package io.github.guoshiqiufeng.core.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.guoshiqiufeng.core.exception.BusinessException;
import io.github.guoshiqiufeng.core.response.BaseResponseCode;
import io.github.guoshiqiufeng.core.utils.QueryUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 通用业务逻辑接口
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-05 13:42
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDomain> extends ServiceImpl<M, T>
		implements IBaseService<T> {

	/**
	 * 通过反射调用 getId()
	 */
	private static final String INVOKE_ID = "getId";

	/**
	 * 检查字段：ID
	 */
	protected static final String ID = "id";

	@Override
	public boolean save(T domain) {
		return super.save(domain);
	}

	@Override
	public boolean remove(Long id) {
		if (checkId(id)) {
			return super.removeById(id);
		}
		return false;
	}

	@Override
	public boolean update(T domain) {
		try {
			if (checkId((Long) domain.getClass().getMethod(INVOKE_ID).invoke(domain))) {
				return super.updateById(domain);
			}
			return false;
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public T get(Long id) {
		T domain = super.getById(id);
		if (null == domain) {
			throw new BusinessException(BaseResponseCode.RESULT_DATA_NONE);
		}
		return domain;
	}

	@Override
	public IPage<?> page(int current, int size, T domain) {
		Page<T> page = new QueryUtils<T>().getPage(current, size);
		for (Field field : domain.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if ("String".equals(field.getType().getSimpleName())) {
					if (StringUtils.isBlank((String) field.get(domain))) {
						field.set(domain, null);
					}
				}
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery(domain);
		return super.page(page, wrapper);
	}

	/**
	 * 批量删除
	 * @param ids id数组
	 * @return {@code boolean}
	 */
	@Override
	public boolean delete(Long[] ids) {
		try {
			super.removeByIds(Arrays.asList(ids));
			return true;
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 检查 ID 是否存在
	 * @param id {@code Long} ID
	 * @return {@code boolean} ID 不存在则抛出异常
	 */
	protected boolean checkId(Long id) {
		if (!checkUniqueness(ID, id)) {
			throw new BusinessException(BaseResponseCode.RESULT_DATA_NONE);
		}
		return true;
	}

	protected boolean checkUniqueness(String column, Object value) {
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		wrapper.eq(column, value);
		return super.count(wrapper) > 0;
	}

}
