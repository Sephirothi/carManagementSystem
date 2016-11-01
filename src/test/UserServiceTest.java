package test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.DataFormatException;

import org.junit.Test;

import com.carManage.model.User;
import com.carManage.utils.GsonUtils;

public class UserServiceTest {

	String json = "";

	// @Before
	public void before() {
		File file = new File("E:/EEout/sshCarManage/src/test/json.txt");
		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] buff = new byte[1024];
				int len = 0;
				while ((len = fis.read(buff)) != -1) {
					json = json + new String(buff, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Test
	public void test() {
		// System.out.println(json);
		// UserService us = new UserServiceImpl();
		// String gson = us.querySingleUser(json);
		// System.out.println(gson);
		Map<String, String> map = null;
		try {
			map = GsonUtils.jsonToMaps(json);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		try {
			User u = getMaptoObject(map, User.class);
			System.out.println(u);
			// System.out.println(map.get("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注意这里必须传输单个object对象
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private <T> T getMaptoObject(Map<String, String> map, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T t = clazz.newInstance();
		Field[] fds = clazz.getDeclaredFields();
		for (Field field : fds) {
			String typename = field.getType().getTypeName();
			String fieldname = field.getName();
			Object obj = map.get(fieldname);
			if (obj == null) {
				continue;
			}
			if (typename.equals(String.class.getName())) {
				PropertyDescriptor pd;
				try {
					pd = new PropertyDescriptor(field.getName(), clazz);
				} catch (IntrospectionException e) {
					continue;
				}
				pd.getWriteMethod().invoke(t, obj);
			}
		}
		return t;
	}

	@Test
	public void testdate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse("1994-09-19");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected Date StringtoDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date.trim());
	}
}
