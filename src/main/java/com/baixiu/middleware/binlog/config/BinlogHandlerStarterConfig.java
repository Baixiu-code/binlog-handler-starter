package com.baixiu.middleware.binlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

/**
 * 用以解析配置文件中以 binlogStarter 开头的配置。
 * 配置形式：properties 或 yaml 均可.
 *  *  *******配置1#listener 数据配置1：start***********
 *
 * common-mq.rocketmq.transport.address=127.0.0.1:9876
 * common-mq.rocketmq.transport.app=mq-client-test
 * #common-mq.rocketmq.transport.user=baixiu
 * #common-mq.rocketmq.transport.password=baixiuTest
 * common-mq.rocketmq.producer.name=messageProducer
 * common-mq.rocketmq.listeners[0].topicName=test
 * common-mq.rocketmq.listeners[0].listenerBeanName=testConsumerMsg
 *  *******配置1#listener 数据配置1：end*********** *

 *
 *  *******配置2* #binlog数据订阅：start***********
 * binlog-starter:
 *   #binlog类型 drc
 *   clientType: drc
 *   handlers:
 *     -
 *       table: sku
 *       handler: skuInfoDataFacadeImpl   #处理当前表的数据处理类
 *     -
 *       table: brand                     #监听binlog对应的表名
 *       handler: brandInfoDataFacadeImpl #处理当前表的数据处理类
 *  *******配置2* #binlog数据订阅：end***********
 *
 *
 * @author baixiu
 * @date 创建时间 2023/12/11 8:45 PM
 */
@ConfigurationProperties(prefix = "binlog-starter")
public class BinlogHandlerStarterConfig {

    /**
     * 用以解析binlog消息客户端的枚举类型。
     * 两种枚举值.
     * DRC,CANAL
     */
    private String clientType;

    /**
     * binlog table match handlers
     * key:tableName
     * value:具体实现的handler bean 名称 需要和使用方在spring容器定义的名称保持一致
     */
    private List<BinlogTableMatchHandlerConfig> binlogTableMatchHandlers;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public List<BinlogTableMatchHandlerConfig> getBinlogTableMatchHandlers() {
        return binlogTableMatchHandlers;
    }

    public void setBinlogTableMatchHandlers(List<BinlogTableMatchHandlerConfig> binlogTableMatchHandlers) {
        this.binlogTableMatchHandlers = binlogTableMatchHandlers;
    }
}
