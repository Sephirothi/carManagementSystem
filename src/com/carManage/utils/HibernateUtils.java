package com.carManage.utils;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class HibernateUtils {

	@Resource(name = "sessionFactory")
	private static SessionFactory sf;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("虚拟机关闭!释放资源");
				sf.close();
			}
		}));
	}

	public static org.hibernate.Session openSession() {
		Session session = sf.openSession();
		return session;
	}

	public static org.hibernate.Session getCurrentSession() {
		// 3 获得session
		Session session = sf.getCurrentSession();
		return session;
	}
}
