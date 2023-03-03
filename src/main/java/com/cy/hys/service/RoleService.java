package com.cy.hys.service;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import com.cy.hys.bean.Transfer;

import java.util.List;

public interface RoleService {
    // ��ȡȨ�޲˵�
    ObjData getPower(long roleId);

    // ��ȡ���н�ɫȨ��
    List<String> getRoles();

    // ����Ȩ��
    List<Transfer> getAlreadyPower(String roleName);

    // δ��Ȩ��
    List<Transfer> getNoPower(String roleName);

    // �ж�һ���˵��Ƿ����
    public int ifsRole(int roleId, int powerId);

    // ����Ȩ��
    int insRole(long roleId,List<Transfer> transferList);

    // �Ƴ�Ȩ��
    int delRole(long roleId, List<Transfer> transferList);

}
