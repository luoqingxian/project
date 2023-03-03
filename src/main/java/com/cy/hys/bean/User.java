package com.cy.hys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Data: 2023/2/28
 * @Time: 11:44
 * @ProjectName: project
 * @UserName: hys
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    String username;
    String password;
}
