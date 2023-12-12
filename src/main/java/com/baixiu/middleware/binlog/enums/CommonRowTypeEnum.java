package com.baixiu.middleware.binlog.enums;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.Getter;
import lombok.Setter;

public enum CommonRowTypeEnum {

    INSERT(0, 1),
    UPDATE(1, 2),
    DELETE(2, 3);

    CommonRowTypeEnum(int index,int value){
        this.index=index;
        this.value=value;
    }
    @Getter
    @Setter
    private final int index;
    @Getter@Setter
    private final int value;


    public final int getNumber() {
        return this.value;
    }

    public static CommonRowTypeEnum valueOf(int value) {
        switch (value) {
            case 1:
                return INSERT;
            case 2:
                return UPDATE;
            case 3:
                return DELETE;
            default:
                return null;
        }
    }

    public static CommonRowTypeEnum transType(CanalEntry.EventType eventType){
        switch (eventType){
            case INSERT:
                return INSERT;
            case DELETE:
                return DELETE;
            case UPDATE:
                return UPDATE;
            default:
                return null;
        }
    }

    public static CommonRowTypeEnum transType(String eventType) {
        switch (eventType){
            case "INSERT":
                return INSERT;
            case "DELETE":
                return DELETE;
            case "UPDATE":
                return UPDATE;
            default:
                return null;
        }
    }
}
