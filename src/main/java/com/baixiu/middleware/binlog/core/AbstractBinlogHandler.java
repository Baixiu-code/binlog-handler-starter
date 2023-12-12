package com.baixiu.middleware.binlog.core;

import com.baixiu.middleware.binlog.model.BinlogTableRowDiffModel;
import java.util.List;
import java.util.Map;

/**
 * @author baixiu
 * @date ����ʱ�� 2023/12/12 11:31 AM
 */
public interface AbstractBinlogHandler {

    /**
     * ��Ҫ���ĵ��ֶΡ�ʵ�ֺ󽫽�ʵ�ֵ��ֶ�ֵ������ fieldValues ��
     * @return ����ֶ�
     */
    String[] getFields();

    /**
     * ��Ҫ���ĵı���ֶΡ�ʵ�ֺ󽫽�ʵ�ֵ��ֶ�ֵ������ changeList ��
     * @return �����ֶ�
     */
    String[] getUpdateFields();

    /**
     * ����ʱ����
     * @param fieldValues Ψһ�ֶΣ�����ȷ��һ������
     * @param changeList �ֶε�ֵ�����仯��
     * @throws Exception ҵ��exception
     */
    void insert(Map<String, String> fieldValues, List<BinlogTableRowDiffModel> changeList) throws Exception;

    /**
     * �����޸�ʱ����
     * @param fieldValues ʵ����getFields�ӿ���õ����ֶ�����ֶ��Լ��ֶε�ֵ
     * @param changeList  �ֶε�ֵ�����仯��
     * @throws Exception ҵ��exception
     */
    void update(Map<String, String> fieldValues, List<BinlogTableRowDiffModel> changeList) throws Exception;

    /**
     * ɾ��ʱ����
     * @param fieldValues Ψһ�ֶΣ�����ȷ��һ������
     * @throws Exception ҵ��exception
     */
    void delete(Map<String, String> fieldValues) throws Exception;

}
