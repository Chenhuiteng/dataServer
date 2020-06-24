package com.cht.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cht.dao.ResidentDao;
import com.cht.entity.TblResident;
import com.cht.service.ResidentService;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

  @Autowired private ResidentDao residentDao;

  /** 查询所有人 */
  @Override
  public List<TblResident> findAll() {
    return residentDao.listAll();
  }

  /** 查询单个人 */
  @Override
  public TblResident findOne(Long id) {
    return residentDao.findOne(id);
  }

  @Override
  public void deleteOne(Long id) {
    residentDao.delete(id);
  }

  @Override
  public List<TblResident> listByParams(Map<String, Object> params) {
    return residentDao.listByParams(params);
  }
}
