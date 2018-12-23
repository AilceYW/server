package test.serverframe.armc.server.manager.service.impl;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import test.serverframe.armc.server.manager.config.FtpConfiguration;
import test.serverframe.armc.server.manager.dao.mapper.DatumMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTextMapper;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumText;
import test.serverframe.armc.server.manager.service.DatumService;
import test.serverframe.armc.server.manager.service.DatumTextService;
import test.serverframe.armc.server.util.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * /**
 *
 * @Auther: jiangyuanwei
 * @Date: 2018/9/25 15:57
 * @Description:
 */
@Service
public class DatumServiceImpl extends BaseServiceImpl<Datum, String> implements DatumService {

    @Value("${upload.filePath.uploadPath}")
    private String FILE_PATH;

    @Value("${ftp.ftp_img_htt_path}")
    private String FTP_HTTP_IMAHE_PATH;

    @Value("${ftp.img_path}")
    private String ftp_img_path;

    @Value("${upload.filePath.imgPath}")
    private String IMG_PATH;

    @Autowired
    private DatumMapper datumMapper;
    @Autowired
    private DatumTextMapper textMapper;
    @Autowired
    private DatumTextService service;

    @Override
    public PageInfo<Datum> pageFind(int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return this.pageFind("selectAllDatumPages", pageNum, pageSize, parameter);
    }

    @Transactional
    @Override
    public int addDatumSelective(Datum datum) {

        int result = 0;
        try {
            if (datum != null) {
                result = datumMapper.insertSelective(datum);
                DatumText datumText = new DatumText();
                datumText.setZlbh(datum.getZlbh());
                datumText.setZwwb(datum.getDatumText().getZwwb());
                result = textMapper.insertSelective(datumText);
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Transactional
    @Override
    public int updateZTC(Datum datum) {
        //先查询之前是否有主题词，有的话就追加在后面,没有就新建
        Datum d = datumMapper.selectByPrimaryKey(datum.getZlbh());
        int count = 0;
        try {
            //設值如果不為空填寫d.getZtc()+" "+datum.getZtc()，如果為空填寫d.getZtc()
            datum.setZtc(StringUtils.isNotEmpty(d.getZtc()) ? d.getZtc() + " " + datum.getZtc() : d.getZtc());
            count = datumMapper.updateZTC(datum);
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return count;
    }

    @Transactional
    @Override
    public int uodateZTAndAddZWWBAndZTC(Datum datum) throws IOException {
        int mbrValues = 0;
        //图片名
        String imageName = "";
        //替换的目标内容
        String newStr = "";
        DatumText text = new DatumText();
        text.setKzlj(FtpConfiguration.getRoot_path() + datum.getBt() + ".html");
        text.setZlbh(datum.getZlbh());
        text.setZwwb(datum.getDatumText().getZwwb());
        //查询正文
        Datum datums = datumMapper.selectByPrimaryKey(datum.getZlbh());

         mbrValues = textMapper.updateByPrimaryKeyWithBLOBs(text);
        //修改标题名称
        if (!datum.getBt().equals(datums.getBt())) {
            mbrValues = datumMapper.updateByPrimaryKeySelective(datum);
            FTPUtil.renameFile(FtpConfiguration.getRoot_path(),datum.getBt()+".html",datums.getBt()+".html");
        }
        //传过来的内容
        String data = text.getZwwb();
        try {
            if (datums.getDatumText() == null) {
                //添加正文文本
                mbrValues = textMapper.insert(text);
            }
            if (!datum.getDatumText().getZwwb().equals(datums.getDatumText().getZwwb())) {

                Html2Text.writeListToFile(data, FILE_PATH + File.separator + datum.getBt() + ".html");
                //如果存在就删除后再上传
                if (FTPUtil.existFile(FtpConfiguration.getRoot_path(), datum.getBt() + ".html")) {
                    FTPUtil.deleteFile(FtpConfiguration.getRoot_path() + datum.getBt() + ".html");
                }
                //上传文件到ftp
                boolean falg = FTPUtil.uploadFile(FtpConfiguration.getRoot_path(), datum.getBt() + ".html", FILE_PATH + File.separator + datum.getBt() + ".html");
                //上传图片到ftp
                // FTPUtil.uploadFile(ftp_img_path, imageName, IMG_PATH+);
                FileUtils.isDelete(falg, FILE_PATH + File.separator + datum.getBt() + ".html");
            }
            //先置空主题词
            mbrValues = datumMapper.updataByZTC(datum.getZlbh());
            //修改主题词
            if (mbrValues != 0) {
                mbrValues = datumMapper.updateZTC(datum);
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return mbrValues;
    }

    @Override
    public void getDatumDwnloadToWordData(Datum datum) {
        Map<String, Object> root = new HashMap<String, Object>();
        List<Datum> list = new ArrayList<>();
        for (String zlbh : datum.getZlbhs()) {
            Datum datums = datumMapper.selectByPrimaryKey(zlbh);
            list.add(datums);
        }
        for (Datum datums : list) {
            DatumText text = textMapper.selectByPrimaryKey(datum.getZlbh());
            root.put("BT", datum.getBt());
            root.put("ZTC", datum.getZtc());
            root.put("CJR", datum.getCjr());
            root.put("ZWWB", datum.getDatumText().getZwwb());
            root.put("CJSJ", datum.getCjsj());
        }
    }


}
