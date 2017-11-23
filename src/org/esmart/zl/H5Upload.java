package org.esmart.zl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.esmart.zl.Progress;

/**
 * Servlet implementation class H5Upload
 */
@WebServlet("/H5Upload")
public class H5Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public H5Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4*1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> fileItems;
		try {
			fileItems = upload.parseRequest(new ServletRequestContext(request));
			//获取文件域
			FileItem fileItem = fileItems.get(0);
			//使用sessionid + 文件名生成文件号
			String id = request.getSession().getId() + fileItem.getName();
			//文件进度长度
			long progress = 0;
			//用流的方式读取文件，以便可以实时的获取进度
			InputStream in = fileItem.getInputStream();
			String filePath="D:/home/"+UUID.randomUUID().toString()+fileItem.getName();
			File file = new File(filePath);
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int readNumber = 0;
			while((readNumber = in.read(buffer)) != -1){
			//每读取一次，更新一次进度大小
			progress = progress + readNumber;
			out.write(buffer);
			}
			in.close();
			out.close();
			} catch (FileUploadException e) {
			e.printStackTrace();
			}
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write("done");
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
