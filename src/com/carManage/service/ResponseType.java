package com.carManage.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
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
				System.out.println("objectTojson");
				result = GsonUtils.objectToJson(lrb);
			}
			System.out.println("count");
			return result;
		}

	}

	/**
	 * 异常别介意!==
	 * 
	 * @param map
	 * @param clazz  需要转化的类型
	 * @return T 返回需要的类型
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public <T> T getMaptoObject(Map<String, String> map, Class<T> clazz)
			throws  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		T t = clazz.newInstance();
		Field[] fds = clazz.getDeclaredFields();
		for (Field field : fds) {
			String typename = field.getType().getTypeName();
			String fieldname = field.getName();
			Object obj = map.get(fieldname);
			if (obj == null) {
				continue;
			}
			// 这里后面还会加判断
			// TODO 根据需求更改
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
	/**
	 * 
	 * @param date 日期字符串
	 * @return  返回日期 java.util.Date
	 * @throws ParseException
	 */
	protected Date StringToDate(String date) throws ParseException {
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date.trim());
	}
	/**
	 * 
	 * @param page 前端传来的和页面相关的字符串
	 * @return 将String转化为Integer
	 */
	protected Integer stringToInteger(String page){
		if(page==null){
			return null;
		}
		return Integer.parseInt(page);
	}
}
