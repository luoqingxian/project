package com.cy.hys.service.impl;

import com.cy.hys.bean.ObjData;
import com.cy.hys.bean.Power;
import com.cy.hys.bean.Transfer;
import com.cy.hys.dao.RoleDao;
import com.cy.hys.dao.impl.RoleDaoImpl;
import com.cy.hys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Data: 2023/2/28
 * @Time: 22:59
 * @ProjectName: project
 * @UserName: hys
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public ObjData getPower(long roleId) {
        ObjData power = roleDao.getPower(roleId);
        return power;
    }

    @Override
    public List<String> getRoles() {
        List<String> roles = roleDao.getRoles();
        return roles;
    }

    @Override
    public List<Transfer> getAlreadyPower(String roleName) {
        List<Power> alreadyPower = roleDao.getAlreadyPower(roleName);
        List<Transfer> transferList = dispose(alreadyPower);

        return transferList;
    }

    @Override
    public List<Transfer> getNoPower(String roleName) {
        List<Power> noPower = roleDao.getNoPower(roleName);
        List<Transfer> transferList = dispose(noPower);

        return transferList;
    }


    public List<Transfer> dispose(List<Power> powerList){
        Stream<Power> stream1 = powerList.stream();
        Stream<Power> stream2 = powerList.stream();

        List<Power> collect1 = stream1.filter(x -> x.getParentId() == 0).collect(Collectors.toList());
        List<Power> collect2 = stream2.filter(x -> x.getParentId() > 0).collect(Collectors.toList());

        // 将一级菜单加进去
        List<Transfer> transferList = new ArrayList<>();
        for (Power power : collect1) {
            Transfer transfer = new Transfer();
            transfer.setId(String.valueOf(power.getPowerId()));
            transfer.setTitle(power.getPowerName());
            transfer.setChildren(new ArrayList<>());
            for (Power power1 : collect2) {
                if (power1.getParentId() == power.getPowerId()) {
                    List<Transfer> checked = transfer.getChildren();
                    Transfer transfer1 = new Transfer();
                    transfer1.setId(String.valueOf(power1.getPowerId()));
                    transfer1.setTitle(power1.getPowerName());
                    checked.add(transfer1);
                }
            }
            if (transfer.getChildren().size() > 0) {
                transferList.add(transfer);
            }
        }
        return transferList;
    }

    @Override
    public int ifsRole(int roleId, int powerId) {
        int i = roleDao.ifsRole(roleId, powerId);
        return i;
    }

    @Override
    public int insRole(long roleId,List<Transfer> transferList) {
        for (Transfer t : transferList) {
            int i = roleDao.ifsRole(roleId,Integer.parseInt(t.getId()));
            if (i == 0) {
                int i1 = roleDao.insFatherRole(roleId, Integer.parseInt(t.getId()));
                System.out.println("父级菜单添加成功:" + i1);
            }
            List<Transfer> children = t.getChildren();
            for (Transfer tChildren : children) {
                int i1 = roleDao.insRole(roleId, Integer.parseInt(tChildren.getId()));
                System.out.println("二级菜单添加成功:" + i1);
            }
        }

        return 0;
    }

    @Override
    public int delRole(long roleId, List<Transfer> transferList) {
        int i = 0;
        for (Transfer transfer : transferList) {
            for (Transfer child : transfer.getChildren()) {
                i = roleDao.delRole(roleId, Long.parseLong(child.getId()));
                System.out.println("移出权限成功:" + i);
            }

        }
        return i;
    }
}
