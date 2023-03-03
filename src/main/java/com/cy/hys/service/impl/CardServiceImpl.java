package com.cy.hys.service.impl;

import com.cy.hys.bean.Card;
import com.cy.hys.bean.ObjData;
import com.cy.hys.dao.CardDao;
import com.cy.hys.dao.impl.CardDaoImpl;
import com.cy.hys.service.CardService;
import com.cy.hys.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Data: 2023/2/19
 * @Time: 19:56
 * @ProjectName: three
 * @UserName: hys
 */
@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardDao cardDao;
    private ObjData objData = new ObjData();

    @Override
    public ObjData selectCard(Map<String, Object> map) {

        return null;
    }

    @Override
    public ObjData getCard(Map<String, Object> map) {
        Card card = new Card();
        card.setStartText(((String[]) map.get("startText"))[0]);                   // 开始卡号
        card.setEndText(((String[]) map.get("endText"))[0]);
        // 结束卡号
        String startTime = ((String[]) map.get("startTime"))[0];
        if (!startTime.equals("")) {
            card.setStartTime(Utils.setTime(((String[]) map.get("startTime"))[0]));    // 开始时间
        }
        String endTime = ((String[]) map.get("endTime"))[0];
        if (!endTime.equals("")) {
            card.setEndTime(Utils.setTime(((String[]) map.get("endTime"))[0]));        // 结束时间

        }
        String status = ((String[]) map.get("status"))[0];
        if (!status.equals("")) {
            card.setStatus(((String[]) map.get("status"))[0]);                         // 状态
        }
        System.out.println(card);
        int ins = (Integer.parseInt(((String[]) map.get("ins"))[0]));
        int page = ins * 5 + 5;
        card.setIns(ins * 5);                                           // 页码1
//        card.setPage(page);                                         // 总页

        objData = cardDao.getCard(card);

        return objData;
    }

    @Override
    public ObjData batchImportCard(Map<String, Object> map) {
        int start = Integer.parseInt(String.valueOf(map.get("start")));
        int end = Integer.parseInt(String.valueOf(map.get("end")));
        String sign = (String) map.get("sign");

        List<String> list = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            list.add(sign + i);
        }

        objData = cardDao.batchImportCard(list);

        return objData;
    }

    @Override
    public ObjData delCardById(Map<String, Object> map) {
        Object cardId = map.get("cardId");
        objData = cardDao.delCardById(String.valueOf(cardId));

        return objData;
    }
}
