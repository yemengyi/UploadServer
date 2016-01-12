package cn.amsoft.upload.global;

import java.io.File;
/**
 * ����
 * @author zhaoqp
 */
public class Constant {
	
	public static final String CHARSET_NAME="gbk";
	
    //·��
    public static final String UPLOADFILES = "uploads";
    public static final String SEPARATOR = File.separator;
    
    //Ȩ��
    public static final int ADMINRIGHT = 0;
    public static final int MEMBERRIGHT = 1;
    public static final String IMAGES_PATH = "/uploads/images";

    public static final String WEB_ROOT = "UploadServer";
   
    public static final int UPLOAD_MAX_FILESIZE = 10240000;
}
