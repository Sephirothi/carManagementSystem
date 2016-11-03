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
	public void test(){
		List<CarTransfer> list = new ArrayList<>();
		CarTransfer ct = new CarTransfer();
		ct.setId(1235);
		Car c=new Car();
		c.setId("2368");
//		ct.setCar(c);
		list.add(ct);
		ReturnResponse<List<CarTransfer>> rr= new ReturnResponse<>();
		try {
			
			
			System.out.println(rr.extracted(0, list, 100l));
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static class ResponseBody<T> {
		public int code;
		public T data;

		public ResponseBody() {
		}
	}

	private static class ListResponseBody<T> extends ResponseBody<T> {
		public long count;

		public ListResponseBody() {
		}
	}

	/**
	 * 
	 * @author hp
	 *
	 * @param <T>
	 *            T 确定data的类型 返回的json
	 */
	protected static class ReturnResponse<T> {
		public ReturnResponse() {

		}

		/**
		 * 不会抛出异常
		 * 
		 * @param code
		 *            int
		 * @param msg
		 *            String
		 * @return
		 */
		public String extracted(int code, String msg) {
			ResponseBody<String> rb = new ResponseBody<>();
			String result = null;
			rb.code = code;
			rb.data = msg;
			try {
				result = GsonUtils.objectToJson(rb);
			} catch (DataFormatException e) {
				e.printStackTrace();
			}
			return result;
		}

		/**
		 * 
		 * @param code
		 *            状态码
		 * @param data
		 *            需要传输的数据
		 * @param l
		 *            是否有页数
		 * @return
		 * @throws DataFormatException
		 */
		public String extracted(int code, T data, Long l) throws DataFormatException {
			String result = null;
			if (l == null) {
				ResponseBody<T> rb = new ResponseBody<>();
				rb.code = code;
				rb.data = data;
				result = GsonUtils.objectToJson(rb);
			} else {
				ListResponseBody<T> lrb = new ListResponseBody<>();
				lrb.code = code;
				lrb.data = data;
				lrb.count = l;
				//System.out.println("objectTojson");
				result = GsonUtils.objectToJson(lrb);
			}
			//System.out.println("count");
			return result;
		}

	}
}
