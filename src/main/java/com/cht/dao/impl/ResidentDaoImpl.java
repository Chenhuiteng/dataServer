package com.cht.dao.impl;

import org.springframework.stereotype.Repository;
import com.cht.dao.ResidentDao;
import com.cht.entity.TblResident;

@Repository
public class ResidentDaoImpl extends ResidentDao{

	@Override
	protected Class<TblResident> getEntityClass() {
		return TblResident.class;
	}

}
