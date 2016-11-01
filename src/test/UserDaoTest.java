package test;

<<<<<<< HEAD
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
=======
import java.util.Date;
import java.util.List;
>>>>>>> branch 'master' of ssh://git@github.com/47oo/sshCarManage.git

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.User;
import com.carManage.utils.HibernateUtils;

import edu.emory.mathcs.backport.java.util.LinkedList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/spring-core.xml")
public class UserDaoTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "sessionFactory")
	SessionFactory sf;

	@Test
<<<<<<< HEAD
	public void createOK(){
=======
	public void testt() {
		Session session = sf.openSession();

//		for(int i = 0; i < 20; i++) {
//			session.beginTransaction();
//			User u = new User();
//			u.setPassword("123");
//			u.setSex("男");
//			u.setAuthority("3");
//			u.setState("可用");
//			u.setDate(new Date());
//			u.setName("test" + i);
//			u.setUsername("testUs" + i);
//			session.save(u);
//			session.getTransaction().commit();
//		}

//		System.out.println(userQuery(u));
//		testFenYe();
		
//		User user = new User();
//		user.setState("可用");
//		user.setName("");
//		List<User> query = query(user, 110, 20, null, null);
//		for(User u : query) {
//			System.out.println(u.getName());
//		}
>>>>>>> branch 'master' of ssh://git@github.com/47oo/sshCarManage.git
		
	}
	public List<User> query(User t, int start, int count, NULL o1, NULL o2) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(User.class);
		// 此时表示查询多个
		if (!t.getName().equals(""))
			criteria.add(Restrictions.eq("name", t.getName()));
		if (!t.getState().equals("全部"))
			criteria.add(Restrictions.eq("state", t.getState()));
		criteria.setFirstResult(start);
		criteria.setMaxResults(count);
		return criteria.list();
	}
	
	public void testFenYe() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		
		criteria.add(Restrictions.eq("state", "可用"));
		criteria.setFirstResult(81);
		criteria.setMaxResults(90);
		List<User> list = criteria.list();
		System.out.println(list.size());
		for(User u : list) {
			System.out.println(u.getName());
		}
		
	}

	public boolean insert(User t) {
		Session session = sf.openSession();
		session.beginTransaction();

		try {
			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>保存失败");
			return false;
		}

		return true;
	}

	public int delete(List<User> list) {
		Session session = sf.openSession();

		int successCount = 0;
		// 对集合中的User进行循环删除
		for (User user : list) {
			session.beginTransaction();

			try {
				// 查询
				String hql = "from User u where u.username = ?";
				Query query = session.createQuery(hql);
				query.setParameter(0, user.getUsername());
				User tempUser = (User) query.uniqueResult();
				if (tempUser == null) {
					// 表示表中并没有这个数据
					continue;
				}
				session.delete(tempUser);
			} catch (Exception e) {
				System.out.println("======>删除失败：" + user.getUsername());
				e.printStackTrace();
			}
			successCount++;
			session.getTransaction().commit();
		}
		return successCount;
	}

	public List<User> userQuery(User t) {
		 Session session = sf.openSession();
//		Session session = HibernateUtils.openSession();
		Criteria criteria = session.createCriteria(User.class);
		String username = t.getUsername();
		List<User> resultList = null;
		if (username == null || username.equals("")) {
			// 此时表示查询多个

			if (!t.getName().equals(""))
				criteria.add(Restrictions.eq("name", t.getName()));
			if (!t.getState().equals("全部"))
				criteria.add(Restrictions.eq("state", t.getState()));
		} else {
			// 此时表示查询单个
			criteria.add(Restrictions.eq("username", t.getUsername()));
		}
		resultList = criteria.list();
		session.close();
		return resultList;
	}

	public boolean updateUser(User t) {
		Session session = null;
		try {
			String username = t.getUsername();
			if (username == null || username.equals("")) {
				System.out.println("======>username为空");
				return false;
			}
			session = sf.openSession();
			session.beginTransaction();

			String hql = "from User u where u.username = ?";
			System.out.println("======>构建的HQL为 :" + hql);
			Query query = session.createQuery(hql);
			query.setParameter(0, t.getUsername());
			User user = (User) query.uniqueResult();
			if (user == null) {
				System.out.println("======>数据库中查询没有此用户");
				return false;
			}
			// 更新User的数据
			user.updateUser(t);
			// session.update(user);

			session.getTransaction().commit();
		} catch (TransientObjectException ex) {
			ex.printStackTrace();
			System.out.println("===============>发生异常，添加失败");
			return false;
		} finally {
			if (session != null)
				session.close();
		}
		return true;
	}
}
