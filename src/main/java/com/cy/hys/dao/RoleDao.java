package com.cy.hys.dao;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RoleDao {
    // 获取权限菜单
    ObjData getPower(long roleId);

    // 获取所有角色权限
    List<String> getRoles();

    // 未有权限
    List<Power> getAlreadyPower(String roleName);

    // 已有权限
    List<Power> getNoPower(String roleName);

    // 1. 判断一级菜单是否存在
    int ifsRole(long roleId, long powerId);

    // 2. 添加父级菜单
    int insFatherRole(long roleId, long powerId);

    // 移入权限
    int insRole(long roleId, long powerId);

    // 移出权限
    int delRole(long roleId, long powerId);
}
