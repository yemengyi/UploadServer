package cn.amsoft.upload.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.amsoft.upload.global.Dictionary;

public class PublicHandle {

	
	/**
	 * 描述：获取客户端广告
	 * @param key
	 */
	public void getSetting(HttpServletRequest request,HttpServletResponse response) { 
		response.setContentType("text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			String key = request.getParameter("key");
			out = response.getWriter();
			Object o = Dictionary.setting.get(key);
			if(o!=null){
				out.print(o.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	
}
