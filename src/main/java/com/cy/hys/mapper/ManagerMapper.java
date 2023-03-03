package com.cy.hys.mapper;

import com.cy.hys.bean.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ManagerMapper {
    // 用户登录
    List<Manager> loginManager(@Param("manager") Manager manager);
}
