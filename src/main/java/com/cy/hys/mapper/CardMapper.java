package com.cy.hys.mapper;


import com.cy.hys.bean.Card;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardMapper {
    // 无条件获取数据
    List<Card> selectCard();

    // 条件获取数据
    List<Card> getCard(@Param("card") Card card);

    // 获取页码
    int getPage(@Param("card") Card card);

    // 批量导入
    int batchImportCard(@Param("list") List<String> stringList);

//    // 删除
//    int delCardById(@Param("cardId") String cardId);
}
