package com.cy.hys.dao;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RoleDao {
    // ��ȡȨ�޲˵�
    ObjData getPower(long roleId);

    // ��ȡ���н�ɫȨ��
    List<String> getRoles();

    // δ��Ȩ��
    List<Power> getAlreadyPower(String roleName);

    // ����Ȩ��
    List<Power> getNoPower(String roleName);

    // 1. �ж�һ���˵��Ƿ����
    int ifsRole(long roleId, long powerId);

    // 2. ��Ӹ����˵�
    int insFatherRole(long roleId, long powerId);

    // ����Ȩ��
    int insRole(long roleId, long powerId);

    // �Ƴ�Ȩ��
    int delRole(long roleId, long powerId);
}
