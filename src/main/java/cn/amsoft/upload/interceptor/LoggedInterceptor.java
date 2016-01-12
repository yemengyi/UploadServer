package cn.amsoft.upload.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Repository  
public class LoggedInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("------afterCompletion------");
		super.afterCompletion(request, response, handler, ex);
		
	} 

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("------afterConcurrentHandlingStarted------");
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("------postHandle------");
		request.setCharacterEncoding("GBK");  
        response.setCharacterEncoding("GBK");  
        response.setContentType("text/html;charset=GBK"); 
        HttpSession session = request.getSession();
  
        // 后台session控制   
        String[] noFilters = new String[] { "index.jsp"};  
        String uri = request.getRequestURI(); 
        //只过滤views下面的文件
        if (uri.indexOf("views") != -1 || uri.indexOf(".do")!= -1) {  
            boolean beFilter = true;  
            for (String s : noFilters) {  
                if (uri.indexOf(s) != -1) {  
                    beFilter = false;  
                    break;  
                }  
            }  
            if (beFilter) {  
                /*Object obj = request.getSession().getAttribute("user");  
                if (null == obj) {  
                    // 未登录   
                	modelAndView.addObject("MESSAGE","请先登录");
                	modelAndView.setViewName("index");
                } else {  
                    
                }  */
            }  
        }  
  
		super.postHandle(request, response, handler, modelAndView);
	}

}
