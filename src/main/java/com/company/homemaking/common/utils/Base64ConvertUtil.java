package com.company.homemaking.common.utils;


import com.company.homemaking.common.serial.BASE64DecodedMultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Slf4j
public class Base64ConvertUtil {

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStr = base64.split(",");

            byte[] b = new byte[0];
            b = Base64.getDecoder().decode(baseStr[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b, baseStr[0]);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
