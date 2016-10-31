package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.carManage.service.UserService;

public class SHUserServiceTest extends BaseDAOTest{
	@Resource(name = "userServiceImpl")
	UserService us;
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
	public void createisOK(){
		
		System.out.println(us.checklogin(json));
	}
	@Test
	public void loginisOK(){
		
		System.out.println(us.checklogin(json));
	}
	@Test
	public void deleteisOK(){
		
		System.out.println(us.deleteUser(json));
	}
}
