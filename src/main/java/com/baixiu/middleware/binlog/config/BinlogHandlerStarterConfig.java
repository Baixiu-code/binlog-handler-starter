package com.baixiu.middleware.binlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

/**
 * ���Խ��������ļ����� binlogStarter ��ͷ�����á�
 * ������ʽ��properties �� yaml ����.
 *  *  *******����1#listener ��������1��start***********
 *
 * common-mq.rocketmq.transport.address=127.0.0.1:9876
 * common-mq.rocketmq.transport.app=mq-client-test
 * #common-mq.rocketmq.transport.user=baixiu
 * #common-mq.rocketmq.transport.password=baixiuTest
 * common-mq.rocketmq.producer.name=messageProducer
 * common-mq.rocketmq.listeners[0].topicName=test
 * common-mq.rocketmq.listeners[0].listenerBeanName=testConsumerMsg
 *  *******����1#listener ��������1��end*********** *

 *
 *  *******����2* #binlog���ݶ��ģ�start***********
 * binlog-starter:
 *   #binlog���� drc
 *   clientType: drc
 *   handlers:
 *     -
 *       table: sku
 *       handler: skuInfoDataFacadeImpl   #����ǰ������ݴ�����
 *     -
 *       table: brand                     #����binlog��Ӧ�ı���
 *       handler: brandInfoDataFacadeImpl #����ǰ������ݴ�����
 *  *******����2* #binlog���ݶ��ģ�end***********
 *
 *
 * @author baixiu
 * @date ����ʱ�� 2023/12/11 8:45 PM
 */
@ConfigurationProperties(prefix = "binlog-starter")
public class BinlogHandlerStarterConfig {

    /**
     * ���Խ���binlog��Ϣ�ͻ��˵�ö�����͡�
     * ����ö��ֵ.
     * DRC,CANAL
     */
    private String clientType;

    /**
     * binlog table match handlers
     * key:tableName
     * value:����ʵ�ֵ�handler bean ���� ��Ҫ��ʹ�÷���spring������������Ʊ���һ��
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
