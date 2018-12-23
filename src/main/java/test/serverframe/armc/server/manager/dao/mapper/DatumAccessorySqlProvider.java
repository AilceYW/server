package test.serverframe.armc.server.manager.dao.mapper;

import org.apache.ibatis.jdbc.SQL;
import test.serverframe.armc.server.manager.domain.DatumAccessory;

public class DatumAccessorySqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QB_GYWB_ZLFJ
     *
     * @mbg.generated
     */
    public String insertSelective(DatumAccessory record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("QB_GYWB_ZLFJ");
        
        if (record.getFjid() != null) {
            sql.VALUES("FJID", "#{fjid,jdbcType=VARCHAR}");
        }
        
        if (record.getZlbh() != null) {
            sql.VALUES("ZLBH", "#{zlbh,jdbcType=VARCHAR}");
        }
        
        if (record.getWjm() != null) {
            sql.VALUES("WJM", "#{wjm,jdbcType=VARCHAR}");
        }
        
        if (record.getFjlx() != null) {
            sql.VALUES("FJLX", "#{fjlx,jdbcType=VARCHAR}");
        }
        
        if (record.getMtgs() != null) {
            sql.VALUES("MTGS", "#{mtgs,jdbcType=VARCHAR}");
        }
        
        if (record.getZs() != null) {
            sql.VALUES("ZS", "#{zs,jdbcType=DECIMAL}");
        }
        
        if (record.getWjdx() != null) {
            sql.VALUES("WJDX", "#{wjdx,jdbcType=DECIMAL}");
        }
        
        if (record.getCclj() != null) {
            sql.VALUES("CCLJ", "#{cclj,jdbcType=CLOB}");
        }
        
        if (record.getFjwb() != null) {
            sql.VALUES("FJWB", "#{fjwb,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QB_GYWB_ZLFJ
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(DatumAccessory record) {
        SQL sql = new SQL();
        sql.UPDATE("QB_GYWB_ZLFJ");
        
        if (record.getZlbh() != null) {
            sql.SET("ZLBH = #{zlbh,jdbcType=VARCHAR}");
        }
        
        if (record.getWjm() != null) {
            sql.SET("WJM = #{wjm,jdbcType=VARCHAR}");
        }
        
        if (record.getFjlx() != null) {
            sql.SET("FJLX = #{fjlx,jdbcType=VARCHAR}");
        }
        
        if (record.getMtgs() != null) {
            sql.SET("MTGS = #{mtgs,jdbcType=VARCHAR}");
        }
        
        if (record.getZs() != null) {
            sql.SET("ZS = #{zs,jdbcType=DECIMAL}");
        }
        
        if (record.getWjdx() != null) {
            sql.SET("WJDX = #{wjdx,jdbcType=DECIMAL}");
        }
        
        if (record.getCclj() != null) {
            sql.SET("CCLJ = #{cclj,jdbcType=CLOB}");
        }
        
        if (record.getFjwb() != null) {
            sql.SET("FJWB = #{fjwb,jdbcType=CLOB}");
        }
        
        sql.WHERE("FJID = #{fjid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}