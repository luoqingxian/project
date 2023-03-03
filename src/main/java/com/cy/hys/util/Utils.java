package com.cy.hys.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Data: 2023/2/22
 * @Time: 19:31
 * @ProjectName: three
 * @UserName: hys
 */
public class Utils {
    public static Timestamp setTime(String time) {
        Date parse = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            parse = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(parse.getTime());
    }
}
