package test.serverframe.armc.server.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Auther: Administrator
 * @Date: 2018/9/30 09:37
 * @Description: C 层基类
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;



    /**
     * @Descripttion
     * @Author jiangyuanwei
     * @Date 2018/9/30 9:35
     * @Param [file]
     * @Return
     **/
    public void responseStream(File file) {
        try
                (
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())
                ) {
            /*
             * 下载响应头设置
             */
            String filename = URLEncoder.encode(file.getName(), "UTF-8");
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=\"" + filename + "\"");
            response.setHeader("Content-Length", String.valueOf(file.length()));
           // response.setHeader("content-disposition", "attachment;filename=hehe" + URLEncoder.encode(fileName, "UTF-8"));
            /*
             * 输入流读取文件，返回前端 输出流
             */
            byte[] buff = new byte[1024 * 10];
            int len;
            while ((len = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, len);
            }
            // 文件流输出完成后删除服务器临时文件
            //  FileUtil.deleteFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
