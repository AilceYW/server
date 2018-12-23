package test.serverframe.armc.server.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumAccessory;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.manager.service.DatumAccessoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/9/29 18:04
 * @Description:
 */
@CrossOrigin(origins = "*")
@Api(value = "资料附件")
@Validated
@RestController
@RequestMapping("/file")
public class DatumAccessoryController extends BaseController {

    @Autowired
    private DatumAccessoryService service;
    /**
     * @Descripttion 资料附件上传
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:10
     * @Param [request, file, myfile, fileName, size, blobname, count, blobcut]
     * @Return
     **/
    @PostMapping("/upload")
    @ApiOperation(notes = "资料附件上传", value = "资料附件上传", httpMethod = "POST")
    public ResultDto<Boolean> upload(HttpServletRequest request, @RequestBody MultipartFile[] file, @RequestParam("myfile") long myfile, @RequestParam("filename") String fileName,
                                     @RequestParam("size") long size, @RequestParam("blobname") int blobname, @RequestParam("count") int count,
                                     @RequestParam("blobcut") boolean blobcut) {
        ResultDto<Boolean> result = null;
        try {
            boolean falg  = service.uploadFile(file, fileName, blobname, size, myfile, count);
            result = ResultDtoUtil.success(falg);
        } catch (Exception e) {
            result = ResultDtoUtil.error(500, "服务器错误");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion  下载文件
     * @Author jiangyuanwei
     * @Date 2018/10/12 10:50
     * @Param [wjm]
     * @Return
     **/
    @GetMapping(value = "/download/{wjm}")
    @ApiOperation(notes = "资料下载", value = "资料下载", httpMethod = "GET")
    public ResultDto<Boolean> download(@PathVariable(name = "wjm") String wjm) {
        ResultDto<Boolean> result = null;
        boolean falg = true;
        try {
            DatumAccessory accessory = service.selectByPrimaryKey(wjm);
            if (falg) {
                responseStream(service.dowloadFile(accessory.getCclj()));
                result = ResultDtoUtil.success(falg);
            }else  {
                result = ResultDtoUtil.success(falg);
            }
        } catch (Exception e) {
            result = ResultDtoUtil.error(500, "服务器错误");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 上传文件
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:09
     * @Param
     * @Return
     **/
    @PostMapping("/uploads")
    @ApiOperation(notes = "资料上传", value = "资料上传", httpMethod = "POST")
    public ResultDto<Boolean> uploads(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) {
        ResultDto<Boolean> result = null;
     try {
            boolean falg = service.upload(file,request);
         result = ResultDtoUtil.success(falg);
        } catch (Exception e) {
            System.err.println("出错了！500");
            e.printStackTrace();
        }
        return result;
    }
    /**  
     * @Descripttion  删除资料附件
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:09
     * @Param [accessory]
     * @Return 
     **/
    @PostMapping("/delete")
    @ApiOperation(notes = "资料删除", value = "资料删除", httpMethod = "POST")
    public ResultDto<Integer> deleteFile(@RequestBody DatumAccessory accessory) {
        ResultDto<Integer> result;
        try {
            int mbrValues = service.deleteFile(accessory.getFjid());
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }
    /**
     * @Descripttion  添加资料附件
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:08
     * @Param [accessory]
     * @Return
     **/
    @PostMapping("/addFile")
    @ApiOperation(notes = "添加资料附件对象", value = "添加资料附件对象", httpMethod = "POST")
    public ResultDto<Integer> addFile(@RequestBody DatumAccessory accessory) {
        ResultDto<Integer> result;
        try {
            int mbrValues = service.addFile(accessory);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping("/addDatumFile")
    @ApiOperation(notes = "添加资料附件对象", value = "添加资料附件对象", httpMethod = "POST")
    public ResultDto<Integer> addDatumFile(@RequestBody Datum datum) {
        ResultDto<Integer> result;
        try {
            int mbrValues = service.addDatumFile(datum,null,null,null);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }
    /**  
     * @Descripttion  获取资料的文件列表
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:08
     * @Param [accessory]
     * @Return 
     **/
    @PostMapping("/getList")
    @ApiOperation(notes = "获取资料的文件列表", value = "获取资料的文件列表", httpMethod = "POST")
    public ResultDto<List<DatumAccessory>> getAllDatumAccessory(@RequestBody DatumAccessory accessory) {
        ResultDto<List<DatumAccessory>> result;
        try {
            List<DatumAccessory> lists = service.getAllDatumAccessory(accessory.getZlbh());
            result = ResultDtoUtil.success(lists);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }
}
