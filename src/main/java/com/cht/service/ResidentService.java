package com.cht.service;

import java.util.List;
import java.util.Map;

import com.cht.entity.TblResident;

/**
 * @author tick
 */
public interface ResidentService {

	/** 查询所有人 */
	public List<TblResident> findAll();
	/**查询单个人*/
	public TblResident findOne(Long id);

    void deleteOne(Long id);

	List<TblResident> listByParams(Map<String, Object> params);
}
