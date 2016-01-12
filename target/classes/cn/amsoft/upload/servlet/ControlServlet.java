package cn.amsoft.upload.servlet;



import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * HttpServlet����
 * ��web.xml������,ͨ���������ò�ͬ�Ĵ����༰����,������struts��ActionServlet���á�
 * ʹ��: /FormServlet?methodName=add
 * methodName=add :��ʾҪ���õķ�������
 * className : ��ʾ������������������
 *    <servlet>
 *       <servlet-name>FormServlet</servlet-name>
 *       <servlet-class>com.zhaoqp.servlet.ControlServlet</servlet-class>
 *       <init-param>
 *            <param-name>className</param-name> 
 *            <param-value>com.zhaoqp.servlet.UserFormHandle</param-value>
 *        </init-param>
 *    </servlet> 
 *    <servlet-mapping>
 *       <servlet-name>FormServlet</servlet-name>
 *       <url-pattern>/FormServlet</url-pattern>
 *    </servlet-mapping>
 *    
 * @author zhaoqp
 * @email:zhaoqp2010@163.com
 * @version 1.0
 */
public class ControlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			try {
				String methodName = request.getParameter("methodName");
				String className = this.getInitParameter("className");
				
				if(className==null || "".equals(className)){
					return ;
				}
				if(methodName==null || "".equals(methodName)){
					return ;
				}
				
				Class c = Class.forName(className);
				Object obj = c.newInstance();
				Method m = c.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
				m.invoke(obj,request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void init() throws ServletException {
	}
}
