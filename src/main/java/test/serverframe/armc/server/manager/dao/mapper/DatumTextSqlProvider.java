package test.serverframe.armc.server.manager.dao.mapper;

import org.apache.ibatis.jdbc.SQL;
import test.serverframe.armc.server.manager.domain.DatumText;

public class DatumTextSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QB_GYWB_ZLZW
     *
     * @mbg.generated
     */
    public String insertSelective(DatumText record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("QB_GYWB_ZLZW");
        
        if (record.getZlbh() != null) {
            sql.VALUES("ZLBH", "#{zlbh,jdbcType=VARCHAR}");
        }
        
        if (record.getKzdx() != null) {
            sql.VALUES("KZDX", "#{kzdx,jdbcType=DECIMAL}");
        }
        
        if (record.getMtgs() != null) {
            sql.VALUES("MTGS", "#{mtgs,jdbcType=VARCHAR}");
        }
        
        if (record.getWjzs() != null) {
            sql.VALUES("WJZS", "#{wjzs,jdbcType=DECIMAL}");
        }
        
        if (record.getYs() != null) {
            sql.VALUES("YS", "#{ys,jdbcType=DECIMAL}");
        }
        
        if (record.getKzlj() != null) {
            sql.VALUES("KZLJ", "#{kzlj,jdbcType=CLOB}");
        }
        
        if (record.getZwwb() != null) {
            sql.VALUES("ZWWB", "#{zwwb,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QB_GYWB_ZLZW
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(DatumText record) {
        SQL sql = new SQL();
        sql.UPDATE("QB_GYWB_ZLZW");
        
        if (record.getKzdx() != null) {
            sql.SET("KZDX = #{kzdx,jdbcType=DECIMAL}");
        }
        
        if (record.getMtgs() != null) {
            sql.SET("MTGS = #{mtgs,jdbcType=VARCHAR}");
        }
        
        if (record.getWjzs() != null) {
            sql.SET("WJZS = #{wjzs,jdbcType=DECIMAL}");
        }
        
        if (record.getYs() != null) {
            sql.SET("YS = #{ys,jdbcType=DECIMAL}");
        }
        
        if (record.getKzlj() != null) {
            sql.SET("KZLJ = #{kzlj,jdbcType=CLOB}");
        }
        
        if (record.getZwwb() != null) {
            sql.SET("ZWWB = #{zwwb,jdbcType=CLOB}");
        }
        
        sql.WHERE("ZLBH = #{zlbh,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}