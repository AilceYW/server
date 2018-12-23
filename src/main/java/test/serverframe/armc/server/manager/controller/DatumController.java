package test.serverframe.armc.server.manager.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.common.exception.ExceptionHandle;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumAccessory;
import test.serverframe.armc.server.manager.domain.DatumText;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.manager.service.DatumAccessoryService;
import test.serverframe.armc.server.manager.service.DatumService;
import test.serverframe.armc.server.manager.service.DatumTextService;
import test.serverframe.armc.server.util.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

/**
 * @Auther: Administrator
 * @Date: 2018/9/25 16:07
 * @Description:
 */
@CrossOrigin(origins = "*")
@Api(value = "资料")
@Validated
@RestController
@RequestMapping("/datum")
public class DatumController {

    @Autowired
    private DatumService datumService;

    @Autowired
    private DatumTextService textService;
    @Autowired
    private DatumAccessoryService datumAccessoryService;

    @Autowired
    private ExceptionHandle<PageInfo<Datum>> exceptionHandle;

    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param datum    查询条件
     * @return
     */
    @PostMapping("/pages")
    @ApiOperation(notes = "分页展示资料的数据", value = "分页展示资料的数据", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "pageNum", value = "页码", required = true)
            , @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页大小", required = true)})
    public ResultDto<PageInfo<Datum>> pages(@Min(value = 1, message = "pageNum必须大于0") int pageNum,
                                            @Min(value = 1, message = "pageSize必须大于0") int pageSize,
                                            @ApiParam("条件模型") @RequestBody @Valid Datum datum) {
        ResultDto<PageInfo<Datum>> result;
        try {
            PageInfo<Datum> mbrValues = datumService.pageFind(pageNum, pageSize, datum);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = exceptionHandle.exceptionGet(ex);
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
    @ApiOperation(notes = "添加资料", value = "添加资料", httpMethod = "POST")
    public ResultDto<Integer> addDatum(@ApiParam("资料模型") @RequestBody Datum datum) {
        ResultDto<Integer> result;
        try {
            //datum.setZlbh(StringUtil.getId());
            datum.setCjsj(new Date());
            /*List<DatumAccessory> lists = datumAccessoryService.getAllDatumAccessory(datum.getZlbh());
            if (!CollectionUtils.isEmpty(lists)) {
                datum.setFjbs((short)1);
            }else {
                datum.setFjbs((short)0);
            }*/
            int mbrValues = datumService.insertSelective(datum);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 修改资料和添加正文文本和主题词
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:10
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/update")
    @ApiOperation(notes = "修改资料", value = "修改资料", httpMethod = "POST")
    public ResultDto<Integer> updateDatum(@ApiParam("资料模型") @RequestBody Datum datum) {
        ResultDto<Integer> result;
        try {
            int mbrValues = datumService.uodateZTAndAddZWWBAndZTC(datum);
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @Descripttion 删除资料
     * @Author jiangyuanwei
     * @Date 2018/9/26 9:24
     * @Param [datum]
     * @Return
     **/
    @PostMapping("/delete")
    @ApiOperation(notes = "删除资料", value = "删除资料", httpMethod = "POST")
    public ResultDto<Integer> deleteDatum(@ApiParam("资料模型") @RequestBody Datum datum) {
        ResultDto<Integer> result;
        try {
            int mbrValues = 0;
            List<String> itme = datum.getZlbhs();
            for (String s : itme) {
                //删除资料
                mbrValues = datumService.deleteByPrimaryKey(s);
                //删除资料下的正文
                textService.deleteByPrimaryKey(s);
                //删除附件
                datumAccessoryService.deleteByPrimaryKey(s);
            }
            result = ResultDtoUtil.success(mbrValues);
        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }
    @PostMapping("/dwnloadWord")
    @ApiOperation(notes = "导出word", value = "导出word", httpMethod = "POST")
    public ResultDto<String> dwnloadWord(@ApiParam("资料模型") @RequestBody Datum datum) {
        ResultDto<String> result = null;
        String name ="";
        int num = 0;
        try {
            Map<String, Object> root = new HashMap<String, Object>();
            //List<Datum> list = new ArrayList<>();
            for (String zlbh : datum.getZlbhs()) {
                String data="";
                Datum datums = datumService.selectByPrimaryKey(zlbh);
                name = datums.getBt();
                //查询快照路劲
                //判断快照路径不为null，取出数据
                if (datums.getDatumText().getKzlj() != null) {
                    //data = FTPUtil.getFtpHtmlData(datums.getDatumText().getKzlj());
                    //下载ftp上的html
                    FTPUtil.downLoadFile(datums.getDatumText().getKzlj(),name+".html","f:/");
                    //得到html的内容带样式
                    data = Html2String.readfile("f:/"+name+".html");
                    //获取网络图片路径
                   Set<String> sets =  Html2Text.getImgStr(data);
                   String names = "";
                    for (String set : sets) {
                        //ftp的图片路径
                        names = set;
                        //截取名称
                        names = names.substring(names.lastIndexOf("/")+1);
                        System.err.println(names);
                    }
                    //更改下载的html图片路径
                    String newStr = Html2Text.replaceHtmlTagd(data, "img", "src", "src=\"D://uplodFile//images//1111.jpg","\"" );
                    //写入一个新的html
                    Html2Text.writeListToFile(newStr,"f:/"+name + ".html");
                    //把html转为word（2003）
                    new Html2Word_doc().htmlToWord2("f:/"+name + ".html",null,"f:/"+name + ".doc","utf-8");
                    //转换为不带样式的内容
                   // data = Html2Text.Html2Text(data);
                }
                //root.put("BT", datums.getBt());
               // root.put("ZTC", datums.getZtc());
                //root.put("CJR", datums.getCjr());
                //root.put("ZWWB", data);
                //root.put("CJSJ",  DateUtil.formatDate(datums.getCjsj()));
               // MDoc doc = new MDoc();
               // doc.createDoc(root,"E:/"+name+".doc");
                num++;
            }
            //如果
            if (num == datum.getZlbhs().size()){
                result = ResultDtoUtil.success("成功！");
            }

        } catch (Exception ex) {
            result = ResultDtoUtil.error(500, "服务器错误");
            ex.printStackTrace();
        }
        return result;
    }

}
