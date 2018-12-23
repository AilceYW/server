package test.serverframe.armc.server.manager.service;

import test.serverframe.armc.server.manager.domain.Datum;

import java.io.IOException;

public interface DatumService extends BaseService<Datum, String> {

    /**
     * @Descripttion 添加资料与资料文本
     * @Author jiangyuanwei
     * @Date 2018/10/5 16:48
     * @Param [datum]
     * @Return
     **/
    int addDatumSelective(Datum datum);

    /**
     * @Descripttion 添加主题词
     * @Author jiangyuanwei
     * @Date 2018/10/8 13:41
     * @Param [datum]
     * @Return
     **/
    int updateZTC(Datum datum);

    /**
     * @Descripttion 编辑资料
     * @Author jiangyuanwei
     * @Date 2018/10/9 13:36
     * @Param [datum]
     * @Return
     **/
    int uodateZTAndAddZWWBAndZTC(Datum datum) throws IOException;
    /**
     * @Descripttion  获取下载word的资料数据
     * @Author jiangyuanwei
     * @Date 2018/10/23 17:10
     * @Param [datum]
     * @Return
     **/
    void  getDatumDwnloadToWordData(Datum datum);




}
