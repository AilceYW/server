package test.serverframe.armc.server.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: Administrator  映射网络图片路径
 * @Date: 2018/10/10 14:52
 * @Description:
 */

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Value("${upload.filePath.path}")
    private String image_path;
    @Value("${ftp.img_path}")
    private String img_path;
    @Value("${ftp.path}")
    private String PATH;
    @Value("${ckeditor.storage.image.path}")
    private String getImage_path;
    @Value("${ckeditor.storage.image.url}")
    private String http_path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /*
         * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
         * 用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
         */
        registry.addResourceHandler("/upload/image/**").addResourceLocations(image_path);




        registry.addResourceHandler("/ftp/image/**").addResourceLocations(PATH);


        registry.addResourceHandler("/public/image/**").addResourceLocations(getImage_path);

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //阿里云(映射路径去除盘符)
        //registry.addResourceHandler("/ueditor/image/**").addResourceLocations("/upload/image/");
        //registry.addResourceHandler("/ueditor/video/**").addResourceLocations("/upload/video/");
        super.addResourceHandlers(registry);
    }
}
