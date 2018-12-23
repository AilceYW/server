package test.serverframe.armc.server.util;
/**
 * 2018/4/24
 *
 * @author Administrator
 */

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class Word2Html_docx {

    /**
     * 2007版本word转换成html 2017-2-27
     * @param wordPath word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix word文件后缀
     * @param imagePath word图片存放路径
     * @param img word图片存放的相对路径
     * @return
     * @throws IOException
     */
    public static String Word2007ToHtml(String wordPath, String wordName, String suffix,String imagePath,String httpImg,String img) throws IOException, InterruptedException {
        String htmlPath = wordPath + File.separator;
        String htmlName = wordName + ".html";
        //String imagePath = htmlPath + "img" + File.separator;
        //判断html文件是否存在
      File htmlFile = new File(htmlPath + htmlName);
        //创建文件夹
       // FileUtils.mkdirs(img);
        //word文件
        File wordFile = new File(wordPath + File.separator + wordName + suffix);

        // 1) 加载word文档生成 XWPFDocument对象
       /* InputStream in = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(in);

        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        File imgFolder = new File(imagePath);
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(imgFolder));
        //html中图片的路径 相对路径  img是图片存放的相对路径
        options.URIResolver(new BasicURIResolver(imagePath));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);

        System.err.println(options.);

        // 3) 将 XWPFDocument转换成XHTML
        //生成html文件上级文件夹
        File folder = new File(htmlPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        OutputStream out = new FileOutputStream(htmlFile);


        OutputStreamWriter writer = new OutputStreamWriter(out, "GB2312");//自定义编码
        XHTMLConverter instance = (XHTMLConverter)XHTMLConverter.getInstance();
        instance .convert(document,writer, options);

        //        OutputStream out = new FileOutputStream(new File(htmlFile));
        //        XHTMLConverter.getInstance().convert(document, out, options);
        //String htmlData = Html2String.readfile(htmlPath+htmlName);
        return getHtmlData(htmlFile.getAbsolutePath());*/

        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(wordFile));
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePath)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver(imagePath));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlFile), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        }
        return  "";
    }


    public static String getHtmlData(String filePath) {
        return Html2String.readfile(filePath);
    }

    /**
     * 2007版本word转换成html 2018-4-9
     * 
     * @param wordPath
     *            word文件路径
     * @param wordName
     *            word文件名称无后缀
     * @param suffix
     *            word文件后缀
     * @return
     * @throws IOException
     */
    public static String Word2007ToHtmls(String wordPath, String wordName,
                                        String suffix) throws IOException {
        String htmlPath = wordPath + File.separator + wordName + "_show"
                + File.separator;
        String htmlName = wordName + ".html";
        String imagePath = htmlPath + "image" + File.separator;


// 判断html文件是否存在
        File htmlFile = new File(htmlPath + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }


// word文件
        File wordFile = new File(wordPath + File.separator + wordName + suffix);


// 1) 加载word文档生成 XWPFDocument对象
        InputStream in = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(in);


// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        File imgFolder = new File(imagePath);
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(imgFolder));
// html中图片的路径 相对路径
        options.URIResolver(new BasicURIResolver("image"));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);


// 3) 将 XWPFDocument转换成XHTML
// 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //OutputStream out = new FileOutputStream(htmlFile);
        //XHTMLConverter.getInstance().convert(document, out, options);
// 也可以使用字符数组流获取解析的内容
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     XHTMLConverter.getInstance().convert(document, baos, options);
     String content = baos.toString();
     System.out.println("2007-docx"+content);
     baos.close();
        return htmlFile.getAbsolutePath();
    }
    /**
     * 调用的模板
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

//            Word2003ToHtml("H:\\MyTest\\Java\\","test",".doc");

        //Word2007ToHtml("D:\\uplodFile", "3-功能说明完整版", ".docx");
        Word2007ToHtmls("D:\\uplodFile\\","docx",".docx");

    }
}