package com.cy.hys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Manager {

  private long managerId;
  private String managerName;
  private long deptId;
  private long roleId;
  private String status;
  private String username;
  private String password;

}
