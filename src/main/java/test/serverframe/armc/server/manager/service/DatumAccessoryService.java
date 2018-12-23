package test.serverframe.armc.server.manager.service;

import org.springframework.web.multipart.MultipartFile;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumAccessory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


public interface DatumAccessoryService extends BaseService<DatumAccessory, String> {
    /**
     * @Descripttion  断点上传文件
     * @Author jiangyuanwei
     * @Date 2018/10/12 10:55
     * @Param [file, fileName, t, size, myfile, blobname]
     * @Return
     **/
    boolean uploadFile(MultipartFile[] file, String fileName, int t, long size, long myfile, int blobname) throws IOException, InterruptedException;
    /**
     * @Descripttion  文件下载
     * @Author jiangyuanwei
     * @Date 2018/10/12 10:55
     * @Param [wjm]
     * @Return
     **/
    File dowloadFile(String wjm);
    /**
     * @Descripttion  上传文件
     * @Author jiangyuanwei
     * @Date 2018/10/12 10:56
     * @Param [files, request]
     * @Return
     **/
    boolean upload(MultipartFile[] files, HttpServletRequest request);
    /**
     * @Descripttion  删除文件
     * @Author jiangyuanwei
     * @Date 2018/10/12 10:56
     * @Param [fjId]
     * @Return
     **/
    int deleteFile(String fjId);

    /**
     * @Descripttion  查询资料附件列表
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:05
     * @Param [zlbh]
     * @Return
     **/
    List<DatumAccessory> getAllDatumAccessory(String zlbh);
    /**
     * @Descripttion  添加资料附件
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:25
     * @Param [accessory]
     * @Return
     **/
    int addFile(DatumAccessory accessory);

    /**
     * @Descripttion  资料分类上传后添加数据
     * @Author jiangyuanwei
     * @Date 2018/10/18 11:26
     * @Param [datum, name, data, suffixName]
     * @Return
     **/
    int addDatumFile(Datum datum, String name, String data, String suffixName);

}
