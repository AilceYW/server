package test.serverframe.armc.server.manager.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import test.serverframe.armc.server.manager.domain.DatumState;

public interface DatumStateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @Delete({
        "delete from YEW_QB_CL_ZLZT",
        "where ZLBH = #{zlbh,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String zlbh);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @Insert({
        "insert into YEW_QB_CL_ZLZT (ZLBH, CGYYBZ, ",
        "TBYYBZ, KWYYBZ, ",
        "PZBZ, SCBZ, YBCS, ",
        "XFCS, CGCS, ZLZT)",
        "values (#{zlbh,jdbcType=VARCHAR}, #{cgyybz,jdbcType=DECIMAL}, ",
        "#{tbyybz,jdbcType=DECIMAL}, #{kwyybz,jdbcType=DECIMAL}, ",
        "#{pzbz,jdbcType=DECIMAL}, #{scbz,jdbcType=DECIMAL}, #{ybcs,jdbcType=DECIMAL}, ",
        "#{xfcs,jdbcType=DECIMAL}, #{cgcs,jdbcType=DECIMAL}, #{zlzt,jdbcType=VARCHAR})"
    })
    int insert(DatumState record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @InsertProvider(type=DatumStateSqlProvider.class, method="insertSelective")
    int insertSelective(DatumState record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "ZLBH, CGYYBZ, TBYYBZ, KWYYBZ, PZBZ, SCBZ, YBCS, XFCS, CGCS, ZLZT",
        "from YEW_QB_CL_ZLZT",
        "where ZLBH = #{zlbh,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="ZLBH", property="zlbh", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="CGYYBZ", property="cgyybz", jdbcType=JdbcType.DECIMAL),
        @Result(column="TBYYBZ", property="tbyybz", jdbcType=JdbcType.DECIMAL),
        @Result(column="KWYYBZ", property="kwyybz", jdbcType=JdbcType.DECIMAL),
        @Result(column="PZBZ", property="pzbz", jdbcType=JdbcType.DECIMAL),
        @Result(column="SCBZ", property="scbz", jdbcType=JdbcType.DECIMAL),
        @Result(column="YBCS", property="ybcs", jdbcType=JdbcType.DECIMAL),
        @Result(column="XFCS", property="xfcs", jdbcType=JdbcType.DECIMAL),
        @Result(column="CGCS", property="cgcs", jdbcType=JdbcType.DECIMAL),
        @Result(column="ZLZT", property="zlzt", jdbcType=JdbcType.VARCHAR)
    })
    DatumState selectByPrimaryKey(String zlbh);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @UpdateProvider(type=DatumStateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DatumState record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table YEW_QB_CL_ZLZT
     *
     * @mbg.generated
     */
    @Update({
        "update YEW_QB_CL_ZLZT",
        "set CGYYBZ = #{cgyybz,jdbcType=DECIMAL},",
          "TBYYBZ = #{tbyybz,jdbcType=DECIMAL},",
          "KWYYBZ = #{kwyybz,jdbcType=DECIMAL},",
          "PZBZ = #{pzbz,jdbcType=DECIMAL},",
          "SCBZ = #{scbz,jdbcType=DECIMAL},",
          "YBCS = #{ybcs,jdbcType=DECIMAL},",
          "XFCS = #{xfcs,jdbcType=DECIMAL},",
          "CGCS = #{cgcs,jdbcType=DECIMAL},",
          "ZLZT = #{zlzt,jdbcType=VARCHAR}",
        "where ZLBH = #{zlbh,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DatumState record);
}