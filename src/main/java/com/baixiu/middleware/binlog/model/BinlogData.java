package com.baixiu.middleware.binlog.model;


/**
 * @author baixiu
 * @date 创建时间 2023/12/11 8:37 PM
 */
public class BinlogData {

    private Object binlogDataObject;

    public BinlogData(){

    }

    public BinlogData(Object binlogDataObject) {
        this.binlogDataObject= binlogDataObject;
    }

    public Object getBinlogDataObject() {
        return binlogDataObject;
    }

    public void setBinlogDataObject(Object binlogDataObject) {
        this.binlogDataObject = binlogDataObject;
    }
}
