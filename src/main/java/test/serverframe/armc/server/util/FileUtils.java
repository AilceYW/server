package test.serverframe.armc.server.util;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: Administrator
 * @Date: 2018/9/29 17:21
 * @Description:
 */
public class FileUtils {


    /**
     * @Descripttion  断点续传
     * @Author jiangyuanwei
     * @Date 2018/9/29 17:58
     * @Param [file, filePath, startRange, myfile, t, tmpFileName] 對象，文件地址，起始位置，每片大小，第幾篇,零時文件名稱
     * @Return  文件地址
     **/
    public static String download(MultipartFile[] files, String filePath, long startRange, long myfile, int t, long size, String tmpFileName) throws IOException, InterruptedException {
        //当前片数不等总片数
        //while (blobcut) {
        for (MultipartFile file : files) {
            // 将相应内容读取到buf中
            byte[] buf = new byte[(int) (myfile)];
            //把file的文件同过输入流
            InputStream in;
            in = file.getInputStream();
            in.read(buf);
            try {
                in.close();
            } catch (Exception e) {
            }
            //1.查看.tmp文件是否已经存在，如果不存在，就新建该文件
            if (tmpFileName == null) {
                filePath = filePath + t + ".tmp";
            }
            File f = new File(filePath);
            if (!f.exists()) {// .tmp文件不存在，使用OutputStream手动创建该文件
                OutputStream os = new FileOutputStream(f);
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
            //TimeUnit.MILLISECONDS.sleep(10L);
            int let = 0;
            RandomAccessFile raf;
            raf = new RandomAccessFile(f, "rw");
            //插入需要指定添加的数据
            raf.seek(startRange);//返回原来的插入处
            //System.out.println();
            //追加需要追加的内容
            raf.write(buf);
            try {
                raf.close();
            } catch (Exception e) {
            }
            tmpFileName = filePath;
        }
        return tmpFileName;
    }

    /**
     * 本地文件格式限定如下：
     * <p>
     * 如果localFile不为null，那么该文件在本地必须存在并且是文件
     * </p>
     * <p>
     * 如果localFile不为null，那么该文件的后缀必须是.tmp，说明该文件在之前没有传输完，本次继续传输
     * </p>
     *
     * @param localFile
     * @return
     */
    public static String checkLocalFileFormat(String localFile) {
        if (localFile == null) {
            return null;
        }
        String retMsg = null;
        try {
            File f = new File(localFile);
            if (!f.exists()) {

                localFile = null;
            } else if (localFile.endsWith(".tmp")) {
                // retMsg = "本地文件[" + localFile + "]应该以.tmp结尾";
                localFile = "本地文件[" + localFile + "]存在";
            }
        } catch (Exception e) {
            localFile = "在读取本地文件[" + localFile + "]时出错，请检查该文件";
        }
        return localFile;
    }

    /**
     * @Descripttion 检查路径是否存在
     * @Author jiangyuanwei
     * @Date 2018/9/30 9:35
     * @Param [filePath]
     * @Return
     **/
    public static String isDirectory(String filePath) {
        //验证目录是否存在，不存在则创建
        if (!(new File(filePath)).isDirectory()) {
            try {
                (new File(filePath)).mkdirs();
            } catch (Exception e) {
                return "創建文件失敗！";
            }
        }
        return "創建文件失敗！";
    }

    /**
     * @Descripttion  获取文件的大小 单位：kb
     * @Author jiangyuanwei
     * @Date 2018/10/23 11:22
     * @Param [size]
     * @Return
     **/
    public static int getPrintSize(long size) {
        if (size < 1024) {
            return (int) (size); //+ "Kb";
        } else {
            return (int) (size = size / 1024); //+ "Kb";
        }
    }
    /**
     * @Descripttion  判断目录是否存在
     * @Author jiangyuanwei
     * @Date 2018/10/23 11:22
     * @Param [imagePath]
     * @Return
     **/
    public static void mkdirs(String imagePath) {
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * @Descripttion  富文本框的图片上传
     * @Author jiangyuanwei
     * @Date 2018/10/23 11:20
     * @Param [file, filePath, fileName]
     * @Return
     **/
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * @Descripttion  删除文件
     * @Author jiangyuanwei
     * @Date 2018/10/18 14:43
     * @Param [falg]
     * @Return
     **/
    public static boolean isDelete(boolean falg,String str) {
        //删除本地的临时文件
        if(falg = true) {
            File file = new File(str);
            if (file.isFile() && file.exists()) {
                while (file.exists()) {
                    System.gc();
                    System.out.println( file.delete());
                }
            }
        }
        return false;
    }
    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }




}
