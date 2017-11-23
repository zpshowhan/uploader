package org.esmart.zl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ProgressServlet
 */
@WebServlet("/progress")
public class ProgressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProgressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getSession().getId();
		String filename = request.getParameter("filename");
		System.out.println(filename);
		//使用sessionid + 文件名生成文件号，与上传的文件保持一致
		id = id + filename;
		Object size = Progress.get(id + "Size");
		size = size == null ? 100 : size;
		Object progress = Progress.get(id + "Progress");
		progress = progress == null ? 0 : progress; 
		JSONObject json = new JSONObject();
		json.put("size", size);
		json.put("progress", progress);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().print(json.toString());
	}

}
