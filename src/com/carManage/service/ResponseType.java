package com.carManage.service;

import java.util.zip.DataFormatException;
import com.carManage.utils.GsonUtils;

public class ResponseType {
	private static class ResponseBody<T> {
		public int code;
		public T data;

		public ResponseBody() {
		}
	}

	private static class ListResponseBody<T> extends ResponseBody<T> {
		public int count;

		public ListResponseBody() {
		}
	}
	/**
	 * 
	 * @author hp
	 *
	 * @param <T> T 确定data的类型
	 * 返回的json
	 */
	protected static class ReturnResponse<T> {
		public ReturnResponse() {

		}

		/**
		 * 不会抛出异常
		 * 
		 * @param code  int
		 * @param msg  String
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
		 * @param count
		 *            是否有页数
		 * @return
		 * @throws DataFormatException
		 */
		public String extracted(int code, T data, Integer count) throws DataFormatException {
			String result = null;
			if (count == null) {
				ResponseBody<T> rb = new ResponseBody<>();
				rb.code = code;
				rb.data = data;
				result = GsonUtils.objectToJson(rb);
			} else {
				ListResponseBody<T> lrb = new ListResponseBody<>();
				lrb.code = code;
				lrb.data = data;
				lrb.count = count;
				result = GsonUtils.objectToJson(lrb);
			}
			return result;
		}

	}
}
