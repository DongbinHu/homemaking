package com.company.homemaking.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.company.homemaking.common.pojo.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Slf4j
public class OSSClientUtil {


    public static final Properties p = new Properties();

    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String bucketName;
    private static String filedir;
    private static String fileKey;
    private static String outerUrl;
    private static OSSClient ossClient;


    private OSSClientUtil() {
    }

    static {

    }

    //读取配置文件
    static {

        try {
            p.load(OSSClientUtil.class.getClassLoader().getResourceAsStream("oss_client.properties"));

            endpoint = p.getProperty("oss.endPoint");
            accessKeyId = p.getProperty("oss.accessKeyId");
            accessKeySecret = p.getProperty("oss.accessKeySecret");
            bucketName = p.getProperty("oss.bucketName");
            outerUrl = p.getProperty("oss.outerurl");
            //初始化
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        } catch (IOException e) {
            log.error("init fail", e);
        }

//        logger.info("aliyunOSS:{" + "\nendpoint:" + endpoint + "\naccessKeyId:" + accessKeyId + "\naccessKeySecret:" + accessKeySecret + "\nbucketName:" + bucketName + "\nossClient" + ossClient + "}");

    }


    /**
     * 上传文件
     *
     * @param file     图片
     * @param category 图片类别
     * @return url
     */
    public static JSONResult uploadImg2Oss(MultipartFile file, String category) {

        String ret = "";

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        String dateNowStr = sdf.format(d);

        filedir = category + dateNowStr;

        if (file.getSize() > 1024 * 1024 * 10) {
            return JSONResult.errorException("上传图片大小不能超过10M！");
        }
        String imgname = getImgname(file);
        try {
            InputStream instream = file.getInputStream();

            try {
                //创建上传Object的Metadata
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(instream.available());
                objectMetadata.setCacheControl("no-cache");
                objectMetadata.setHeader("Pragma", "no-cache");

                String imgcContentType = getcontentType2(imgname.substring(imgname.lastIndexOf(".")));
                if (StringUtils.isBlank(imgcContentType)) {
                    return JSONResult.errorException("图片上传失败");
                }
                objectMetadata.setContentType(imgcContentType);
                objectMetadata.setContentDisposition("inline;filename=" + imgname);

                fileKey = filedir + imgname;
                //上传文件
                PutObjectResult putResult = ossClient.putObject(bucketName, fileKey, instream, objectMetadata);
                ret = putResult.getETag();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return JSONResult.errorException("图片上传失败！");
            } finally {
                try {
                    if (instream != null) {
                        instream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        log.info("图片的唯一数字签名MD5值:" + ret);
        return JSONResult.ok(outerUrl + fileKey);

    }


    //生成图片名
    private static String getImgname(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        return name;
    }


    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }


    public static String getcontentType2(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        return "";
    }

}
