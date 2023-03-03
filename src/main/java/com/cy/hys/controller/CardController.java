package com.cy.hys.controller;

import com.alibaba.fastjson.JSONObject;
import com.cy.hys.bean.ObjData;
import com.cy.hys.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Data: 2023/3/1
 * @Time: 15:11
 * @ProjectName: project
 * @UserName: hys
 */
@RestController
@RequestMapping(value = "/cards",produces = "text/html;charset=utf-8")
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping("/card")
    public String card(HttpServletRequest request) {
        Map<String,Object> map = request.getParameterMap();
        ObjData objData = cardService.getCard(map);
//        cardService.batchImportCard()
        System.out.println(objData);
        return JSONObject.toJSONString(objData);
    }

    @RequestMapping("/selectCard")
    public String selectCard(HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        System.out.println(request.getParameter("page"));
        System.out.println(request.getParameter("limit"));
        System.out.println(request.getParameter("startText"));
        System.out.println(request.getParameter("endText"));
        System.out.println(request.getParameter("startTime"));
        System.out.println(request.getParameter("endTime"));


        return null;
    }


}
