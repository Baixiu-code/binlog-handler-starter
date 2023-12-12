package com.baixiu.middleware.binlog.config;

import com.baixiu.middleware.binlog.adapter.CanalBinlogHandlerAdapter;
import com.baixiu.middleware.binlog.core.AbstractBinlogHandler;
import com.baixiu.middleware.binlog.core.BinlogTableHandlerRouter;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author baixiu
 * @date ����ʱ�� 2023/12/11 8:50 PM
 */
@Configuration
@EnableConfigurationProperties(value = BinlogHandlerStarterConfig.class)
public class BinlogStarterBoot implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    @Autowired
    private BinlogHandlerStarterConfig binlogHandlerStarterConfig;

    /**
     * ���� properties �����е�clientTypeö�٣����������router ע��
     * ���router��mapӳ�䣬key=tableName,value=handler bean Name
     * binlog table handler router
     * @return
     */
    @Bean(name="binlogTableHandlerRouter")
    @ConditionalOnProperty(prefix = "binlog-starter",name={"clientType"})
    public BinlogTableHandlerRouter initStarterConfig(){

        BinlogTableHandlerRouter binlogTableHandlerRouter=null;

        for (BinlogTableMatchHandlerConfig binlogTableMatchHandler : binlogHandlerStarterConfig.getBinlogTableMatchHandlers ()) {
            binlogTableHandlerRouter=new BinlogTableHandlerRouter();
            binlogTableHandlerRouter.ALL_TABLE_HANDLERS= Maps.newHashMapWithExpectedSize(binlogHandlerStarterConfig
                    .getBinlogTableMatchHandlers().size());
            try {
                binlogTableHandlerRouter.ALL_TABLE_HANDLERS.put(binlogTableMatchHandler.getTableName()
                        ,this.findHandlerFromContext(binlogTableMatchHandler.getHandlerName()));
            } catch (Exception e) {
                throw new RuntimeException ("not found bean"+binlogTableMatchHandler.getHandlerName ()+"beans from context.");
            }
        }

        return binlogTableHandlerRouter;
    }

    /**
     * ע�� canal binlog ���乤��
     * �������д���clientType=canalʱ
     * @return
     */
    @Bean(name="canalBinlogHandlerAdapter")
    @ConditionalOnProperty(prefix = "binlog-starter",name={"clientType"},havingValue = "canal")
    public CanalBinlogHandlerAdapter initCanalBinlogHandler(){
        return new CanalBinlogHandlerAdapter();
    }


    public AbstractBinlogHandler findHandlerFromContext(String beanName){
        return this.applicationContext.getBean(beanName,AbstractBinlogHandler.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
