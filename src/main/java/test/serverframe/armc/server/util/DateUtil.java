package test.serverframe.armc.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 转换日期格式为yyyy-MM-dd
     * @param date 日期
     * @return yyyy-MM-dd字符串
     */
    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
