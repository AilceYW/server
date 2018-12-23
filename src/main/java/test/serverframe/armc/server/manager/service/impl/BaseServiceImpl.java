/********************************************
 * Service层基类的实现
 *
 * @author zwq
 * @create 2018-05-31 22:30
 *********************************************/

package test.serverframe.armc.server.manager.service.impl;

import test.serverframe.armc.server.manager.dao.BaseDao;
import test.serverframe.armc.server.manager.service.BaseService;
import test.serverframe.armc.server.util.GenericsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


@Service
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    @SuppressWarnings("unchecked")
    public String getNameSpace() {
        @SuppressWarnings("rawtypes")
        Class<T> clazz = (Class) GenericsUtils.getSuperClassGenricType(this.getClass());
        return clazz.getName();
    }

    @Autowired
    private BaseDao<T, PK> baseDao;

    public BaseDao<T, PK> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    public int insert(T entity) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.insert(entity);
    }

    public int insertSelective(T record) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.insertSelective(record);
    }

    public T selectByPrimaryKey(PK id) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(T record) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(T record) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.updateByPrimaryKeySelective(record);
    }

    public int deleteByPrimaryKey(PK id) {
        baseDao.setNameSpace(getNameSpace());
        return baseDao.deleteByPrimaryKey(id);
    }

    public PageInfo<T> pageFind(String statementKey, int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return baseDao.pageFind(statementKey, pageNum, pageSize, parameter);
    }

    /*public int deleteByPrimaryKeyLists(String statementKey, List<String> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return baseDao.deleteByKeyList(statementKey,list);
    }*/
}


