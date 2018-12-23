package test.serverframe.armc.server.manager.service;

import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumText;

import java.io.FileNotFoundException;

public interface DatumTextService extends BaseService<DatumText, String> {

    DatumText getAllDatumText(String zlbh);
    /**
     * @Descripttion  资料互转
     * @Author jiangyuanwei
     * @Date 2018/10/12 15:07
     * @Param [datumText]
     * @Return
     **/
    int addDatumTextAll(DatumText param);
    /**  
     * @Descripttion 
     * @Author jiangyuanwei
     * @Date 2018/10/19 13:47
     * @Param []
     * @Return 
     **/
    Datum getFtpHtmlData(String zlbh) throws FileNotFoundException;
}
