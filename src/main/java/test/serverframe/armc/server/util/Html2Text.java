package test.serverframe.armc.server.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class Html2Text extends HTMLEditorKit.ParserCallback {
    /*StringBuffer s;

    public Html2Text() {}

    public void parse(Reader in) throws IOException {
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
    }

    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static void main (String[] args) {
        try {
            // the HTML to convert
            //Reader in=new StringReader("string");
            FileReader in = new FileReader("f:/20181019.html");
            Html2Text parser = new Html2Text();
            parser.parse(in);
            in.close();
            System.out.println(parser.getText());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 解析html文件
     *
     * @param file
     * @return
     */
    public static String readHtml(File file) {
        String body = "";
        try {
            FileInputStream iStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(iStream);
            BufferedReader htmlReader = new BufferedReader(reader);

            String line;
            boolean found = false;
            while (!found && (line = htmlReader.readLine()) != null) {
                if (line.toLowerCase().indexOf("<body") != -1) { // 在<body>的前面可能存在空格
                    found = true;
                }
            }

            found = false;
            while (!found && (line = htmlReader.readLine()) != null) {
                if (line.toLowerCase().indexOf("</body") != -1) {
                    found = true;
                } else {
                    // 如果存在图片，则将相对路径转换为绝对路径
                    String lowerCaseLine = line.toLowerCase();
                    if (lowerCaseLine.contains("src")) {

                        //这里是定义图片的访问路径
                        String directory = "D:/test";
                        // 如果路径名不以反斜杠结尾，则手动添加反斜杠
                        /*if (!directory.endsWith("\\")) {
                            directory = directory + "\\";
                        }*/
                        //    line = line.substring(0,  lowerCaseLine.indexOf("src") + 5) + directory + line.substring(lowerCaseLine.indexOf("src") + 5);
                        /*String filename = extractFilename(line);
                        line = line.substring(0,  lowerCaseLine.indexOf("src") + 5) + directory + filename + line.substring(line.indexOf(filename) + filename.length());
                    */
                        // 如果该行存在多个<img>元素，则分行进行替代
                        String[] splitLines = line.split("<img\\s+"); // <img后带一个或多个空格
                        // 因为java中引用的问题不能使用for each
                        for (int i = 0; i < splitLines.length; i++) {
                            if (splitLines[i].toLowerCase().startsWith("src")) {
                                splitLines[i] = splitLines[i].substring(0, splitLines[i].toLowerCase().indexOf("src") + 5)
                                        + directory
                                        + splitLines[i].substring(splitLines[i].toLowerCase().indexOf("src") + 5);
                            }
                        }

                        // 最后进行拼接
                        line = "";
                        for (int i = 0; i < splitLines.length - 1; i++) { // 循环次数要-1，因为最后一个字符串后不需要添加<img
                            line = line + splitLines[i] + "<img ";
                        }
                        line = line + splitLines[splitLines.length - 1];
                    }

                    body = body + line + "\n";
                }
            }
            htmlReader.close();
            //        System.out.println(body);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * @param htmlLine 一行html片段，包含<img>元素
     * @return 文件名
     */
    public static String extractFilename(String htmlLine) {
        int srcIndex = htmlLine.toLowerCase().indexOf("src=");
        if (srcIndex == -1) { // 图片不存在，返回空字符串
            return "";
        } else {
            String htmlSrc = htmlLine.substring(srcIndex + 4);
            char splitChar = '\"'; // 默认为双引号，但也有可能为单引号
            if (htmlSrc.charAt(0) == '\'') {
                splitChar = '\'';
            }
            String[] firstSplit = htmlSrc.split(String.valueOf(splitChar));
            String path = firstSplit[1]; // 第0位为空字符串
            String[] secondSplit = path.split("[/\\\\]"); // 匹配正斜杠或反斜杠
            return secondSplit[secondSplit.length - 1];
        }
    }

    //从html中提取纯文本
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_special;
        java.util.regex.Matcher m_special;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_special = "\\&[a-zA-Z]{1,10};"; // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
            m_special = p_special.matcher(htmlStr);
            htmlStr = m_special.replaceAll(""); // 过滤特殊标签
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        //剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        System.err.println(textStr);
        return textStr;// 返回文本字符串
    }

   /* public static void main(String[] args) {
        String data = Html2String.readfile("f:/20181019.html");
        System.out.println(data);
       // Html2Text(data);
    }*/


    private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签

    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

    /**
     * 替换指定标签的属性和值
     *
     * @param str       需要处理的字符串
     * @param tag       标签名称
     * @param tagAttrib 要替换的标签属性值
     * @param startTag  新标签开始标记
     * @param endTag    新标签结束标记
     * @return
     * @author huweijun
     * @date 2016年7月13日 下午7:15:32
     */
    public static String replaceHtmlTagd(String str, String tag, String tagAttrib, String startTag, String endTag) {
        StringBuffer sb = new StringBuffer();
            String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*";
            String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"";
            Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.CASE_INSENSITIVE);
            Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib, Pattern.CASE_INSENSITIVE);
            Matcher matcherForTag = patternForTag.matcher(str);
            boolean result = matcherForTag.find();
            while (result) {
                StringBuffer sbreplace = new StringBuffer("<" + tag + " ");
                Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group());
                if (matcherForAttrib.find()) {
                    String attributeStr = matcherForAttrib.group(1);
                    attributeStr = "";
                    matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr);
                }
                matcherForAttrib.appendTail(sbreplace);
                matcherForTag.appendReplacement(sb, sbreplace.toString());
                result = matcherForTag.find();
            }
            matcherForTag.appendTail(sb);
        return sb.toString();
    }

    /**
     * 得到网页中图片的地址
     */
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //分解正则表达式
        // <img 匹配字符串为 <img 的
        // .*   匹配任意长度的除/r /n 以外的字符,* 是不限次数
        // src  匹配字符串为 src 的
        // \\s* 匹配任意长度任意不可见字符，包括空格、制表符、换页符等等
        // =    匹配等号字符
        // \\s* 匹配任意长度任意不可见字符，包括空格、制表符、换页符等等
        // (.*?) 匹配任意长度的除/r /n 以外的任意字符 0次或一次
        // [^>]*? 匹配不为>的不限次数 0次或1次
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        //Pattern.CASE_INSENSITIVE 参数含义为忽略大小写匹配，会稍微损失一点性能
        // 把这个正则的字符串编译为Pattern对象
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        //调用Pattern对象的matcher方法返回一个Matcher对象
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * 获取指定HTML标签的指定属性的值
     * @param source 要匹配的源文本
     * @param element 标签名称
     * @param attr 标签的属性名称
     * @return 属性值列表
     */
    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group();
            result.add(r);
        }
        return result;
    }

    public static void main(String[] args) {
        String source = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><style type=\"text/css\">.b1{white-space-collapsing:preserve;}.b2{margin: 1.0in 1.25in 1.0in 1.25in;}.p1{text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p2{text-indent:-0.7in;margin-left:0.7in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p3{text-indent:0.1388889in;margin-left:0.625in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p4{margin-left:0.625in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p5{text-indent:0.33333334in;margin-left:0.29166666in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p6{text-indent:0.1388889in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p7{text-indent:0.33333334in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p8{text-indent:0.1388889in;margin-left:0.875in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p9{text-align:center;hyphenate:auto;keep-with-next.within-page:always;font-family:Times New Roman;font-size:12pt;}.p10{margin-top:0.108333334in;margin-bottom:0.108333334in;text-align:start;hyphenate:auto;keep-together.within-page:always;keep-with-next.within-page:always;font-family:Times New Roman;font-size:12pt;}.p11{text-indent:-0.29166666in;margin-left:0.625in;text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p12{text-indent:0.1388889in;margin-left:0.25in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.p13{text-indent:0.29166666in;margin-left:0.25in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}.p14{text-indent:0.29166666in;margin-left:0.25in;text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}.s1{display: inline-block; text-indent: 0; min-width: 0.22222222in;}.s2{font-family:宋体;font-size:11pt;}.s3{color:red;}.s4{display: inline-block; text-indent: 0; min-width: 0.4722222in;}.s5{display: inline-block; text-indent: 0; min-width: 0.15277778in;}.s6{display: inline-block; text-indent: 0; min-width: 0.097222224in;}.s7{font-family:宋体;font-size:11pt;color:red;}.s8{display: inline-block; text-indent: 0; min-width: 0.44444445in;}.s9{font-size:12pt;color:red;}</style><meta content=\"leizhenyang\" name=\"author\"></head><body class=\"b1 b2\"><p class=\"p1\">1.1.1.1 <a name=\"_Ref513486059\"><a name=\"OLE_LINK7\"><a name=\"OLE_LINK8\"><span>系统业务配置</span></a></a></a></p><p class=\"p2\">1.1.1.1.1 <span>字典维护配置【字典名称（ZD_ZDMC）、字典值（ZD_ZDZ）】</span></p><p class=\"p3\"><span class=\"s1\">1)\u200B&nbsp;</span><span>），权限条件（？）；</span></p><p class=\"p4\"></p><p class=\"p2\">1.1.1.1.1 <span>功能权限配置【功能权限（YEW_QB_PZ_GNQX）】</span></p><p class=\"p3\"><span class=\"s1\">a)\u200B&nbsp;</span><span>需求标识</span></p><p class=\"p4\"><span>SRS_QBCL_QBZBTB_XTYWPZ_QXPZ_002</span></p><p class=\"p3\"><span class=\"s1\">b)\u200B&nbsp;</span><span>功能描述</span></p><p class=\"p5\"><span>系统管理人员根据业务要求手动维护角色的功能权限配置，根据功能全集设置对角色的映射关系，针对所有人所有角色有效。</span></p><p class=\"p3\"><span class=\"s1\">1)\u200B&nbsp;</span><span>功能包括：查询、修改；</span></p><p class=\"p3\"><span class=\"s1\">2)\u200B&nbsp;</span><span>&ldquo;功能权限&rdquo;修改功能，填写字段包括：可见、不可见</span></p><p class=\"p6\"></p><p class=\"p2\">1.1.1.1.1 <span>用户权限配置</span></p><p class=\"p3\"><span class=\"s1\">1)\u200B&nbsp;</span><span>需求标识：SRS_QBCL_QBZBTB_XTYWPZ_QXPZ_001</span></p><p class=\"p4\"><span>打开系统业务配置界面并选择用户任务配置功能。</span></p><p class=\"p3\"><span class=\"s1\">a)\u200B&nbsp;</span><span>输入输出</span></p><p class=\"p4\"><span>输入：用户关联任务；</span></p><p class=\"p4\"><span>输出：无；</span></p><p class=\"p3\"><span class=\"s1\">b)\u200B&nbsp;</span><span>处理要求</span></p><p class=\"p3\"><span class=\"s1\">1)\u200B&nbsp;</span><span>页面显示包括：用户列表【用户（YEW_QB_PZ_YH）</span><span class=\"s2\">】</span><span>、任务列表【</span><span class=\"s3\">找不到相应数据表</span><span>】；</span></p><p class=\"p3\"><span class=\"s1\">2)\u200B&nbsp;</span><span>用户列表展示包括（需确认、展示内容来自后台表）：序号、姓名、机构标示；</span></p><p class=\"p3\"><span class=\"s1\">3)\u200B&nbsp;</span><span>任务列表包括：</span><span class=\"s3\">？？</span><span>；</span></p><p class=\"p7\"><span class=\"s3\">在 用户方向（YEW_QB_PZ_YHFX）CURD   SELECT 用户方向组（YEW_QB_PZ_YHFXZ） 方向组（YEW_QB_PZ_FXZ） 方向（YEW_QB_PZ_FX）按理说就行了</span></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p8\"><span class=\"s4\">1)\u200B&nbsp;</span><span>执行授权操作；</span></p><p class=\"p8\"><span class=\"s4\">2)\u200B&nbsp;</span><span>关闭系统业务配置界面；</span></p><p class=\"p3\"><span class=\"s1\">a)\u200B&nbsp;</span><span>界面示意</span></p><p class=\"p9\"><img <img src='ftp://ftpFile:123456@192.168.1.141:21/ftp/image/0.png' style=\"width:8.017361in;height:5.617361in;vertical-align:text-bottom;\"></p><p class=\"p9\"></p><p class=\"p9\"></p><p class=\"p9\"><span>图 178个人敏感授权界面示意图</span></p><p class=\"p9\"></p><p class=\"p1\"></p><p class=\"p1\"><span class=\"s3\">1、该功能是否是一个权限转移操作？操作用户权限（YEW_QB_PZ_YHQX）表实现把A的权限转移到B，不是的话是否有一个用户与相关数据的关联关系表存在？</span></p><p class=\"p1\"><span class=\"s3\">2、个人敏感报量从哪里统计？</span></p><p class=\"p10\"><span class=\"s3\">个人敏感报文（YEW_QB_CL_GRMGBW）</span></p><p class=\"p11\"><span class=\"s5\">a)\u200B&nbsp;</span><span>处理流程图</span></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"><span class=\"s3\">个人资料与公共资料根据页面示意图以及功能介绍来说，存在几个问题</span></p><p class=\"p12\"><span class=\"s6\">1、\u200B&nbsp;</span><span class=\"s3\">左侧的资料菜单关系表无，无法构建该树形菜单（考虑是否新增一张资料分组关系表，以PID的形式实现该树形菜单关系）</span></p><p class=\"p12\"><span class=\"s6\">2、\u200B&nbsp;</span><span class=\"s3\">用户与资料菜单的关联关系，与资料的关联关系无（考虑是否新增一张用户与资料表</span><span class=\"s7\">（QB_GYWB_ZL），用户与</span><span class=\"s3\">资料分组关系表的关联关系表）</span></p><p class=\"p13\"><span class=\"s8\">3、\u200B&nbsp;</span><span class=\"s3\">资料正文表</span><span class=\"s9\">（QB_GYWB_ZLZW）是用来存储一段介绍文本加上一个任意类型文件？</span></p><p class=\"p14\"><span class=\"s8\">4、\u200B&nbsp;</span><span class=\"s3\">资料（QB_GYWB_ZL）表是否缺少公共还是私人资料标示字段？</span></p><p class=\"p14\"><span class=\"s8\">5、\u200B&nbsp;</span><span class=\"s3\">公共资料与个人资料是否是同一张表？是是否缺少资料类型字段?</span></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"><span class=\"s3\">结果：新增资料分组关系表以及其他一些细节问题，还不够清楚</span></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p><p class=\"p1\"></p></body></html>\n";
        //String da = Html2String.readfile("F:\\FTP\\ftp\\20181019.html");
        List<String> ss = match(source, "img", "src");

        //System.out.println(s+"--------------");
        for (String s : ss) {
            String path = s.substring(s.indexOf("ftp"), s.lastIndexOf(".")).substring(0, s.substring(s.indexOf("ftp"), s.lastIndexOf(".")).lastIndexOf("/") );
            System.out.println(path+"+++++++++++++++++++++");
            String name  =     s.substring(s.indexOf("image"));
             String names = name.substring(0,name.indexOf("\"")).substring(name.substring(0,name.indexOf("\"")).indexOf("/")+1);
             String sss = names.substring(0,names.lastIndexOf("'"));
            System.out.println(  sss+"***********");
            System.out.println(source.replace(path,"192.168.1.141:10002"));

        }



            // String name  =     s.substring(s.indexOf("image"));
            // String names = name.substring(0,name.indexOf("\""));
            /*System.out.println(  names.substring(names.indexOf("/")+1)+"***********");
            System.out.println(names+"+++++++++++++++++++++");*/
            //  String name  =  strings.get(0).substring(strings.get(0).indexOf("image")).substring(0,strings.get(0).substring(strings.get(0).indexOf("image")).indexOf("\"")).substring(0,strings.get(0).substring(strings.get(0).indexOf("image")).indexOf("\""));
            // String name1 = name.substring(name.indexOf("/")+1);
            // System.out.println(name1);
            // String newStr = replaceHtmlTagd(s, "img", "src", "src=\"D://uplodFile//images//", "\"");
            // System.out.println(newStr);

       /* String newStr = replaceHtmlTagd(da, "img", "src", "src=\"D://uplodFile//images//1.jpeg", "\"");
        System.out.println("       替换后为:" + newStr);*/
        // }
   /*public static void main(String[] args) {
        StringBuffer content = new StringBuffer();
        content.append(" <p class=\"p9\">");
        content.append("<img src=\"..//word/media/0.png\" style=\"width:8.017361in;height:5.617361in;vertical-align:text-bottom;\"></p>");
        content.append("<p class=\"p9\"></p>\n" +
                "<p class=\"p9\"></p>\n" +
                "<p class=\"p9\">");
        String da = Html2String.readfile("f:/20181019.html");
        String s = "<p>Image 1:<img width=\"199\" src=\"_image/12/label\" alt=\"\"/> Image 2: <img width=\"199\" src=\"_image/12/label\" alt=\"\"/><img width=\"199\" src=\"_image/12/label\" alt=\"\"/></p>";

        stringControl(da);
      /*  System.out.println("原始字符串为:" + da);
        String newStr = replaceHtmlTagd(da, "img", "src", "src=\"D://uplodFile//images//1.jpeg", "\"",0);
        System.out.println("       替换后为:" + newStr);
        writeListToFile(newStr,"123.html");*//*
    }*/
    }


    /**
     * 实现写操作方法
     */
    public static void writeListToFile(String data, String name) {
        File file = new File(name);// 要写入的文件路径
        if (!file.exists()) {// 判断文件是否存在
            try {
                file.createNewFile();// 如果文件不存在创建文件
                System.out.println("文件" + file.getName() + "不存在已为您创建!");
            } catch (IOException e) {
                System.out.println("创建文件异常!");
                e.printStackTrace();
            }
        } else {
            //删除本地的临时文件
            while (file.exists()) {
                System.gc();
                file.delete();
                System.out.println("文件" + file.getName() + "已删除!");
            }
        }
        FileOutputStream fos = null;
        PrintStream ps = null;
        try {
            fos = new FileOutputStream(file, true);// 文件输出流	追加
            ps = new PrintStream(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ps.print(data); // 执行写操作
        ps.close();    // 关闭流
    }

}

