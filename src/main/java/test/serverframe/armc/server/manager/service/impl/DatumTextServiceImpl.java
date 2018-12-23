package test.serverframe.armc.server.manager.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import test.serverframe.armc.server.manager.dao.mapper.DatumMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTextMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTypeMapper;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumText;
import test.serverframe.armc.server.manager.domain.DatumType;
import test.serverframe.armc.server.manager.service.DatumTextService;
import test.serverframe.armc.server.util.FTPUtil;
import test.serverframe.armc.server.util.Html2Text;
import test.serverframe.armc.server.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/5 16:11
 * @Description:
 */
@Service("datumTextService")
public class DatumTextServiceImpl extends BaseServiceImpl<DatumText, String> implements DatumTextService {
    @Value("${ftp.ftp_img_htt_path}")
    private String FTP_HTTP_IMAHE_PATH;
    @Value("${upload.filePath.imagePath}")
    private String IMG_PATH;
    @Value("${url.path}")
    private String path;
    @Autowired
    private DatumTextMapper datumTextMapper;
    @Autowired
    private DatumTypeMapper typeMapper;
    @Autowired
    private DatumMapper datumMapper;

    @Override
    public PageInfo<DatumText> pageFind(int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return null;
    }

    @Override
    public DatumText getAllDatumText(String zlbh) {
        return datumTextMapper.selectAll(zlbh);
    }

    @Transactional
    @Override
    public int addDatumTextAll(DatumText datumText) {
        int count = 0;
        try {
            for (String zlbh : datumText.getZlbhs()) {
                String id = StringUtil.getId();
                //添加资料正文
                DatumText text = datumTextMapper.selectAll(zlbh);
                datumText.setKzlj(text.getKzlj());
                datumText.setKzdx(text.getKzdx());
                datumText.setMtgs(text.getMtgs());
                datumText.setWjzs(text.getWjzs());
                datumText.setYs(text.getYs());
                datumText.setZwwb(text.getZwwb());
                datumText.setZlbh(id);
                count = datumTextMapper.insertSelective(datumText);
                //添加资料
                Datum datum = new Datum();
                datum.setFjbs(text.getDatum().getFjbs());
                datum.setBt(text.getDatum().getBt());
                datum.setZtc(text.getDatum().getZtc());
                datum.setFlid(datumText.getDatum().getDatumType().getId());
                datum.setCjsj(new Date());
                datum.setZlbh(id);
                datum.setCjr(text.getDatum().getCjr());
                datum.setFx(text.getDatum().getFx());
                datum.setHjid(text.getDatum().getHjid());
                datum.setKzbs(text.getDatum().getKzbs());
                datum.setMglx(text.getDatum().getMglx());
                datum.setXgr(text.getDatum().getXgr());
                datum.setMmdj(text.getDatum().getMmdj());
                datum.setXh(text.getDatum().getXh());
                datum.setZlly(text.getDatum().getZlly());
                count = datumMapper.insertSelective(datum);
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        //添加资料分类
           /* DatumType datumType = new DatumType();
            datumType.setFlms(text.getDatum().getDatumType().getFlms());
            datumType.setFid(datumText.getDatum().getDatumType().getFid());
            datumType.setId(datumText.getDatum().getDatumType().getId());
            //判断是个人还是公共
            if (text.getDatum().getDatumType().getFllx().equals("公共")) {
                datumType.setFllx("个人");
            } else {
                datumType.setFllx("公共");
            }
            datumType.setJgid(text.getDatum().getDatumType().getJgid());
            datumType.setYhid(text.getDatum().getDatumType().getYhid());
            datumType.setFlmc(text.getDatum().getDatumType().getFlmc());
            count = typeMapper.insert(datumType);*/
        return count;
    }

    @Override
    public Datum getFtpHtmlData(String zlbh) throws FileNotFoundException {
        Datum datums =datumMapper.selectByPrimaryKey(zlbh);
        String data="";
        if (datums.getDatumText() != null && datums.getDatumText().getKzlj() != null){
            data= FTPUtil.getFtpHtmlData(datums.getDatumText().getKzlj());
            System.out.println(data);
            DatumText text = new DatumText();
            text.setZwwb(data);
            datums.setDatumText(text);
        }
        return datums;
    }
}
