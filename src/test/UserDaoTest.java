package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/spring-core.xml")
public class UserDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Resource(name = "userDAOImpl")
	private BaseDAO<User,NULL> ud;

	//查询指定用户名密码的数据	
	@Test
	public void fun() {
		User u = new User();
		u.setUsername("root1");
		u.setPassword("123");
		List<User> list = ud.query(u);
		System.out.println(list);
		for(User u1 : list) {
			System.out.println(u1.getName());
		}
	}

  //添加一个不存在的用户
	@Test
	public void fun2() {
		User u1 = new User();
		u1.setUsername("root");
		u1.setPassword("root");
		u1.setName("张山");
		u1.setSex("男");
		u1.setState("可用");
		u1.setAuthority("系统管理员");
		boolean list = ud.insert(u1);
		System.out.println(list);
	}
	
}
