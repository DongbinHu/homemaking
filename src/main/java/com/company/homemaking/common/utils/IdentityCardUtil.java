package com.company.homemaking.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 */
public class IdentityCardUtil {
    private static Pattern pattern = Pattern.compile("^[1-9]\\d{5}((((19|[2-9][0-9])\\d{2})(0?[13578]|1[02])(0?[1-9]|[12][0-9]|3[01]))|(((19|[2-9][0-9])\\d{2})(0?[13456789]|1[012])(0?[1-9]|[12][0-9]|30))|(((19|[2-9][0-9])\\d{2})0?2(0?[1-9]|1[0-9]|2[0-8]))|(((1[6-9]|[2-9][0-9])(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))0?229))\\d{3}[0-9Xx]$");

    /**
     * 通过 IDcard  解析出用户的基本信息
     *
     * @param IDcard 18位身份证号
     * @return map  flag==true  map - key: birthday age sex
     * flag==false map - key: info
     */
    public static Map<String, Object> extIdentityInfo(String IDcard) {

        Map<String, Object> map = new HashMap<>();
        boolean flag = true;

        if (IDcard.equals("") || IDcard == null) {
            flag = false;
            map.put("flag", false);
            map.put("info", "身份证号不能为空");
        }
        Matcher matcher = pattern.matcher(IDcard);
        if (!matcher.matches()) {
            flag = false;
            map.put("flag", false);
            map.put("info", "身份证格式不合法");
        }

        if (flag) {
            map.put("flag", true);
            String birthdayStr = IDcard.substring(6, 14);
            LocalDate birthday = LocalDate.from(DateTimeFormatter.ofPattern("yyyyMMdd").parse(birthdayStr));
            long age = ChronoUnit.YEARS.between(birthday, LocalDate.now());
            Date date = Date.valueOf(birthday);
            map.put("birthday", date);
            map.put("age", age);
            if (Integer.parseInt(IDcard.substring(16).substring(0, 1)) % 2 == 0) {
                map.put("sex", "女");
            } else {
                map.put("sex", "男");
            }

        }
        return map;
    }

    /**
     * 校验身份证是否是合理的18位
     *
     * @param IDcard
     * @return
     */
    public static boolean valIdentityCard(String IDcard) {

        if (StringUtils.isNotBlank(IDcard)) {
            return pattern.matcher(IDcard).matches();
        }

        return false;
    }

}



