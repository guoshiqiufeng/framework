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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.guoshiqiufeng.core.response.BaseResponseCode;
import io.github.guoshiqiufeng.core.response.ResponseResult;
import io.github.guoshiqiufeng.core.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 通用访问层
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-19 14:34
 */
@SuppressWarnings("all")
public abstract class BaseController<T extends BaseDomain, S extends IBaseService<T>> {

	@Autowired
	protected S service;

	/**
	 * 新增
	 * @param domain 领域模型
	 * @return {@link ResponseResult}
	 */
	@PostMapping("save")
	public ResponseResult save(@Valid @RequestBody T domain) {
		// 业务逻辑
		boolean created = service.save(domain);
		if (created) {
			return ResponseResult.success("创建成功");
		}

		return ResponseResult.failure(BaseResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 删除
	 * @param id {@code Long}
	 * @return {@link ResponseResult}
	 */
	@PostMapping("delete/{id}")
	public ResponseResult deleteById(@PathVariable Long id) {
		// 业务逻辑
		boolean deleted = service.remove(id);
		if (deleted) {
			return ResponseResult.success("删除成功");
		}

		return ResponseResult.failure(BaseResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	@PostMapping("delete")
	public ResponseResult delete(@RequestBody Long[] ids) {
		// 业务逻辑
		boolean deleted = service.delete(ids);
		if (deleted) {
			return ResponseResult.success("删除成功");
		}

		return ResponseResult.failure(BaseResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 修改
	 * @param domain 领域模型
	 * @return {@link ResponseResult}
	 */
	@PostMapping("update")
	public ResponseResult update(@Valid @RequestBody T domain) {
		// 业务逻辑
		boolean updated = service.update(domain);
		if (updated) {
			return ResponseResult.success("编辑成功");
		}

		return ResponseResult.failure(BaseResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 获取
	 * @param id {@code Long} id
	 * @return {@link ResponseResult}
	 */
	@GetMapping("info/{id}")
	public ResponseResult get(@PathVariable Long id) {
		T domain = service.get(id);
		return ResponseResult.success(domain);
	}

	/**
	 * 分页
	 * @param current {@code int} 页码
	 * @param size {@code int} 笔数
	 * @return {@link ResponseResult}
	 */
	@GetMapping("page")
	public ResponseResult page(@RequestParam int page, @RequestParam int limit, @ModelAttribute T domain) {
		IPage<?> pageResult = service.page(page, limit, domain);
		return ResponseResult.success(new PageResult(pageResult));
	}

	/**
	 * 列表查询
	 * @param domain 领域模型
	 * @return {@link ResponseResult}
	 */
	@GetMapping("list")
	public ResponseResult list(@ModelAttribute T domain) {
		List<T> list = service.list(new QueryWrapper<>(domain));
		return ResponseResult.success(list);
	}

}
