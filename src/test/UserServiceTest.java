package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.carManage.service.UserService;
import com.carManage.service.impl.UserServiceImpl;

public class UserServiceTest {
	String json = "";
	@Before
	public void before() {
		File file = new File("E:/EEout/sshCarManage/src/test/json.txt");
		if(file.exists()){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] buff= new byte[1024];
				int len  = 0;
				while((len=fis.read(buff))!=-1){
					json = json+new String(buff,0,len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(fis!=null){
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
		System.out.println(json);
		UserService us = new UserServiceImpl();
		String gson = us.querySingleUser(json);
		System.out.println(gson);
	}
}
