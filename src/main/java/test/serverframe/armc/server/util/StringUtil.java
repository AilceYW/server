package test.serverframe.armc.server.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lp on 2018/8/31.
 */
public class StringUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    /**
     * 字符串转list（以逗号分隔）
     * @param str string类型
     * @return
     */
    public static List<String> stringToList(String str) {
        List<String> strList = null;
        try {
            String[] s = str.split(",");
            if (s.length == 0) throw new Exception("数组为空，请检查！");
            strList = Arrays.asList(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    /**
     * 检查是否包含% 或 _
     *
     * @param
     * @return
     */
    public static String checkParam(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuffer sbf = new StringBuffer();
        for (char c : str.toCharArray()) {
            if (c == '%' || c == '_' || c == '.') {
                sbf.append("\\" + c);
            } else {
                sbf.append(c);
            }
        }
        return sbf.toString();
    }

    /*
     * @Descripttion  生成随机id
     * @Author jiangyuanwei
     * @Date 2018/9/25 18:25
     * @Param [date]
     * @Return
     **/
    public static String getId() {
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        String s = "<img alt=\"\" src=\"http://192.168.1.141:10002/public/image/2-XXX池1.png\" style=\"height:768px; width:1080px\" />";

        System.err.println(  s.substring(s.indexOf("http"),s.lastIndexOf(".")).substring(0,s.substring(s.indexOf("http"),s.lastIndexOf(".")).lastIndexOf("/")));

    }
}
