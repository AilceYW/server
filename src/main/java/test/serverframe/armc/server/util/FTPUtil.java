package test.serverframe.armc.server.util;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;

/**
 * Created by Fanyx on 2018-07-30.
 */
public class FTPUtil {

    private static String host;

    private static Integer port;

    private static String username;

    private static String password;

    public static FTPClient ftpClient = null;

    /**
     * 初始化ftp服务器
     */
    public static void initFtpClient() {
       /* host = FtpConfiguration.getHost();
        port = Integer.parseInt(FtpConfiguration.getPort());
        username = FtpConfiguration.getUsername();
        password = FtpConfiguration.getPassword();*/
        host = "127.0.0.1";
        port = 21;
        username = "ftp";
        password = "123456";
        ftpClient = new FTPClient();
       // ftpClient.setControlEncoding("utf-8");
        System.out.println("connecting...ftpServer：" + host + ":" + port);
        try {
            //连接ftp服务器
            ftpClient.connect(host, port);
            //登陆ftp服务器
            ftpClient.login(username, password);
            //是否成功登陆
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect success...ftpServer：" + host + ":" + port);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param pathName       ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originFileName 待上传文件的名称(绝对地址)
     * @return
     */
    public static Boolean uploadFile(String pathName, String fileName, String originFileName) {
        boolean flag = false;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originFileName));
            flag = uploadFile(pathName, fileName, inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param pathName    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    @SuppressWarnings("static-access")
    public static boolean uploadFile(String pathName, String fileName, InputStream inputStream) {
        boolean flag = false;
        try {
            System.out.println("start upload file ...");
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            /*ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);*/
            CreateDirecroty(pathName);
            ftpClient.storeFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            System.out.println("file upload success");
        } catch (IOException e) {
            System.out.println("文件上传失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 改变目录路径
     *
     * @param directory
     * @return
     */
    public static boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + "成功");
            } else {
                System.out.println("进入文件夹" + directory + "失败！开始创建文件夹");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     *
     * @param remote
     * @return
     * @throws IOException
     */
    public static boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        //如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path, null)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }
                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                //检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }

        return success;
    }

    /**
     * 判断ftp服务器文件是否存在
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean existFile(String path, String naem) throws IOException {
        boolean flag = false;
        initFtpClient();
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            for (FTPFile ftpFile : ftpFileArr) {
                if (ftpFile.getName().equals(naem)) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    /**
     * 创建目录
     *
     * @return
     */
    public static boolean makeDirectory(String dir) {
        boolean flag = false;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + "成功！");
            } else {
                System.out.println("创建文件夹" + dir + "失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param pathName  FTP服务器文件目录
     * @param fileName  文件名称
     * @param localPath 下载后的文件路径
     * @return
     */
    public static boolean downLoadFile(String pathName, String fileName, String localPath) {
        boolean flag = false;
        OutputStream outputStream = null;
        initFtpClient();
        try {
            //截取路径
            String filePath = pathName.substring(0, pathName.lastIndexOf("/"));
            System.out.println("开始下载文件");
            //切换FTP目录
            ftpClient.changeWorkingDirectory(filePath);
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            for (FTPFile file : ftpFiles) {
                if (fileName.equals(file.getName())) {
                    //  File localFile = new File(localPath + "/" + file.getName());
                    outputStream = new FileOutputStream(localPath + file.getName());
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    outputStream.flush();
                    // outputStream.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (IOException e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param pathName FTP服务器保存目录     * @param fileName
     * @return
     */
    public static boolean deleteFile(String pathName) {
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            String naem = pathName.substring(pathName.lastIndexOf("/")+1);
            pathName = pathName.substring(0,pathName.lastIndexOf("/")+1);


           // ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (int i = 0; i < ftpFiles.length; i++) {
                String fileName = new String(ftpFiles[i].getName().getBytes("ISO-8859-1"),"GBK");
                if (naem.equals(fileName)) {
                    ftpClient.deleteFile(naem);
                }
            }


            /*//切换FTP目录
              ftpClient.changeWorkingDirectory(pathName);
            ftpClient.deleteFile(naem);
            ftpClient.logout();*/
            flag = true;
            System.out.println("删除文件成功");
        } catch (IOException e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return flag;
    }


    /**
     * 通过路径获取文件内容返回html格式
     */
    public static String getFtpHtmlData(String ftpFilePath) {
        initFtpClient();
        StringBuffer newUrl = new StringBuffer();
        //截取路径
        String filePath = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));
        System.out.println(filePath);
        String remotePath = filePath;//FTP服务器上的相对路径
        //截取名称
        String name = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
        System.out.println(name);
        String fileName = name;//要下载的文件名
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            BufferedReader outputStream = null;
            FTPFile[] ff = ftpClient.listFiles(remotePath);
            for (FTPFile ftpFile : ff) {
                if (ftpFile.getName().equals(fileName)) {
                    InputStream in = ftpClient.retrieveFileStream(new String(ftpFile.getName().getBytes("UTF-8"), "ISO-8859-1"));
                    String len;
                    outputStream = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    while ((len = outputStream.readLine()) != null) {
                        newUrl.append(len);
                    }
                }
            }
            outputStream.close();
            ftpClient.logout();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newUrl.toString();
    }


    /**
     * 修改文件名
     *
     * @param directory  FTP服务器文件目录
     * @param srcFname   重命名后的文件名
     * @param targetFname  要重命名的文件名
     * @return
     */

    public static boolean renameFile(String directory,String srcFname, String targetFname) throws IOException {
        initFtpClient();
            boolean flag = false;
            //进入目录
            ftpClient.changeWorkingDirectory(directory);
            flag = ftpClient.rename(targetFname, srcFname);//重命名远程文件
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /*
     * 写文件到ftp
     * param: fileContent：文件所在目录，fileName: 创建的文件 path：指定写入的目录
     */
    public static int writeFileToFtp(String fileContent, String fileName, String path) {
        boolean falg;
        InputStream is = null;
        try {
            // 1.输入流
            is = new ByteArrayInputStream(fileContent.getBytes());
            // 2.连接服务器
            initFtpClient();
            //3.判断文件是否存在
            //falg = existFile(path+fileName);
            if (true) {
                if (deleteFile(path + fileName)) {
                    // 4.指定写入的目录
                    ftpClient.changeWorkingDirectory(path);
                    // 5.写操作
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
                }
            } else {
                // 4.指定写入的目录
                ftpClient.changeWorkingDirectory(path);
                // 5.写操作
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) throws IOException {
        initFtpClient();
        //rename("1323.html", "/ftp/123654.html");
       // renameFile("/ftp/","13234.html", "/ftp/1323.html");
        deleteFile("/ftp/WH-JL-HR-28-A 新员工转正申请表.docx");

       // uploadFile("/","WH-JL-HR-28-A 新员工转正申请表.docx","F:\\java学习资料\\Spring Cloud微服务实战.pdf");
    }
}
