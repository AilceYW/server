package test.serverframe.armc.server.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumType;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.manager.service.DatumTypeService;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/9/26 09:51
 * @Description:
 */
@CrossOrigin(origins = "*")
@Api(value = "资料类型")
@Validated
@RestController
@RequestMapping("/datumType")
public class DatumTypeController {

    @Autowired
    private DatumTypeService service;

    /**
     * 查詢資料類型
     *
     * @Descripttion
     * @Author jiangyuanwei
     * @Date 2018/10/8 13:54
     * @Param []
     * @Return
     **/
    @GetMapping("/selectAll")
    @ApiOperation(notes = "展示樹形资料类型数据", value = "展示樹形资料类型数据", httpMethod = "GET")
    public ResultDto<List<DatumType>> selectAlls() {
        ResultDto<List<DatumType>> result;
        try {
            List<DatumType> datumTypes = service.selectRoot();
            for (DatumType datumType : datumTypes) {
                //datumType.setRootId(datumType.getId());
                service.selectAllDatumType(datumTypes);
            }
            result = ResultDtoUtil.success(datumTypes);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;

    }

    /**
     * @Descripttion 添加资料
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:05
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/add")
    @ApiOperation(notes = "添加资料类型", value = "添加资料类型", httpMethod = "POST")
    public ResultDto<Integer> addDatum(@ApiParam("资料类型模型") @RequestBody DatumType datumType) {
        ResultDto<Integer> result;
        try {
            // datumType.setId(StringUtil.getId());
            List<DatumType> datumTypes = service.selectRoot();
            for (DatumType type : datumTypes) {
                if (type.getId().equals(datumType.getRootId()) && type.getFlmc().equals("公共资料")) {
                    datumType.setFllx("公共");
                } else {
                    datumType.setFllx("个人");
                }
            }
            //默认描述就是名称
            datumType.setFlms(datumType.getFlmc());
            int mbrValues = service.insertSelective(datumType);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 修改资料
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:10
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/update")
    @ApiOperation(notes = "修改资料类型", value = "修改资料类型", httpMethod = "POST")
    public ResultDto<Integer> updateDatum(@ApiParam("资料模型") @RequestBody DatumType datumType) {
        ResultDto<Integer> result;
        try {
            int num = service.updateByPrimaryKeySelective(datumType);
            result = ResultDtoUtil.success(num);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            //ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 拖拽修改
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:10
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/update/drags")
    @ApiOperation(notes = "修改资料类型", value = "修改资料类型", httpMethod = "POST")
    public ResultDto<Integer> updateDatumType(@RequestBody DatumType datumType) {
        ResultDto<Integer> result;
        try {
            int num = service.updateDatumType(datumType);
            result = ResultDtoUtil.success(num);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            //ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 删除资料类型
     * @Author jiangyuanwei
     * @Date 2018/9/26 9:24
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/delete")
    @ApiOperation(notes = "删除资料类型", value = "删除资料类型", httpMethod = "POST")
    public ResultDto<Integer> deleteDatum(@ApiParam("资料类型模型") @RequestBody DatumType datumType) {
        ResultDto<Integer> result;
        try {
            int mbrValues = service.deleteDatumType(datumType.getId());
            result = ResultDtoUtil.success();
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 外部调用接口
     * @Author jiangyuanwei
     * @Date 2018/9/26 9:24
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/messageIntroduction")
    @ApiOperation(notes = "外部调用接口", value = "外部调用接口", httpMethod = "POST")
    public ResultDto<Integer> deleteDatum(@ApiParam("资料模型") @RequestBody Datum datum) {
        ResultDto<Integer> result;
        try {
           int number =  service.addDatumAndDatumTypeAndDatumText(datum);
            result = ResultDtoUtil.success(number);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }
}
