package com.carManage.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.utils.HibernateUtils;

public abstract class BaseDAO<T, E> implements IDAO<T> {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public boolean update(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int delete(List<T> t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insert(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<T> query(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param t
	 *            传入的表实体
	 * @param start
	 *            请求数据，开始的位置
	 * @param count
	 *            请求的条数
	 * @param o1
	 *            限制条件1
	 * @param o2
	 *            限制条件2
	 * @return
	 */
	public List<T> query(T t, int start, int count, E o1, E o2) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据限制条件来获取到总的数据数量
	 * 
	 * @param t
	 * @param o1
	 *            限制条件1
	 * @param o2
	 *            限制条件2
	 * @return
	 * 			如果查询出错返回-1
	 */
	public long getDataCountWithLimit(T t, E o1, E o2) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1l;
	}

	/**
	 * 获取到数据库中此类对象的总的条数
	 * 
	 * @param t
	 *            需要查询的对象
	 * @return
	 */
	public long getDataCount(T t) {
		long count = 0;
		try {

			Session session = sessionFactory.openSession();
			Query query = session.createQuery("select count(*) from "
					+ t.getClass().getSimpleName() + " s ");
			count = (Long) query.uniqueResult();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>获取表中数据条数出错");
		}
		return count;
	}

	private void addException() throws Exception {
		throw new Exception("no this method");
	}

	/**
	 * 用于表示无限制条件的接口
	 */
	public interface NULL {}

	/**
	 * 获取到当前业务需要的Criteria
	 * 
	 * @param t
	 * @param o1
	 * @param o2
	 * @param limitStr
	 * @param session
	 * @return
	 */
	protected Criteria getCriteria(T t, E o1, E o2, String limitStr,
			Session session) {
		Criteria criteria = session.createCriteria(t.getClass());

		doBussiness(t, criteria);

		// 附加限制条件（处理日期部分）
		if (! (o1 instanceof NULL) && o1 != null || o2 != null) {
			if (o1 != null && o2 != null) {
				// 在两个时间段之间进查找
				criteria.add(Restrictions.between(limitStr, o1, o2));
			} else if (o1 != null) {
				// 左边有值，所以查找大于等于当前值的数据
				criteria.add(Restrictions.ge(limitStr, o1));
			} else if (o2 != null) {
				// 右边有值，所以查找小于当前值的数据
				criteria.add(Restrictions.le(limitStr, o2));
			}
		}

		return criteria;
	}

	/**
	 * 每个业务中的限制条件的拼接
	 * 
	 * @param t
	 * @param criteria
	 * @return
	 */
	abstract protected void doBussiness(T t, Criteria criteria);
}
