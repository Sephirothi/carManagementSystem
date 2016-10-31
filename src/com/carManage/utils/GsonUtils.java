package com.carManage.utils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json转换工具 通过getList获取到成员为cls的集合。 通过getObject获得类型为cls的对象。
 * 
 * @author admin
 *
 */
public class GsonUtils {
	private GsonUtils() {
	}

	private static class InstanceHolder {
		public static Gson gson = new Gson();
	}

	private static Gson gson = InstanceHolder.gson;

	/**
	 * 将json字符串转换为成员为cls类型的List集合</br> 例子：
	 * 
	 * <pre>
	 * List&ltPerson&gt list = GsonUtils.getList(jsonStr , Person.class);
	 * </pre>
	 * 
	 * @param json
	 *            需要转换的字符串
	 * @param cls
	 *            成员类型
	 * @return 成员为cls的List
	 * @throws DataFormatException
	 */
	public static <T> List<T> jsonToList(String json, Class<T> cls)
			throws DataFormatException {
		Type type = new TypeToken<List<T>>() {
		}.getType();
		List<T> result = null;
		List<T> re = null;
		if (gson != null) {

			try {
				result = gson.fromJson(json, type);
			} catch (Exception e) {
				throw new DataFormatException("解析json时出错");
			}
			re = new LinkedList<T>();
			for (T t : result) {
				T obj = jsonToObject(t.toString(), cls);
				re.add(obj);
			}
		}
		return re;
	}

	/**
	 * 将json字符串转换为成员为cls类型的对象 例子：
	 * 
	 * <pre>
	 * Person obj = GsonUtils.&ltPerson&gtgetObject(jsonStr , Person.class);
	 * </pre>
	 * 
	 * @param json
	 *            需要转换的字符串
	 * @param cls
	 *            成员类型
	 * @return 成员为cls的对象
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String json, Class<T> cls)
			throws DataFormatException {
		T result = null;
		if (gson != null) {
			try {
				result = gson.fromJson(json, cls);
			} catch (Exception e) {
				throw new DataFormatException("解析json时出错");
			}
		}
		return result;
	}

	/**
	 * 将json字符串转为map
	 * 
	 * @param gsonString
	 * @return
	 * @throws DataFormatException
	 */
	public static  Map<String, String> jsonToMaps(String gsonString)
			throws DataFormatException {
		Map<String, String> map = null;
		if (gson != null) {
			try {
				map = gson.fromJson(gsonString,
						new TypeToken<Map<String, String>>() {
						}.getType());
			} catch (Exception e) {
				throw new DataFormatException("解析json时出错");
			}
		}
		return map;
	}

	/**
	 * 将Object转化为Json
	 * 
	 * @param obj
	 * @return
	 * @throws DataFormatException
	 */
	public static <T> String objectToJson(Object obj)
			throws DataFormatException {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			throw new DataFormatException("json解析出错");
		}
	}

	/**
	 * 将List转化为json
	 * 
	 * @param list
	 * @return
	 * @throws DataFormatException
	 */
	public static <T> String listToJson(List<T> list)
			throws DataFormatException {
		try {
			return gson.toJson(list, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			throw new DataFormatException("json解析出错");
		}
	}

}
