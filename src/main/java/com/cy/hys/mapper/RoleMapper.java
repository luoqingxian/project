package com.cy.hys.mapper;

import com.cy.hys.bean.Power;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    // ��ȡȨ�޲˵�
    List<Power> getPower(@Param("roleId") long roleId);

    // ��ȡ���н�ɫȨ��
    List<String> getRoles();

    // δ��Ȩ��
    List<Power> getAlreadyPower(@Param("roleName")String roleName);

    // ����Ȩ��
    List<Power> getNoPower(@Param("roleName")String roleName);

    // 1. �ж�һ���˵��Ƿ����
    int ifsRole(@Param("roleId") long roleId,@Param("powerId") long powerId);

    // 2. û�������
    int insFatherRole(@Param("roleId") long roleId,@Param("powerId") long powerId);

    // ����Ȩ��
    int insRole(@Param("roleId")long roleId,@Param("powerId") long powerId);

    // �Ƴ�Ȩ��
    int delRole(@Param("roleId")long roleId,@Param("powerId") long powerId);

}
