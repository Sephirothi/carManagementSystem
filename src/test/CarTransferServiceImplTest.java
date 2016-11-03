package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.service.CarTransferService;
import com.carManage.utils.GsonUtils;

public class CarTransferServiceImplTest extends BaseDAOTest{
	@Resource(name="carTransferServiceImpl")
	CarTransferService cts;
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
	public void testQueryAll(){
		System.out.println(cts.queryCarTransfers(json));
	}
	@Test
	public void testSingleQuery(){
		System.out.println(cts.querySingle(json));
	}
	@Test
	public void testadd(){
		System.out.println(cts.addCarTransfers(json));
	}
}
