package test.serverframe.armc.server.manager.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.domain.DatumType;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.util.FileResponse;
import test.serverframe.armc.server.util.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 图片上传
 * @author: yz
 * @create: 2018/8/16 11:09
 */
@CrossOrigin(origins = "*")
@Api(value = "资料类型")
@Validated
@Controller
@RequestMapping("/files")
public class FileUploadController {
    @Value("${ckeditor.storage.image.path}")
    private String ckeditorStorageImagePath;

    @Value("${ckeditor.storage.image.url}")
    private String http_path;

    /*
     * 图片命名格式
     */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * 上传图片文件夹
     */
    private static final String UPLOAD_PATH = "/upload/yzimg/";

    /*
     * 上传图片
     */
    @PostMapping("/upload/image")
    public ResultDto<String> uplodaImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        FileResponse fileResponse = new FileResponse();
        ResultDto<String> result = null;
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            logger.info("fileSize: " + file.getSize());
            // 图片大小不超过500K
            if (file.getSize() > 1024 * 500) {
                String error = fileResponse.error(0, "图片大小超过500K");
                out.println(error);
                out.flush();
                out.close();
            }
            // String proPath = request.getSession().getServletContext().getRealPath("/");
            //获取后缀名
            String proName = request.getContextPath();
            String path = "g:\\uplodFile\\img\\word\\media\\";
            //文件名
            String fileName = file.getOriginalFilename();
            //文件类型
            String uploadContentType = file.getContentType();
            String expandedName = "";
            if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
                expandedName = ".jpg";
            } else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
                expandedName = ".png";
            } else if (uploadContentType.equals("image/gif")) {
                expandedName = ".gif";
            } else if (uploadContentType.equals("image/bmp")) {
                expandedName = ".bmp";
            } else {
                String error = fileResponse.error(0, "文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）");
                out.println(error);

            }
           /* DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) + expandedName;*/
            FileUtils.uploadFile(file.getBytes(), path, fileName);
            //返回的数据
            String success = fileResponse.success(1, fileName, http_path + fileName, null);
            out.println(success);
            out.flush();
            out.close();
            result = ResultDtoUtil.success(success);
            //  return;
        } catch (Exception e) {
            e.printStackTrace();
            String error = fileResponse.error(0, "系统异常");
            try {
                response.getWriter().println(error);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

}

