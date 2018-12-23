/********************************************
 * 数据字典行列模型
 *
 * @author zwq
 * @create 2018-07-28
 *********************************************/

package test.serverframe.armc.server.manager.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("全量字典模型")
public class DicModel {

    @ApiModelProperty("字典分类Id")
    private Long dicTypeId;
    @ApiModelProperty("字典分类名称")
    private String dicTypeName;
    @ApiModelProperty("字典值Id")
    private Long dicValueId;
    @ApiModelProperty("字典值名称")
    private String dicValueName;
    @ApiModelProperty("字典值序号")
    private Long dicValueIndex;

    public Long getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Long dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public String getDicTypeName() {
        return dicTypeName;
    }

    public void setDicTypeName(String dicTypeName) {
        this.dicTypeName = dicTypeName;
    }

    public Long getDicValueId() {
        return dicValueId;
    }

    public void setDicValueId(Long dicValueId) {
        this.dicValueId = dicValueId;
    }

    public String getDicValueName() {
        return dicValueName;
    }

    public void setDicValueName(String dicValueName) {
        this.dicValueName = dicValueName;
    }

    public Long getDicValueIndex() {
        return dicValueIndex;
    }

    public void setDicValueIndex(Long dicValueIndex) {
        this.dicValueIndex = dicValueIndex;
    }
}
