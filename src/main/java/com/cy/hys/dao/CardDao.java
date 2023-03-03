package com.cy.hys.dao;

import com.cy.hys.bean.Card;
import com.cy.hys.bean.ObjData;

import java.util.List;

public interface CardDao {
    //    // 读卡
//    public ObjData getTblCard(String cardNo);
//
//    // 充值
//    public ObjData topUpMoney(String carNo, Double money);
//

//
    // 获取所有卡信息
    ObjData selectCard();


    // 获取card信息
    ObjData getCard(Card card);

    // 卡批量导入
    ObjData batchImportCard(List<String> stringList);

    // 删除
    ObjData delCardById(String cardId);


}
