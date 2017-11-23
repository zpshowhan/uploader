package org.esmart.zl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import net.sf.json.JSONObject;


/**
 *需要满足的条件：
 *a、表单的method必须是post
 *b、表单的enctype属性必须是multipart/form-data
 *c、表单中提供<input type="file"/>类型的上传输入域 
 *
* @ClassName: Upload 
* @Description: TODO 文件上传
* @Company:方正
* @author zhaolei 
* @version 1.0 2017年8月14日 下午2:53:54
 */
/**
 * Servlet implementation class Upload
 */
@WebServlet("/uploader")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
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
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 我们建立一个servlet，在此servlet中开始准备上传时用到的类，也就是DiskFileItemFactory类，
		 * 和ServletFileuqLoad类，DiskFileItemFactory负责管理磁盘文件，ServletFileuqLoad类
		 * 负责上传和解析文件。下面是具体的代码。
		 */
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		List<String> filePaths=new ArrayList<String>();
		// 1.0. 检查是否是multipart/form-data类型的
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {  
            throw new RuntimeException(  
                    "The form's enctype attribute value must be multipart/form-data");  
        } 
		// 产生FileItem的工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置是否将上传文件已临时文件的形式保存在磁盘的临界值（以字节为单位的int值）
		factory.setSizeThreshold(4*1024);
		//因为解析器依赖于这个工厂，所以将这个工厂传进去
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> fileItems;
		try {
//			fileItems = upload.parseRequest(new ServletRequestContext(request));
			fileItems = upload.parseRequest(request);
			
//			FileItem fileItem = fileItems.get(0);
			if(!fileItems.isEmpty()&&fileItems!=null){
				
				//获取文件域
				for (FileItem fileItem: fileItems) {
					//contentType
					String contentType = fileItem.getContentType();
					System.out.println("contentType: "+contentType);
					//获取input[name]的属性名称
					String name=fileItem.getFieldName();
					System.out.println("name: "+name);
					//判断是否是文件流,不是文件，而是文本
					if(fileItem.isFormField()){
						System.out.println(name+": "+fileItem.getString("UTF-8"));
						 continue;
					}
					//文件名
					String fileName=fileItem.getName();
					
					System.out.println("fileName :"+fileName);
					if(fileName==null || fileName.trim().equals("")){
						     continue;
						 }
					//不同的浏览器得到的名字不一样
					int start = fileName.lastIndexOf("\\"); 
			        fileName = fileName.substring(start+1); 
					String[] suffix=fileName.split("\\.");
					//文件后缀名
					String suffixLast =suffix[suffix.length-1].toLowerCase();
					System.out.println("后缀名："+suffixLast);
					//用流的方式读取文件，以便可以实时的获取进度
					InputStream in = fileItem.getInputStream();
					String filePath="D:/home/"+UUID.randomUUID().toString()+fileName;
					filePaths.add(filePath);
					File file = new File(filePath);
					file.createNewFile();
					//1、可以使用流的方式
					FileOutputStream out = new FileOutputStream(file);
					byte[] buffer = new byte[1024*1024];
					int readNumber = 0;
					while((readNumber = in.read(buffer)) != -1){
						out.write(buffer);
					}
					in.close();
					out.flush();
					out.close();
					//2、可以用commons-fileupload中的方法
					/*try {
						fileItem.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					
				}
			}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			json.put("paths", filePaths);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(json.toString());
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
