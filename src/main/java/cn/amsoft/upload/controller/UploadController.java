package cn.amsoft.upload.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.amsoft.upload.global.Constant;
import cn.amsoft.upload.util.FileUploadUtil;

import com.ab.model.AbResult;
import com.ab.util.AbDateUtil;
import com.ab.util.AbStrUtil;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory
			.getLogger(UploadController.class);

	@Autowired
	private HttpSession session = null;

	@Autowired
	private HttpServletRequest request = null;

   /**
	 * 描述：增加建筑.
	 * @return
	 */
	@RequestMapping(value = "/upload.do", method = {
			RequestMethod.POST})
	public ModelAndView upload() {
		
		FileUploadUtil upload;
		String imgUrl = null;
		ModelAndView mav = new ModelAndView();
		AbResult result = new AbResult();
		//部署根目录
		String webPath =  null;
		try {
			String newPath = session.getServletContext().getRealPath(Constant.IMAGES_PATH);
			//部署根目录
			webPath = session.getServletContext().getRealPath("/");
			//按日期存储
			String folder = AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD);
			upload = new FileUploadUtil(new File(newPath+File.separator+folder));
			// 开始上传文件,文件名和路径
			HashMap<String,String> filePaths  = upload.download(request,"GBK");
						
			//没有文件域的时候从request中获取
			String data1 = request.getParameter("data1");
			String data2 = request.getParameter("data2");
			if(!AbStrUtil.isEmpty(data1)){
				logger.debug("request data1:"+data1);
			}else{
				//有文件域的时候从upload中获取
				data1 = upload.getParameter("data1");
			}
			if(!AbStrUtil.isEmpty(data1)){
				try {
					data1 = URLDecoder.decode(data1,"UTF-8");
					logger.debug("upload data1:"+data1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!AbStrUtil.isEmpty(data2)){
				logger.debug("request upload data2:"+data2);
			}else{
				data2 = upload.getParameter("data2");
				logger.debug("upload data2:"+data2);
			}
			
			
			Set<Entry<String, String>> set = filePaths.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String key = (String) entry.getKey();
				String path = filePaths.get(key);
				logger.debug("上传后的图片路径："+path);
				
				int start2 = path.indexOf(Constant.WEB_ROOT);
				if(start2!=-1){
					 path = path.substring(start2+Constant.WEB_ROOT.length());
				}
				imgUrl = path;
				logger.debug("显示的图片路径："+imgUrl);
				imgUrl = imgUrl.replace('\\', '/');
				
			}
			
			int resultCode = 200;
			String resultMessage = imgUrl;
			//imgUrl = webPath+ imgUrl;
			result.setResultCode(resultCode);
			result.setResultMessage(resultMessage);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof SizeLimitExceededException){
				result.setResultCode(-4);
				result.setResultMessage("文件大小超出限制");
				FileUploadUtil.deleteFile(imgUrl);
			}
		}
		
		mav.addObject("result",result.toJson());
		mav.setViewName("public/result");
		logger.debug("upload result:"+result.toJson());
		return mav;

	}
   
}
