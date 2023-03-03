package com.cy.hys.dao.impl;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import com.cy.hys.dao.RoleDao;
import com.cy.hys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Data: 2023/2/28
 * @Time: 22:56
 * @ProjectName: project
 * @UserName: hys
 */
@Component
public class RoleDaoImpl implements RoleDao {
    @Autowired
    RoleMapper roleMapper;
    ObjData objData = new ObjData();

    @Override
    public ObjData getPower(long roleId) {
        List<Power> power = roleMapper.getPower(roleId);
        if (power.size() > 0) {
            return objData.setObj(200, power, "获取成功", 200);
        }
        return objData.setObj(201, power, "获取失败", 201);
    }

    @Override
    public List<String> getRoles() {
        List<String> roles = roleMapper.getRoles();
        return roles;
    }

    @Override
    public List<Power> getAlreadyPower(String roleName) {
        List<Power> alreadyPower = roleMapper.getAlreadyPower(roleName);

        return alreadyPower;
    }

    @Override
    public List<Power> getNoPower(String roleName) {
        List<Power> noPower = roleMapper.getNoPower(roleName);

        return noPower;
    }

    @Override
    public int ifsRole(long roleId, long powerId) {
        int i = roleMapper.ifsRole(roleId, powerId);
        return i;
    }

    @Override
    public int insFatherRole(long roleId, long powerId) {
        int i = roleMapper.insFatherRole(roleId, powerId);
        return i;
    }

    @Override
    public int insRole(long roleId, long powerId) {
        int i = roleMapper.insRole(roleId, powerId);
        return i;
    }

    @Override
    public int delRole(long roleId, long powerId) {
        int i = roleMapper.delRole(roleId, powerId);
        return i;
    }
}
