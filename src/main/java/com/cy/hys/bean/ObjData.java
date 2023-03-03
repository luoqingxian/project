package com.cy.hys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Data: 2023/2/28
 * @Time: 19:52
 * @ProjectName: project
 * @UserName: hys
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjData {
    private int code=0;     // 状态码
    private int count;    // 页码
    private Object data; // 数据
    private String msg="";   // 信息


    private int page;


    public ObjData setObj(int code, Object data, String msg, int count) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.count = count;
        return this;
    }
}
