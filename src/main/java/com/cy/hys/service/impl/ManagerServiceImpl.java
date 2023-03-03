package com.cy.hys.service.impl;

import com.cy.hys.bean.Manager;
import com.cy.hys.bean.ObjData;
import com.cy.hys.dao.ManagerDao;
import com.cy.hys.dao.impl.ManagerDaoImpl;
import com.cy.hys.mapper.ManagerMapper;
import com.cy.hys.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Data: 2023/2/28
 * @Time: 20:09
 * @ProjectName: project
 * @UserName: hys
 */
@Component
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDao managerDao;

    @Override
    public ObjData loginManager( Manager manager) {
        System.out.println(managerDao);
        ObjData objData = managerDao.loginManager(manager);
        return objData;
    }
}
