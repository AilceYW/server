/********************************************
 * 服务端启动主类
 *
 * @author zwq
 * @create 2018-07-26 22:23
 *********************************************/

package test.serverframe.armc.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("test.serverframe.armc.server.manager.dao.mapper")
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ServerApplication {
    public static void main(String[] args) {

        // 启动管理端服务
        SpringApplication.run(ServerApplication.class);
    }


}
