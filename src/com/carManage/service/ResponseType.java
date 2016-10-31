package com.carManage.service;

import java.util.zip.DataFormatException;

import com.carManage.service.ResponseType.ResponseBody;
import com.carMmanage.utils.GsonUtils;

public class ResponseType {
	protected static class ResponseBody<T> {
		public int code;
		public T data;

		public ResponseBody() {
		}
	}

	protected static class ListResponseBody<T> extends ResponseBody<T> {
		public int count;

		public ListResponseBody() {
		}
	}
	/**
	 * 转化失败返回的json数据
	 * @return  返回失败的json类型
	 */
	protected String extracted(int code,String msg) {
		ResponseBody<String> rb = new ResponseBody<>();
		String result = null;
		rb.code = code;// 表示json转化失败
		rb.data = msg;
		try {
			result = GsonUtils.objectToJson(rb);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
}
