package com.cht.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public abstract class BaseRepository<E , ID extends Serializable>  {
	
	@PersistenceContext
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}
	
	// 实体类的类型
	protected abstract Class<E> getEntityClass();

	// 添加实体
	public E save(E e) {
		getEntityManager().persist(e);
		return e;
	}

	// 获取所有实体类列表数据
	public List<E> listAll() {
		String jpql = "from " + getEntityClass().getName();
		return getEntityManager().createQuery(jpql, getEntityClass()).getResultList();
	}

	// 更新实体
	public E update(E e) {
		getEntityManager().merge(e);
		return e;
	}

	// 根据ID删除实体
	public void delete(ID id) {
		getEntityManager().remove(findOne(id));
	}

	// 根据ID获取实体
	public E findOne(ID id) {
		E e = getEntityManager().find(getEntityClass(), id);
		return e;
	}

	/**
	 * 根据查询条件返回实体列表
	 * 
	 * @param params
	 *            查询条件，(key, value) -> (实体字段名, 字段的值)
	 * @return
	 */
	public List<E> listByParams(Map<String, Object> params) {
		StringBuilder builder = new StringBuilder("select t from " + getEntityClass().getName() + " t where 1 = 1 ");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				builder.append(" and t." + key + " = :" + key);
			}
		}
		String jpql = builder.toString();
		TypedQuery<E> query = getEntityManager().createQuery(jpql, getEntityClass());
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				query.setParameter(p.getKey(), p.getValue());
			}
		}
		return query.getResultList();
	}


	/**
	 *  获取当前实体的总数
	 */
	public Number getEntityCount() {
		Table table = getEntityClass().getAnnotation(Table.class);
		Number number = null;
		if (table != null) {
			String tableName = table.name();
			String countSql = "select count(1) from " + tableName;
			number = (Number) getEntityManager().createNativeQuery(countSql).unwrap(SQLQuery.class).uniqueResult();
		} else {
			number = new Long(0);
		}
		return number;
	}

	public Long getCountByParams(String sql, Map<String, Object> params) {
		TypedQuery<Long> query = getEntityManager().createQuery(sql, Long.class);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				query.setParameter(p.getKey(), p.getValue());
			}
		}
		return query.getSingleResult();
	}

	public void batchDelete(ID ids[]) {
		if (ids != null && ids.length > 0) {
			EntityManager manager = getEntityManager();
			for (int i = 0, len = ids.length; i < len; ++i) {
				manager.remove(findOne(ids[i]));
				if (i % 50 == 0 && i != 0) {
					manager.flush();
					manager.clear();
				}
			}
			manager.flush();
			manager.clear();
		}
	}

	public void batchSave(E entities[]) {
		if (entities != null && entities.length > 0) {
			EntityManager manager = getEntityManager();
			for (int i = 0, len = entities.length; i < len; ++i) {
				manager.persist(entities[i]);
				if (i % 50 == 0 && i != 0) {
					manager.flush();
					manager.clear();
				}
			}
			manager.flush();
			manager.clear();
		}
	}
	
	public void batchSave(List<E> entities) {
		if (entities != null && entities.size() > 0) {
			EntityManager manager = getEntityManager();
			for (int i = 0, len = entities.size(); i < len; ++i) {
				manager.persist(entities.get(i));
				if (i % 50 == 0 && i != 0) {
					manager.flush();
					manager.clear();
				}
			}
			manager.flush();
			manager.clear();
		}
	}

	public void batchUpdate(E entities[]) {
		if (entities != null && entities.length > 0) {
			EntityManager manager = getEntityManager();
			for (int i = 0, len = entities.length; i < len; ++i) {
				manager.merge(entities[i]);
				if (i % 50 == 0 && i != 0) {
					manager.flush();
					manager.clear();
				}
			}
			manager.flush();
			manager.clear();
		}
	}

	public void batchUpdate(List<E> entities) {
		if (entities != null && entities.size() > 0) {
			EntityManager manager = getEntityManager();
			for (int i = 0, len = entities.size(); i < len; ++i) {
				manager.merge(entities.get(i));
				if (i % 50 == 0 && i != 0) {
					manager.flush();
					manager.clear();
				}
			}
			manager.flush();
			manager.clear();
		}
	}

	/**
	 * 根据查询条件返回实体列表
	 * 
	 * @param sql
	 * @param params
	 *            查询条件，(key, value) -> (实体字段名, 字段的值)
	 * @return
	 */
	public List<E> listByParams(String sql, Map<String, Object> params) {
		TypedQuery<E> query = getEntityManager().createQuery(sql, getEntityClass());
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				query.setParameter(p.getKey(), p.getValue());
			}
		}
		return query.getResultList();
	}

	public List<E> listByIds(ID[] ids) {
		return listByIds(Arrays.asList(ids));
	}
	
	public List<E> listByIds(Collection<ID> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<E>();
		}
		Field[] fields = getEntityClass().getFields();
		String idName = null;
		for (Field f : fields) {
			if (f.getAnnotation(Id.class) != null) {
				idName = f.getName();
				break;
			}
		}
		if (idName == null) {
			Method[] methods = getEntityClass().getMethods();
			for (Method m : methods) {
				if (m.getAnnotation(Id.class) != null) {
					idName = ("" + m.getName().charAt(3)).toLowerCase() + m.getName().substring(4);
					break;
				}
			}
		}
		String sql = "select t from " + getEntityClass().getName() + " t where t." + idName + " in (:ids) ";
		return getEntityManager().createQuery(sql, getEntityClass()).setParameter("ids", ids).getResultList();
	}
	
	

	/**
	 * 根据原生sql获取数据
	 * 
	 * @param sql
	 *            原生sql
	 * @param params
	 *            sql中的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listByNativeSql(String sql, Map<String, Object> params) {
		SQLQuery query = getEntityManager().createNativeQuery(sql).unwrap(SQLQuery.class);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				query.setParameter(p.getKey(), p.getValue());
			}
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 执行统计的原生sql
	 * 
	 * @param countSql
	 * @param params
	 * @return
	 */
	public Number countByNativeSql(String countSql, Map<String, Object> params) {
		SQLQuery sqlQuery = getEntityManager().createNativeQuery(countSql).unwrap(SQLQuery.class);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				sqlQuery.setParameter(p.getKey(), p.getValue());
			}
		}
		return (Number) sqlQuery.uniqueResult();
	}

	/**
	 * 执行原生sql
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeByNativeSql(String sql, Map<String, Object> params) {
		Query query = getEntityManager().createNativeQuery(sql);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> p : params.entrySet()) {
				query.setParameter(p.getKey(), p.getValue());
			}
		}
		return query.executeUpdate();
	}

}
