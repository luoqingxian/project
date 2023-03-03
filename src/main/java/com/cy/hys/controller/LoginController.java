package com.cy.hys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cy.hys.bean.Manager;
import com.cy.hys.bean.ObjData;
import com.cy.hys.service.ManagerService;
import com.cy.hys.util.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Data: 2023/2/28
 * @Time: 19:13
 * @ProjectName: project
 * @UserName: hys
 */
@RestController
@RequestMapping(value = "/user", produces = "text/html;charset=utf-8")
public class LoginController {
    ObjData objData = new ObjData();
    @Autowired
    ManagerService managerService;

    @Autowired
    Manager manager;

    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        // 1. 生成图片
        String jpg = CodeUtils.create(200, 37, "jpg", response.getOutputStream());
        System.out.println(jpg);
        // 2. 存到session中
        session.setAttribute("code", jpg);
    }

    @RequestMapping("login")
    public String login(String code, Manager manager, HttpSession session) {
        String code1 = (String) session.getAttribute("code");
        if (!code1.equals(code)) {
            return JSONObject.toJSONString(objData.setObj(201, code, "验证码错误", 3));
        } else {
            objData = managerService.loginManager(manager);
            if (objData.getCode() == 200) {
                List<Manager> managers = JSONArray.parseArray(JSONObject.toJSONString(objData.getData()), Manager.class);
                session.setAttribute("user", managers.get(0));
                System.out.println(objData);
            }
            return JSONObject.toJSONString(objData);
        }
    }
}
