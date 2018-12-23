/********************************************
 * Dao层基础类
 *
 * @author zwq
 * @create 2018-05-31 18:36
 *********************************************/

package test.serverframe.armc.server.manager.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("baseDao")
public class BaseDao<T, PK extends Serializable> extends SqlSessionDaoSupport implements Serializable {

    private static final long serialVersionUID = 7623507504198633838L;

    private final String POSTFIX = "Mapper";

    private final String _INSERT = ".insert";

    private final String _INSERTSELECTIVE = ".insertSelective";

    private final String _SELECTBYPRIMARYKEY = ".selectByPrimaryKey";

    private final String _UPDATEBYPRIMARYKEY = ".updateByPrimaryKey";

    private final String _UPDATEBYPRIMARYKEYSELECTIVE = ".updateByPrimaryKeySelective";

    private final String _DELETEBYPRIMARYKEY = ".deleteByPrimaryKey";

    private String nameSpace = "";

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public void setNameSpace(String _nameSpace) {
        this.nameSpace = (_nameSpace.contains("domain") ? _nameSpace.replace("domain", "dao.mapper") : _nameSpace) + POSTFIX;
    }


    public int insert(T entity) {
        return getSqlSession().insert(this.nameSpace + _INSERT, entity);
    }


    public int insertSelective(T record) {
        return getSqlSession().insert(this.nameSpace + _INSERTSELECTIVE, record);
    }

    public T selectByPrimaryKey(PK id) {
        return getSqlSession().selectOne(this.nameSpace + _SELECTBYPRIMARYKEY, id);
    }


    @SuppressWarnings("unchecked")
    /**************************************
     * 根据输入Object参数，查询数据库，返回List<M>
     * @param statementKey  对应Mapper的接口名称
     * @param parameter     查询条件
     * @return 查询到的数据结构
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public <M> List<M> selectAllModel(String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return (List<M>) getSqlSession().selectList(statementKey, getParamsFromObj(parameter));
    }

    public int updateByPrimaryKey(T record) {
        return getSqlSession().update(this.nameSpace + _UPDATEBYPRIMARYKEY, record);
    }

    public int updateByPrimaryKeySelective(T record) {
        return getSqlSession().update(this.nameSpace + _UPDATEBYPRIMARYKEYSELECTIVE, record);
    }

    public int deleteByPrimaryKey(PK id) {
        return getSqlSession().delete(this.nameSpace + _DELETEBYPRIMARYKEY, id);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map getParamsFromObj(Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map params = new HashMap();
        if (parameter != null) {
            if (parameter instanceof Map) {
                params.putAll((Map) parameter);
            } else {
                Map parameterObject = PropertyUtils.describe(parameter);
                params.putAll(parameterObject);
            }
        }
        if (params.containsKey("class")) {
            params.remove("class");
        }
        return params;
    }

    @SuppressWarnings("rawtypes")
    public PageInfo<T> pageFind(String statementKey, int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        PageHelper.startPage(pageNum, pageSize);
        List<T> list = getSqlSession().selectList(statementKey, getParamsFromObj(parameter));
        @SuppressWarnings("unchecked")
        PageInfo<T> pageInfo = new PageInfo(list);

        return pageInfo;
    }


    public List<T> findTop(int top, String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<T> list = getSqlSession().selectList(statementKey, getParamsFromObj(parameter), new RowBounds(0, top));
        return list;
    }

    public T findTopOne(String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<T> list = findTop(1, statementKey, parameter);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

   /* @SuppressWarnings({"rawtypes", "unchecked"})
    public int deleteByKeyList(String statementKey,List<String> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
       int count = getSqlSession().delete(statementKey,list);
        return count;
    }*/
}

