/********************************************
 * Druid连接池配置类
 *
 * @author zwq
 * @create 2018-06-02
 *********************************************/

package test.serverframe.armc.server.manager.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
public class DruidConfiguration {

    private static String url;
    private static String username;
    private static String password;
    private static String driver_class_namel;
    private static String type;
    private static int max_active;
    private static int initial_size;
    private static int min_idle;
    private static int max_wait;
    private static int time_between_eviction_runs_millis;
    private static int min_evictable_idle_time_millis;
    private static boolean test_while_idle;
    private static boolean test_on_borrow;
    private static boolean test_on_return;
    private static boolean poolPreparedStatements;

    public static String getUrl() {
        return url;
    }

    public void setUrl(String _url) {
        url = _url;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public static String getDriver_class_namel() {
        return driver_class_namel;
    }

    public void setDriver_class_namel(String _driver_class_namel) {
        driver_class_namel = _driver_class_namel;
    }

    public static String getType() {
        return type;
    }

    public void setType(String _type) {
        type = _type;
    }

    public static int getMax_active() {
        return max_active;
    }

    public void setMax_active(int _max_active) {
        max_active = _max_active;
    }

    public static int getInitial_size() {
        return initial_size;
    }

    public void setInitial_size(int _initial_size) {
        initial_size = _initial_size;
    }

    public static int getMin_idle() {
        return min_idle;
    }

    public void setMin_idle(int _min_idle) {
        min_idle = _min_idle;
    }

    public static int getMax_wait() {
        return max_wait;
    }

    public void setMax_wait(int _max_wait) {
        max_wait = _max_wait;
    }

    public static int getTime_between_eviction_runs_millis() {
        return time_between_eviction_runs_millis;
    }

    public void setTime_between_eviction_runs_millis(int _time_between_eviction_runs_millis) {
        time_between_eviction_runs_millis = _time_between_eviction_runs_millis;
    }

    public static int getMin_evictable_idle_time_millis() {
        return min_evictable_idle_time_millis;
    }

    public void setMin_evictable_idle_time_millis(int _min_evictable_idle_time_millis) {
        min_evictable_idle_time_millis = _min_evictable_idle_time_millis;
    }

    public static boolean isTest_while_idle() {
        return test_while_idle;
    }

    public void setTest_while_idle(boolean _test_while_idle) {
        test_while_idle = _test_while_idle;
    }

    public static boolean isTest_on_borrow() {
        return test_on_borrow;
    }

    public void setTest_on_borrow(boolean _test_on_borrow) {
        test_on_borrow = _test_on_borrow;
    }

    public static boolean isTest_on_return() {
        return test_on_return;
    }

    public void setTest_on_return(boolean _test_on_return) {
        test_on_return = _test_on_return;
    }

    public static boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean _poolPreparedStatements) {
        poolPreparedStatements = _poolPreparedStatements;
    }

    @Bean
    public static DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(getUrl());
        datasource.setDriverClassName(getDriver_class_namel());
        datasource.setUsername(getUsername());
        datasource.setPassword(getPassword());
        datasource.setInitialSize(Integer.valueOf(getInitial_size()));
        datasource.setMinIdle(Integer.valueOf(getMin_idle()));
        datasource.setMaxWait(Long.valueOf(getMax_wait()));
        datasource.setMaxActive(Integer.valueOf(getMax_active()));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(getMin_evictable_idle_time_millis()));
        try {
            datasource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }
}
