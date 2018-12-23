package test.serverframe.armc.server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Auther: Administrator
 * @Date: 2018/10/25 19:49
 * @Description:
 */
public class Tes {

    @Value("${url.path}")
    private static String path;
    public static void main(String[] args) throws IOException {
        HttpServletRequest request ;
        //request.getServletContext().getResource(  )
       // FTPUtil.downLoadFile("/media/"+0+".png",0+".png",this.getClass().getResource().getPath());
        //String path = ResourceUtils.getURL().getPath();
        // String  path = Tes.class.getClassLoader().getResources("static/");
        //下载图片
        FTPUtil.downLoadFile("/media/"+0+".png",0+".png","src/main/resources/static/");
        String s = "123";
        String s1 = "123";
        System.out.println(s.equals(s1));
    }
}
