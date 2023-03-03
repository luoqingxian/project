package com.cy.hys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private long cardId;
    private long checkPersonId;
    private long applyPersonId;
    private String cardNo;
    private String name;
    private String sex;
    private long age;
    private String city;
    private String idNo;
    private String phone;
    private String addr;
    private double money;
    private double cash;
    private Timestamp applyTime;
    private Timestamp checkTime;
    private String status;


    // 分页
    private String startText;
    private String endText;
    private Timestamp startTime;
    private Timestamp endTime;
    private int ins;

//    // 批量导入
//    private int startCard;
//    private int endCard;
//    private String identifyCard;

}
