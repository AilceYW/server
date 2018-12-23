package test.serverframe.armc.server.manager.service.impl;

import ch.qos.logback.classic.turbo.TurboFilter;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import test.serverframe.armc.server.manager.config.FtpConfiguration;
import test.serverframe.armc.server.manager.dao.mapper.DatumAccessoryMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTextMapper;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumAccessory;
import test.serverframe.armc.server.manager.domain.DatumText;
import test.serverframe.armc.server.manager.service.DatumAccessoryService;
import test.serverframe.armc.server.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: Administrator
 * @Date: 2018/9/29 17:34
 * @Description:
 */
@Service
public class DatumAccessoryServiceImpl extends BaseServiceImpl<DatumAccessory, String> implements DatumAccessoryService {
    //文件地址
    private static String pathFile;

    private static Datum datums;

    @Value("${upload.filePath.uploadPath}")
    private String FILE_PATH;

    @Value("${upload.filePath.downloadPath}")
    private String DOWNLOAD_PATH;

    @Value("${upload.filePath.imgPath}")
    private String IMG_PATH;

    @Value("${upload.filePath.httpImagePath}")
    private  String HTTP_IMAHE_PATH;

    @Value("${upload.filePath.imagePath}")
    private  String IMAHE_PATH;

    @Value("${ftp.ftp_img_htt_path}")
    private  String FTP_HTTP_IMAHE_PATH;

    @Value("${ftp.img_path}")
    private String ftp_img_path;

    private static String tmpFileName = "";

    @Autowired
    private DatumAccessoryMapper mapper;

    @Autowired
    private DatumMapper datumMapper;

    @Autowired
    private DatumTextMapper datumTextMapper;

    @Override
    public PageInfo<DatumAccessory> pageFind(int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return null;
    }

    @Override
    public boolean uploadFile(MultipartFile[] file, String fileName, int t, long size, long myfile, int blobname) throws IOException, InterruptedException {
        FileUtils.isDirectory(FILE_PATH);
        //文件路徑
        String filePath = FILE_PATH + File.separator + fileName;
        String localFile = null;
        //1.检查文件
        String checkLocalFileMsg;
        if (StringUtils.isEmpty(tmpFileName)) {
            checkLocalFileMsg = FileUtils.checkLocalFileFormat(filePath);
        } else {
            checkLocalFileMsg = FileUtils.checkLocalFileFormat(tmpFileName);
        }
        if (checkLocalFileMsg != null) {
            //System.err.println(checkLocalFileMsg);
            //break;
        }
        // 2. 计算Range的范围
        //判斷是否是第一次上傳
        long startRange, endRange;
        if (checkLocalFileMsg == null) {
            startRange = 0L;
            endRange = myfile; //每片文件的大小
        } else {
            long f = new File(filePath).length();
            startRange = f;
            endRange = startRange + myfile; // 從斷的文件処上傳
        }
        //3.分段下载
        tmpFileName = FileUtils.download(file, filePath, startRange, myfile, t, size, tmpFileName);
        //文件大小
        int fileSize = FileUtils.getPrintSize(size);
        //获取后缀名
        //fileName.substring(fileName.lastIndexOf(".") + 1);
        //String name = fileName.substring(0, fileName.lastIndexOf("."));
        //保存数据到数据库
        int count = t + 1;
        if (blobname == count) {
            //4.转为html格式
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String path = null;
            String htmlData = "";
            String suffixName= "";
            //ftp的文件路径
            String ftpFilePath = "";
            try {
                 suffixName = fileName.substring(fileName.lastIndexOf("."));
                //判断word上传的格式
                if (suffixName.equals(".doc")) {
                    htmlData = Word2Html_doc.convert2Html(tmpFileName,FILE_PATH+File.separator+name+".html",IMG_PATH,IMAHE_PATH,ftp_img_path);
                    replaceHtmlImagePath(suffixName,htmlData,name);
                    //上传到FTP服务器 第一个参数是ftp地址，文件名，上传的文件绝对路径
                   boolean falg =  FTPUtil.uploadFile(FtpConfiguration.getRoot_path(),name+".html",FILE_PATH+File.separator+name+".html");
                   //上传图片到ftp
                   FTPUtil.uploadFile(ftp_img_path,htmlData,IMG_PATH+htmlData);
                   //快照路径
                   ftpFilePath = FtpConfiguration.getRoot_path()+name+".html";
                  FileUtils.isDelete(falg,FILE_PATH+File.separator+name+".html");
                } else if(suffixName.equals(".docx")){
                    htmlData = Word2Html_docx.Word2007ToHtml(FILE_PATH,name,suffixName,FtpConfiguration.getImg_path(),FTP_HTTP_IMAHE_PATH,IMG_PATH);
                    replaceHtmlImagePath(suffixName,htmlData,name);
                    //快照路径
                    ftpFilePath = FtpConfiguration.getRoot_path()+name+".html";
                    //上传到FTP服务器 第一个参数是ftp地址，文件名，上传的文件绝对路径
                    boolean falg =  FTPUtil.uploadFile(FtpConfiguration.getRoot_path(),name+".html",FILE_PATH+File.separator+name+".html");
                    //上传图片到ftp
                    FTPUtil.uploadFile(ftp_img_path,htmlData,IMG_PATH+htmlData);
                    //删除本地的临时文件
                    FileUtils.isDelete(falg,FILE_PATH+File.separator+name+".html");
                }
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            //文件的全路径
            pathFile = this.getFilePath(tmpFileName);
            //封装数据
            datums = addDatumFiles(name,ftpFilePath,suffixName);
        }
        if (tmpFileName != null) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @Descripttion  添加文件上传的数据
     * @Author jiangyuanwei
     * @Date 2018/10/22 13:08
     * @Param [name, data, suffixName]
     * @Return
     **/
    private Datum addDatumFiles(String name, String data, String suffixName) {
        Datum datum = new Datum();
        datum.setBt(name);
        DatumAccessory accessory = new DatumAccessory();
        accessory.setWjm(name);
        accessory.setCclj(pathFile);
        accessory.setFjlx(suffixName);
        datum.setAccessory(accessory);
        DatumText text = new DatumText();
        text.setKzlj(data);
        //text.setZwwb(data);
        datum.setDatumText(text);
        return datum;
    }

    @Override
    public File dowloadFile(String wjm) {
        File file = new File(wjm);
        return file;
    }

    @Override
    public boolean upload(MultipartFile[] files, HttpServletRequest request) {
      /*  String frontSavePath;
        try {
            frontSavePath = FILE_PATH;
        } catch (Exception e) {
            return false;
        }
        String fileName = "";
        File saveFile = null;
        InputStream inputStream = null;
        FileOutputStream outputStream;
        for (MultipartFile file : files) {
            //上传文件保存
            try {
                fileName = file.getOriginalFilename();
                inputStream = file.getInputStream();
                saveFile = new File(frontSavePath, fileName);
                saveFile.createNewFile();
                outputStream = new FileOutputStream(saveFile);
                byte temp[] = new byte[1024];
                int size = -1;
                //写入上传文件
                while ((size = inputStream.read(temp)) != -1) {
                    outputStream.write(temp, 0, size);
                }
            } catch (Exception e) {
                return false;
            } finally {
                inputStream.close();
            }
        }*/
        return true;
    }
    @Transactional
    @Override
    public int deleteFile(String fjId) {
        int result = 0;
        try {
            result =  mapper.deleteByPrimaryKey(fjId);
        } catch (RuntimeException e) {
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
    /**
     * @Descripttion  获取文件路径
     * @Author jiangyuanwei
     * @Date 2018/10/12 11:23
     * @Param [filePath]
     * @Return
     **/
    public String getFilePath(String filePath){
        return filePath;
    }


    @Override
    public List<DatumAccessory> getAllDatumAccessory(String zlbh) {
        return mapper.getAllDatumAccessory(zlbh);
    }
    /**
     * @Descripttion  添加资料附件
     * @Author jiangyuanwei
     * @Date 2018/10/12 12:22
     * @Param [accessory]
     * @Return
     **/
    @Transactional
    @Override
    public int addFile(DatumAccessory accessory) {
        int result = 0;
        try {
            accessory.setCclj(pathFile);
            result = mapper.insertSelective(accessory);
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Transactional
    @Override
    public int addDatumFile(Datum datum, String name, String data, String suffixName) {
        Datum datum1 = datums;
        int num = 0;
        try {
            DatumAccessory accessory = new DatumAccessory();
            accessory.setFjid(datum.getAccessory().getFjid()+1);
            accessory.setZlbh(datum.getZlbh());
            accessory.setWjm(datum1.getAccessory().getWjm());
            accessory.setFjlx(datum1.getAccessory().getFjlx());
            accessory.setCclj(pathFile);
            num = mapper.insertSelective(accessory);
            //添加资料到资料表数据库
            datum.setCjsj(new Date());
            datum.setZlbh(datum.getZlbh());
            datum.setBt(datum1.getBt());
            datum.setFlid(datum.getDatumType().getId());
            num = datumMapper.insertSelective(datum);
            //5.保存html格式到数据库
            //String fileData = Word2Html_docx.getHtmlData(path);
            DatumText  text = new DatumText();
            text.setZlbh(datum.getZlbh());
            text.setKzlj(datum1.getDatumText().getKzlj());
            //text.setZwwb(datum1.getDatumText().getZwwb());
            num = datumTextMapper.insertSelective(text);
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return num;
    }
    /**
     * @Descripttion  替换html的图片路径
     * @Author jiangyuanwei
     * @Date 2018/10/24 17:21
     * @Param [suffixName, htmlData, name] 后缀名，图片名，文件名
     * @Return
     **/
    private void replaceHtmlImagePath( String suffixName, String htmlData, String name) {
        if (suffixName.equals(".doc")) {
            //得到html的内容带样式
            String data = Html2String.readfile(FILE_PATH+File.separator+name+".html");
            StringBuffer sb = new StringBuffer();
            sb.append("src=");
            sb.append("'");
            sb.append(FTP_HTTP_IMAHE_PATH);
            sb.append(htmlData);
            sb.append("'");
            //替换下载的html图片路径
            String newStr = Html2Text.replaceHtmlTagd(data, "img", "src", sb.toString(),"\"" );
            System.err.println(newStr);
            //写入一个新的html
            Html2Text.writeListToFile(newStr,FILE_PATH+File.separator+name + ".html");
        } else {
            //得到html的内容带样式
            String data = Html2String.readfile(FILE_PATH+File.separator+name+".html");
            //获取网络图片路径
            Set<String> sets =  Html2Text.getImgStr(data);
            for (String set : sets) {
                //ftp的图片路径
                htmlData = set;
                //截取名称
                htmlData = htmlData.substring(htmlData.lastIndexOf("/")+1);
                System.err.println(htmlData);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("src=");
            sb.append("'");
            sb.append(FTP_HTTP_IMAHE_PATH);
            sb.append(htmlData);
            sb.append("'");
            //替换下载的html图片路径
            String newStr = Html2Text.replaceHtmlTagd(data, "img", "src", sb.toString(),"\"" );
            System.err.println(newStr);
            //写入一个新的html
            Html2Text.writeListToFile(newStr,FILE_PATH+File.separator+name + ".html");
        }
    }
}
