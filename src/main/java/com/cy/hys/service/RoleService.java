package com.cy.hys.service;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import com.cy.hys.bean.Transfer;

import java.util.List;

public interface RoleService {
    // 获取权限菜单
    ObjData getPower(long roleId);

    // 获取所有角色权限
    List<String> getRoles();

    // 已有权限
    List<Transfer> getAlreadyPower(String roleName);

    // 未有权限
    List<Transfer> getNoPower(String roleName);

    // 判断一级菜单是否存在
    public int ifsRole(int roleId, int powerId);

    // 移入权限
    int insRole(long roleId,List<Transfer> transferList);

    // 移出权限
    int delRole(long roleId, List<Transfer> transferList);

}
