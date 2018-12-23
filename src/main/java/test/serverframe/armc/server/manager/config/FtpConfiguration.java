/********************************************
 * 文件相关配置类
 *
 * @author zwq
 * @create 2018-07-24
 *********************************************/

package test.serverframe.armc.server.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test.ftp")
public class FtpConfiguration {
    private static String host;            // FTP服务url
    private static String port;            // FTP服务端口
    private static String username;        // FTP服务用户名
    private static String password;        // FTP服务密码
    private static String root_path;       // FTP文件根路径
    private static String img_path;        //FTP图片路径

    public static String getHost() {
        return host;
    }
    @Value("${ftp.host}")
    public void setHost(String host) {
        FtpConfiguration.host = host;
    }

    public static String getPort() {
        return port;
    }
    @Value("${ftp.port}")
    public void setPort(String port) {
        FtpConfiguration.port = port;
    }

    public static String getUsername() {
        return username;
    }
    @Value("${ftp.username}")
    public void setUsername(String username) {
        FtpConfiguration.username = username;
    }

    public static String getPassword() {
        return password;
    }
    @Value("${ftp.password}")
    public void setPassword(String password) {
        FtpConfiguration.password = password;
    }

    public static String getRoot_path() {
        return root_path;
    }
    @Value("${ftp.root_path}")
    public void setRoot_path(String root_path) {
        FtpConfiguration.root_path = root_path;
    }


    public static String getImg_path() {
        return img_path;
    }
    @Value("${ftp.img_path}")
    public void setImg_path(String img_path) {
        FtpConfiguration.img_path = img_path;
    }
}
