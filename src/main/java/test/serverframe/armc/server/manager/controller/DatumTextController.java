package test.serverframe.armc.server.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumAccessory;
import test.serverframe.armc.server.manager.domain.DatumText;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.manager.service.DatumAccessoryService;
import test.serverframe.armc.server.manager.service.DatumService;
import test.serverframe.armc.server.manager.service.DatumTextService;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/5 16:13
 * @Description:
 */
@CrossOrigin(origins = "*")
@Api(value = "资料正文")
@Validated
@RestController
@RequestMapping("/datumText")
public class DatumTextController {

    @Autowired
    private DatumTextService textService;
    @Autowired
    private DatumService datumService;
    @Autowired
    private DatumAccessoryService datumAccessoryService;


    @Autowired
    private DatumService service;

    /**
     * @Descripttion 根据资料id查询详情
     * @Author jiangyuanwei
     * @Date 2018/10/5 14:38
     * @Param [datum]
     * @Return
     **/
    @GetMapping("/details")
    @ApiOperation(notes = "查询详情", value = "查询详情", httpMethod = "GET")
    public ResultDto<Datum> selectDetails(@RequestParam("zlbh") String zlbh) {
        ResultDto<Datum> result;
        try {
            List<DatumAccessory> lists = datumAccessoryService.getAllDatumAccessory(zlbh);
            Datum datum = new Datum();
            if (!CollectionUtils.isEmpty(lists)) {
                datum.setFjbs((short)1);
            }else {
                datum.setFjbs((short)0);
            }
            datumService.updateByPrimaryKeySelective(datum);
            Datum datums = textService.getFtpHtmlData(zlbh);
            if(lists.size() != 0) {
                datums.setFj(true);
            }else {
                datums.setFj(false);
            }
            result = ResultDtoUtil.success(datums);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 修改资料正文
     * @Author jiangyuanwei
     * @Date 2018/10/12 15:50
     * @Param [datumText]
     * @Return
     **/
    @PostMapping("/updateDatumText")
    @ApiOperation(notes = "修改资料正文", value = "修改资料正文", httpMethod = "POST")
    public ResultDto<Integer> updateDatumText(@RequestBody DatumText datumText) {
        ResultDto<Integer> result;
        try {
            int count = textService.updateByPrimaryKey(datumText);
            result = ResultDtoUtil.success(count);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
        }
        return result;
    }

    @PostMapping("/addDatumText")
    @ApiOperation(notes = "添加资料正文", value = "添加资料正文", httpMethod = "POST")
    public ResultDto<Integer> addDatumText(@RequestBody DatumText datumText) {
        ResultDto<Integer> result;
        try {
            int count = textService.insert(datumText);
            result = ResultDtoUtil.success(count);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 查询资料的资料正文
     * @Author jiangyuanwei
     * @Date 2018/10/12 15:50
     * @Param [datumText]
     * @Return
     **/
    @PostMapping("/getAllDatumText")
    @ApiOperation(notes = "查询资料的资料正文", value = "查询资料的资料正文", httpMethod = "POST")
    public ResultDto<DatumText> getAllDatumText(@RequestBody DatumText datumText) {
        ResultDto<DatumText> result;
        try {
            DatumText text = textService.getAllDatumText(datumText.getZlbh());
            result = ResultDtoUtil.success(text);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 资料互转
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:10
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/add/data")
    @ApiOperation(notes = "资料互转", value = "资料互转", httpMethod = "POST")
    public ResultDto<Integer> addDatumAndDatumText(@ApiParam("资料正文模型") @RequestBody DatumText param) {
        ResultDto<Integer> result;
        try {
            int num = textService.addDatumTextAll(param);
            result = ResultDtoUtil.success(num);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

}
