/********************************************
 * Service层基类
 *
 * @author zwq
 * @create 2018-05-31 22:23
 *********************************************/

package test.serverframe.armc.server.manager.service;


import com.github.pagehelper.PageInfo;

import java.lang.reflect.InvocationTargetException;

public interface BaseService<T, PK> {

    /**
     * 新增实体
     *
     * @param record 实体模型（Domain）
     * @return 新增的记录数
     */
    int insert(T record);

    /**
     * 新增实体，NULL字段忽略
     *
     * @param record 实体模型（Domain）
     * @return 新增的记录数
     */
    int insertSelective(T record);

    /**
     * 根据实体主键，查询实体模型
     *
     * @param id 实体主键
     * @return 实体模型
     */
    T selectByPrimaryKey(PK id);

    /**
     * 根据实体主键，更新实体
     *
     * @param record 实体
     * @return 更新的记录数
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据实体主键，更新实体，NULL字段忽略
     *
     * @param record 实体
     * @return 更新的记录数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据实体主键，删除实体
     *
     * @param id 实体主键
     * @return 删除的记录数
     */
    int deleteByPrimaryKey(PK id);


    /**
     * 分页查询
     * 默认子类都实现pageFind接口
     *
     * @param pageNum   页码
     * @param pageSize  页尺寸
     * @param parameter 查询条件
     * @return 分页查询结果，以实体模型组织（Domain）
     */
    PageInfo<T> pageFind(int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}

