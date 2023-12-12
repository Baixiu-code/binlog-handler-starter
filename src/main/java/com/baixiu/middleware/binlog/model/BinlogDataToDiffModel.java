package com.baixiu.middleware.binlog.model;

import com.baixiu.middleware.binlog.enums.CommonRowTypeEnum;
import java.util.List;
import java.util.Map;

/**
 * binlog data to  diff model �м�ת������
 *
 * @author baixiu
 * @date ����ʱ�� 2023/12/12 4:24 PM
 */
public class BinlogDataToDiffModel {

    private List<BinlogTableRowDiffModel> binlogTableRowDiffModels;

    /**
     * ���е��ֶζ�Ӧ��ֵ�����ݳ���ӿ� {@link }
     */
    private Map<String,String> allFieldMaps;

    private CommonRowTypeEnum commonRowTypeEnum;

    public BinlogDataToDiffModel(List<BinlogTableRowDiffModel> changeList, Map<String, String> fieldsMap, CommonRowTypeEnum transType) {
        this.commonRowTypeEnum=transType;
        this.allFieldMaps=fieldsMap;
        binlogTableRowDiffModels=changeList;
    }

    public List<BinlogTableRowDiffModel> getBinlogTableRowDiffModels() {
        return binlogTableRowDiffModels;
    }

    public void setBinlogTableRowDiffModels(List<BinlogTableRowDiffModel> binlogTableRowDiffModels) {
        this.binlogTableRowDiffModels = binlogTableRowDiffModels;
    }

    public Map<String, String> getAllFieldMaps() {
        return allFieldMaps;
    }

    public void setAllFieldMaps(Map<String, String> allFieldMaps) {
        this.allFieldMaps = allFieldMaps;
    }

    public CommonRowTypeEnum getCommonRowTypeEnum() {
        return commonRowTypeEnum;
    }

    public void setCommonRowTypeEnum(CommonRowTypeEnum commonRowTypeEnum) {
        this.commonRowTypeEnum = commonRowTypeEnum;
    }
}
