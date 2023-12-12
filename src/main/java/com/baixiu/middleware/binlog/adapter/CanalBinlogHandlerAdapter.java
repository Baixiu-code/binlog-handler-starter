package com.baixiu.middleware.binlog.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.baixiu.middleware.binlog.consts.CommonConsts;
import com.baixiu.middleware.binlog.core.AbstractBinlogHandler;
import com.baixiu.middleware.binlog.core.BinlogTableHandlerRouter;
import com.baixiu.middleware.binlog.enums.CommonRowTypeEnum;
import com.baixiu.middleware.binlog.model.BinlogData;
import com.baixiu.middleware.binlog.model.BinlogDataToDiffModel;
import com.baixiu.middleware.binlog.model.BinlogTableRowDiffModel;
import com.baixiu.middleware.mq.model.CommonMessage;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * canal binlog handler adapter
 * 当property配置的clientType=canal时进行注入bean
 * canal client 用以解析 mq -starter 发送过来的消费消息
 * @author baixiu
 * @date 创建时间 2023/12/11 8:39 PM
 */
@Slf4j
public class CanalBinlogHandlerAdapter implements BinlogHandlerAdapter{

    @Autowired
    private BinlogTableHandlerRouter binlogTableHandlerRouter;

    @Override
    public BinlogData deserializationMQMsg(CommonMessage mqMsg) {
        FlatMessage flatMessage = JSON.parseObject(mqMsg.getText(),FlatMessage.class);

        BinlogData binLogData=new BinlogData ();
        if(flatMessage!=null){
            binLogData.setBinlogDataObject(flatMessage);
        }
        return binLogData;
    }

    @Override
    public void handleBinLogData(BinlogData binLogData) throws Exception {

        if(binLogData==null || binLogData.getBinlogDataObject()==null){
            return;
        }

        FlatMessage flatMessage= (FlatMessage) binLogData.getBinlogDataObject ();
        List<Map<String, String>> rowDatas = flatMessage.getData();
        List<Map<String, String>> oldDatas = flatMessage.getOld();
        String tableName = flatMessage.getTable();
        AbstractBinlogHandler handler = binlogTableHandlerRouter.ALL_TABLE_HANDLERS.get(tableName);


        for (int i = 0; i < rowDatas.size(); i++) {
            Map<String, String> rowData = rowDatas.get(i);
            Map<String, String> oldData = new HashMap<>(i,0.75f);
            if (oldDatas != null && oldDatas.size() == rowDatas.size()) {
                oldData = oldDatas.get(i);
            }
            Map<String, String> fieldsMaps = Maps.newHashMapWithExpectedSize(20);
            BinlogDataToDiffModel binlogDataToDiffModel = transRowDataToAllBinlogData(handler, rowData, oldData
                    , fieldsMaps, flatMessage.getType());

            switch (binlogDataToDiffModel.getCommonRowTypeEnum()) {
                case INSERT:
                    log.info("Canal.handleMessage.binlogTransConfigToMap.INSERT.{}"
                            , JSON.toJSONString(binlogDataToDiffModel.getCommonRowTypeEnum()));
                    handler.insert(binlogDataToDiffModel.getAllFieldMaps(),binlogDataToDiffModel.getBinlogTableRowDiffModels());
                    break;
                case UPDATE:
                    log.info("Canal.handleMessage.binlogTransConfigToMap.UPDATE.{}"
                            ,JSON.toJSONString(binlogDataToDiffModel.getCommonRowTypeEnum()));
                    handler.update(binlogDataToDiffModel.getAllFieldMaps(),binlogDataToDiffModel.getBinlogTableRowDiffModels());
                    break;
                case DELETE:
                    Map<String, String> delMap = getBeforeColumnsFromBinlogData(handler, oldData);
                    log.info("Canal.handleMessage.binlogTransConfigToMap.DELETE");
                    handler.delete(delMap);
                    break;
                default:
                    log.info("CanalBinlogClientAdapter.handleMessage.binlogTransConfigToMap.default.{}"
                            ,JSON.toJSONString(binlogDataToDiffModel.getCommonRowTypeEnum()));
                    break;
            }
        }
    }


    public static BinlogDataToDiffModel transRowDataToAllBinlogData(AbstractBinlogHandler binlogData, Map<String, String> afterColumns
            , Map<String, String> beforeColumns, Map<String, String> fieldsMap, String type) {

        try {

            String[] updateFields = binlogData.getUpdateFields();
            String[] keyFields = binlogData.getFields();

            List<BinlogTableRowDiffModel> changeList = new ArrayList<> ();

            for (String key : afterColumns.keySet()) {

                if (keyFields.length == 1 && ArrayUtils.contains(keyFields, CommonConsts.BINLOG_ALL_FIELDS)) {
                    fieldsMap.put(key, afterColumns.get(key));
                } else if (ArrayUtils.contains(keyFields, key)) {
                    fieldsMap.put(key, afterColumns.get(key));
                }

                if (beforeColumns != null && !beforeColumns.isEmpty() && beforeColumns.get(key) != null) {
                    BinlogTableRowDiffModel bean = new BinlogTableRowDiffModel();
                    bean.setField(key);
                    bean.setAfter(afterColumns.get(key));
                    bean.setBefore(beforeColumns.get(key));
                    if (updateFields.length == 1 && ArrayUtils.contains(updateFields,CommonConsts.BINLOG_ALL_FIELDS)) {
                        changeList.add(bean);
                    } else if (ArrayUtils.contains(updateFields, key)) {
                        changeList.add(bean);
                    }
                }
            }
            BinlogDataToDiffModel data = new BinlogDataToDiffModel(changeList, fieldsMap, CommonRowTypeEnum.transType(type));
            log.info("transRowDataToAllBinlogData.changeList:{}.fieldsMap{}.data{}"
                    ,JSON.toJSONString(changeList), JSON.toJSONString(fieldsMap), JSON.toJSONString(data));
            return data;
        } catch (Exception e) {
            log.error("handleMessage.transRowDataToAllBinlogData.handleMessage.error.{}", JSON.toJSONString(binlogData), e);
        }

        return null;
    }


    /**
     * 删除操作
     * 不同的表需要从binlogData中获取的信息不同，这里抽取
     *
     * @return
     */
    private Map<String, String> getBeforeColumnsFromBinlogData(AbstractBinlogHandler binlogData, Map<String, String> beforeColumns) {

        Map<String, String> keys = new HashMap<>();
        if (beforeColumns != null && !beforeColumns.isEmpty()) {
            String[] keyFields = binlogData.getFields();
            for (String key : beforeColumns.keySet()) {
                // 找出关心的字段值
                if (ArrayUtils.contains(keyFields, key)) {
                    keys.put(key, beforeColumns.get(key));
                }
            }
        }
        return keys;
    }
}
