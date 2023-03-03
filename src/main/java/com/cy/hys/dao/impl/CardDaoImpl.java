package com.cy.hys.dao.impl;

import com.cy.hys.bean.Card;
import com.cy.hys.bean.ObjData;
import com.cy.hys.dao.CardDao;
import com.cy.hys.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Data: 2023/2/19
 * @Time: 19:48
 * @ProjectName: three
 * @UserName: hys
 */
@Component
public class CardDaoImpl implements CardDao {

    @Autowired
    private CardMapper cardMapper;
    private ObjData objData = new ObjData();


    @Override
    public ObjData selectCard() {
        List<Card> cardList = cardMapper.selectCard();
        if (cardList.size() > 0) {
            return objData.setObj(200, cardList, "获取成功", 200);
        }
        return objData.setObj(201, cardList, "获取成功", 201);
    }

    @Override
    public ObjData getCard(Card card) {
        List<Card> cardList = cardMapper.getCard(card);
        System.out.println("查出的数据");
        System.out.println(cardList);
        int page = cardMapper.getPage(card);
        System.out.println("总页数");
        if (cardList.size() > 0) {
            objData.setPage(page);
            return objData.setObj(0,cardList, "", page);
        }
        return objData.setObj(0,cardList, "", page);
    }

    //    private Connection conn;
//    private PreparedStatement ps;
//    private ResultSet rs;
//    private String sql;
//    private List<Card> tblCardList;
//    private ObjData objData;
//
//
//    public CardDaoImpl() {
//        conn = JDBCUtil.getConn();
//        tblCardList = new ArrayList<>();
//        objData = new ObjData();
//    }
//
//    @Override
//    public ObjData getTblCard(String cardNo) {
//        tblCardList.clear();
//        try {
//            sql = "select * from card where cardNo = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, cardNo);
//            rs = ps.executeQuery();
//            tblCardList = Utils.selectRsTwo(Card.class, rs);
//            if (tblCardList.size() > 0) {
//                return objData.setObj("200", "获取成功", tblCardList);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.closeRes(conn, ps, null);
//        }
//
//        return objData.setObj("201", "获取失败", tblCardList);
//    }
//
//    @Override
//    public ObjData topUpMoney(String carNo, Double money) {
//        tblCardList.clear();
//        int i = 0;
//        try {
//            sql = "update card set money=money+? where cardNo=?";
//            ps = conn.prepareStatement(sql);
//            ps.setDouble(1, money);
//            ps.setString(2, carNo);
//            i = ps.executeUpdate();
//            if (i > 0) {
//                return objData.setObj("200", "充值成功", i);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return objData.setObj("201", "充值失败", i);
//    }
//
    @Override
    public ObjData batchImportCard(List<String> stringList) {
//        int i = cardMapper.batchImportCard(stringList);
//        if (i > 0) {
//            return objData.setObj("201", "批量插入成功", i);
//        }
//        return objData.setObj("201", "批量插入失败", i);
        return null;
    }

    @Override
    public ObjData delCardById(String cardId) {
//        int i = cardMapper.delCardById(cardId);
//        if (i > 0) {
//            return objData.setObj("200", "删除成功", i);
//        }
//        return objData.setObj("201", "删除失败", 0);
        return null;
    }
}
