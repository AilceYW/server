package test.serverframe.armc.server.manager.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import test.serverframe.armc.server.manager.dao.mapper.DatumMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTextMapper;
import test.serverframe.armc.server.manager.dao.mapper.DatumTypeMapper;
import test.serverframe.armc.server.manager.domain.Datum;
import test.serverframe.armc.server.manager.domain.DatumType;
import test.serverframe.armc.server.manager.service.DatumTypeService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/9/25 17:47
 * @Description:
 */
@Service("datumTypeService")
public class DatumTypeServiceImpl extends BaseServiceImpl<DatumType, String> implements DatumTypeService {

    @Autowired
    private DatumTypeMapper typeMapper;
    @Autowired
    private DatumMapper datumMapper;
    @Autowired
    private DatumTextMapper datumTextMapper;


    @Override
    public PageInfo<DatumType> pageFind(int pageNum, int pageSize, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return null;
    }


    @Override
    public List<DatumType> selectRoot() {
        return typeMapper.selectRoot();
    }

    @Override
    public int mergeRequest(List<DatumType> datumTypeList) {
      /*  for (DatumType datumType : datumTypeList) {
            //1.查询父节点
            //2.递归查询全部数据
            List<DatumType> datumTypes = this.selectRoot();
            this.selectAllDatumType(datumTypes);
            if (datumType.getChildren().size() > 0) {

            } else {
                if() {

                }
            }

        }*/
        return 0;
    }
    @Transactional
    @Override
    public int deleteDatumType(String id) {
        List<DatumType> list = new ArrayList<>();
        //查询对象
        DatumType datumType = typeMapper.selectByPrimaryKey(id);
        list.add(datumType);
        this.setList(datumType, list);
        try {
            //循环删除
            for (DatumType type : list) {
                typeMapper.deleteByPrimaryKey(type.getId());
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return list.size();
    }

    /**
     * @Descripttion 递归添加数据（做删除）
     * @Author jiangyuanwei
     * @Date 2018/9/30 17:01
     * @Param [datumType, list]
     * @Return
     **/
    public void setList(DatumType datumType, List<DatumType> list) {
        List<DatumType> datumTypes = typeMapper.selectDatumTypes(datumType.getId());
        if (datumTypes.size() > 0) {
            for (DatumType type : datumTypes) {
                list.add(type);
                setList(type, list);
            }
        }
    }

    @Override
    public void selectAllDatumType(List<DatumType> children) {
        for (DatumType datumType : children) {
            //TODO 根据id查询资料类型
            List<DatumType> datumTypes = typeMapper.selectDatumTypes(datumType.getId());
            if (datumTypes.size() > 0) {
                //給下級設置主父ID
                for (DatumType type : datumTypes) {
                    type.setRootId(datumType.getRootId());
                }
                // todo 装数据结构
                datumType.setChildren(datumTypes);
                //TODO 递归
                selectAllDatumType(datumTypes);
            }
        }
    }
    @Transactional
    @Override
    public int updateDatumType(DatumType datumType) {
        int num = 0;
        try {
            if (datumType != null) {
                num = typeMapper.updateDatumType(datumType);
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return num;
    }
    @Override
    @Transactional
    public int addDatumAndDatumText(DatumType datumType) {
        //textService.getAllDatumText(datumType.get);
        int count = 0;
        try {
            count = typeMapper.insertSelective(datumType);
            count = datumMapper.insertSelective(datumType.getDatum());
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

      //  count = datumTextMapper.insertSelective(datumType.getDatum().getDatumText());
        return count;
    }

    @Transactional
    @Override
    public int addDatumAndDatumTypeAndDatumText(Datum datum) {
        int count = 0;
        try {
            if (datum != null) {
               count = typeMapper.insertSelective(datum.getDatumType());
              count = datumMapper.insertSelective(datum);
                count = datumTextMapper.insertSelective(datum.getDatumText());
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return count;
    }
}
