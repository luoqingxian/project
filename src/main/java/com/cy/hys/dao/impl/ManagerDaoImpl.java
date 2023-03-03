package com.cy.hys.dao.impl;

import com.cy.hys.bean.Manager;
import com.cy.hys.bean.ObjData;
import com.cy.hys.dao.ManagerDao;
import com.cy.hys.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Data: 2023/2/28
 * @Time: 20:10
 * @ProjectName: project
 * @UserName: hys
 */

@Component
public class ManagerDaoImpl implements ManagerDao {
    ObjData objData = new ObjData();
    @Autowired
    ManagerMapper managerMapper;


    @Override
    public ObjData loginManager(Manager manager) {
        List<Manager> managers = managerMapper.loginManager(manager);
        if (managers.size() > 0) {
            return objData.setObj(200, managers, "登录成功", 1);
        }
        return objData.setObj(201, managers, "用户名密码错误", 2);
    }
}
