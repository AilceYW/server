package test.serverframe.armc.server.manager.service;

import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumType;

import java.util.List;

/**
 * @version v1.0
 * @ClassName DatumTypeService
 * @Author jiangyuanwei
 * @Description //TODO
 * @Date 17:46 2018/9/25
 */
public interface DatumTypeService extends BaseService<DatumType, String> {
    /***
     * @Descripttion 查询所有的资料
     * @Author jiangyuanwei
     * @Date 2018/9/29 15:36
     * @Param [fid]
     * @Return
     **/
    void selectAllDatumType(List<DatumType> datumTypeList);

    /***
     * @Descripttion 修改个人资料为公共资料
     * @Author jiangyuanwei
     * @Date 2018/9/29 15:34
     * @Param [id]
     * @Return
     **/
    int updateDatumType(DatumType datumType);

    /**
     * @Descripttion 拿到所有根节点
     * @Author jiangyuanwei
     * @Date 2018/9/29 16:38
     * @Param []
     * @Return
     **/
    List<DatumType> selectRoot();

    /**
     * @Descripttion 合并请求（修改，新增。删除）
     * @Author jiangyuanwei
     * @Date 2018/9/30 14:28
     * @Param [datumTypeList]
     * @Return
     **/
    int mergeRequest(List<DatumType> datumTypeList);

    /**
     * @Descripttion 删除资料类型
     * @Author jiangyuanwei
     * @Date 2018/9/30 17:02
     * @Param [id]
     * @Return
     **/
    int deleteDatumType(String id);


    /**
     * @Descripttion 个人资料转公共
     * @Author jiangyuanwei
     * @Date 2018/10/9 13:37
     * @Param [datum]
     * @Return
     **/
    int addDatumAndDatumText(DatumType datumType);
    /**
     * @Descripttion 外部调用接口
     * @Author jiangyuanwei
     * @Date 2018/10/25 9:25
     * @Param [datum]
     * @Return
     **/
    int addDatumAndDatumTypeAndDatumText(Datum datum);


}
