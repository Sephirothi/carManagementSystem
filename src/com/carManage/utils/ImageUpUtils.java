package com.carManage.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

public class ImageUpUtils {
	/**
	 * 文件上传
	 * 
	 * @param request
	 *            HttpServletRequest的请求
	 * @return 返回上传成功的数量暂时版
	 */
	public static String uploadImage(HttpServletRequest request) {
		// 用于记录上传文件成功的次数
		// 判断是否有文件上传的请求
		String result = null;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String savepath = request.getServletContext().getRealPath("/") + "uploadimages\\";
		if (isMultipart) {
			ServletContext servletContext = request.getServletContext();
			// 设置临时文件地址,默认根目录下面
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			DiskFileItemFactory dfi = newDiskFileItemFactory(servletContext, repository);
			ServletFileUpload upload = new ServletFileUpload(dfi);
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String temp = processUploadedFile(item, savepath);
					if (!item.isFormField() && temp!=null) {
						result=""+temp; 
					}
				}
			} catch (FileUploadException e) {
				
			}
		}
		return result;
	}

	/**
	 * 创建工厂类
	 * 
	 * @param context
	 * @param repository
	 * @return
	 */
	private static DiskFileItemFactory newDiskFileItemFactory(ServletContext context, File repository) {
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);
		DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		return factory;
	}

	/**
	 * 判断文件是否上传成功
	 * 
	 * @param item
	 * @param savePath
	 * @return
	 */
	private static String processUploadedFile(FileItem item, String savePath) {
		// System.out.println(savePath);
		String realpath = null;
		String fileName = item.getName();
		long sizeInBytes = item.getSize();
		String type = fileName.substring(fileName.lastIndexOf("."));
		// System.out.println(type);
		// 支持类型及大小
		if (".jpg.gif.png".contains(type) && sizeInBytes < 1024 * 500) {
			try {
				// 生成唯一标识
				String uuid = UUID.randomUUID().toString();
				// System.out.println(savePath+uuid+type);
				realpath = savePath + uuid + type;
				item.write(new File(realpath));
				// TODO 等数据库的资源将src放入进去
			} catch (Exception e) {
				System.out.println("文件类型异常");
			}
		}
		return realpath;
	}

}
