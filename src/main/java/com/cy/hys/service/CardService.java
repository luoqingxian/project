package com.cy.hys.service;

import com.cy.hys.bean.ObjData;

import java.util.Map;

public interface CardService {

    //    // 读卡
//    public ObjData getTblCard(String cardNo);
//
//    // 充值
//    public ObjData topUpMoney(String carNo, Double money);
//
    // 无条件获取数据
    ObjData selectCard(Map<String, Object> map);

    // 获取card信息
    ObjData getCard(Map<String, Object> map);

    // 卡批量导入
    ObjData batchImportCard(Map<String, Object> map);

    // 删除
    ObjData delCardById(Map<String, Object> map);
}
