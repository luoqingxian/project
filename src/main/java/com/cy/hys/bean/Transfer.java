package com.cy.hys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Data: 2023/3/2
 * @Time: 16:11
 * @ProjectName: project
 * @UserName: hys
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private String title;
    private String id;
    private String disabled;
    private List<Transfer> children;
}
