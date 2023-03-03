package com.cy.hys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cy.hys.bean.Manager;
import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import com.cy.hys.bean.Transfer;
import com.cy.hys.service.RoleService;
import com.cy.hys.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Data: 2023/2/28
 * @Time: 23:03
 * @ProjectName: project
 * @UserName: hys
 */
@RestController
@RequestMapping(value = "/roles", produces = "text/html;charset=utf-8")
public class RoleController {
    @Autowired
    RoleService roleService;
    ObjData objData = new ObjData();

    @RequestMapping("/role")
    public String role(HttpSession session) {
        System.out.println(roleService);

        Manager user = (Manager) session.getAttribute("user");
        System.out.println(user);

        ObjData power = roleService.getPower(user.getRoleId());
        return JSONObject.toJSONString(power);
    }

    @RequestMapping("/getRoles")
    public String getRoles(){
        List<String> roles = roleService.getRoles();
        return JSONObject.toJSONString(roles);
    }

    @RequestMapping("/getRoleNA")
    public String getRoleNA(String roleName) {
        System.out.println(roleName);
        List<Transfer> alreadyPower = roleService.getAlreadyPower(roleName);
        List<Transfer> noPower = roleService.getNoPower(roleName);
        List<List<Transfer>> list = new ArrayList<>();
        list.add(alreadyPower);
        list.add(noPower);

        return JSONObject.toJSONString(objData.setObj(200, list, "", 200));
    }

    @RequestMapping("/upInsRole")
    public String upInsRole(String json, HttpSession session) {
        Manager user = (Manager) session.getAttribute("user");
        System.out.println(user);
        List<Transfer> transferList = JSONArray.parseArray(json, Transfer.class);
        System.out.println(transferList);
        roleService.insRole(user.getRoleId(),transferList);
        return JSONObject.toJSONString(json);
    }

    @RequestMapping("/upDelRole")
    public String upDelRole(String json, HttpSession session) {
        Manager user = (Manager) session.getAttribute("user");

        List<Transfer> transferList = JSONArray.parseArray(json, Transfer.class);

        roleService.delRole(user.getRoleId(), transferList);

        return JSONObject.toJSONString(json);
    }
}
