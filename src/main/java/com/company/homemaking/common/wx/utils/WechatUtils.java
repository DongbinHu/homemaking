package com.company.homemaking.common.wx.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.homemaking.consumer.service.JedisClient;
import com.company.homemaking.consumer.vo.user.UserForRedis;
import com.company.homemaking.common.pojo.JSONResult;
import com.company.homemaking.common.utils.FastJsonConvert;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.*;

/**
 * @author 胡东斌
 * @create 2020-04-24
 */
@Slf4j
@Component
public class WechatUtils {


    //token超时时间
    private static Long tokenExpireTime = 0L;
    private static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String createTicketPath = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
    private static String showQrcodePath = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
    //小程序
    private static String appId;
    public static String getAppId(){
        return appId;
    }
    public static void setAppId(String appId){
        WechatUtils.appId = appId;
    }
    private static String appSecret;
    public static String getAppSecret(){
        return appSecret;
    }
    public static void setAppSecret(String appSecret){
        WechatUtils.appSecret = appSecret;
    }
    private static String templateId;
    public static String getTemplateId(){
        return templateId;
    }
    public static void setTemplateId(String templateId){
        WechatUtils.templateId = templateId;
    }

    //公众号
    private static String publicAppId;
    public static String getPublicAppId(){
        return publicAppId;
    }
    public static void setPublicAppId(String publicAppId){
        WechatUtils.publicAppId = publicAppId;
    }
    private static String publicAppSecret;
    public static String getPublicAppSecret(){
        return publicAppSecret;
    }
    public static void setPublicAppSecret(String publicAppSecret){
        WechatUtils.publicAppSecret = publicAppSecret;
    }

    //Redis
    private static String userSession;
    public static String getUserSession(){
        return userSession;
    }
    public static void setUserSession(String userSession){
        WechatUtils.userSession = userSession;
    }

    @Autowired
    private JedisClient JedisClientservie;

    private static JedisClient jedisClient;

    @PostConstruct
    public void init() {
        jedisClient = JedisClientservie;
    }
//    public static JSONResult templateSend(String accessToken, WechatTemplateVO template) {
//        String apiUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="//
//                + (accessToken);
//        String body = HttpRequest.post(apiUrl).body(JSON.toJSONString(template)).execute().body();
//        JSONObject jsonObject = JSON.parseObject(body);
//        if (jsonObject.containsKey("errcode") && jsonObject.getIntValue("errcode") > 0) {
//            return JSONResult.errorMsg("#WechatApi[{}] call error: {}" + body);
//        }
//        return JSONResult.ok();
//    }

    /**
     * 小程序登录
     */

    //获取openid
    public static Map<String, Object> getWxUserOpenid(String code) {
        //拼接url
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=");//appid设置
        url.append(appId);
        url.append("&secret=");//secret设置
        url.append(appSecret);
        url.append("&js_code=");//code设置
        url.append(code);
        url.append("&grant_type=authorization_code");
        Map<String, Object> map = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            net.sf.json.JSONObject res = net.sf.json.JSONObject.fromObject(content);//把信息封装为json
            //把信息封装到map
            map = parseJSON2Map(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //json转map
    public static Map<String, Object> parseJSON2Map(net.sf.json.JSONObject json) {
        Map<String, Object> map = new HashMap<>();
        // 最外层解析
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // 如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<>();
                @SuppressWarnings("unchecked")
                Iterator<net.sf.json.JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    net.sf.json.JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * 获取公众号openid
     */

    //获取openid
    public static Map<String, Object> getPublicOpenid(String code) {
        //拼接url
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=");//appid设置
        url.append(publicAppId);
        url.append("&secret=");//secret设置
        url.append(publicAppSecret);
        url.append("&js_code=");//code设置
        url.append(code);
        url.append("&grant_type=authorization_code");
        Map<String, Object> map = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            net.sf.json.JSONObject res = net.sf.json.JSONObject.fromObject(content);//把信息封装为json
            //把信息封装到map
            map = parseJSON2Map(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /* 发送 post请求 用HTTPclient 发送请求*/
    public static byte[] post(String URL, String json) {
        InputStream inputStream = null;
        byte[] data = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(URL);
        httppost.addHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Accept", "application/json");
        try {
            StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            httppost.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                // 获取相应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    inputStream = entity.getContent();
                    data = readInputStream(inputStream);
                }
                return data;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**  将流 保存为数据数组
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static void ByteToFile(byte[] bytes)throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 = ImageIO.read(bais);
        try {
            File w2 = new File("/Users/liubangzi/company/test.png");//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            bais.close();
        }
    }


    public static com.alibaba.fastjson.JSONObject getUrlInfo(String requestURL, String method, String json) throws Exception {
        URL get_url = new URL(requestURL);
        // 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
        // (标识一个url所引用的远程对象连接)
        // 此时cnnection只是为一个连接对象,待连接中
        HttpURLConnection httpURLConnection = (HttpURLConnection) get_url
                .openConnection();
        // 设置请求方式为post
        httpURLConnection.setRequestMethod(method);
        // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
        httpURLConnection.setDoOutput(true);
        // 设置连接输入流为true
        httpURLConnection.setDoInput(true);
        // post请求缓存设为false
        httpURLConnection.setUseCaches(false);
        // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
        // application/x-javascript text/xml->xml数据
        // application/x-javascript->json对象
        // application/x-www-form-urlencoded->表单数据
        // ;charset=utf-8 必须要，不然妙兜那边会出现乱码
        httpURLConnection.setRequestProperty("Content-type",
                "application/json;charset=utf-8");
        // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
        httpURLConnection.connect();

        // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
        OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
        if (method.equals("POST")) {
            out.append(json);
        }
        out.flush();
        out.close();

        // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpURLConnection.getInputStream(), "UTF-8"));
        // 读取数据操作
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = reader.readLine()) != null) {
            buffer.append(str);
        }
        //转换成json
        com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSONObject.parseObject(buffer.toString());
        reader.close();
        return jsonObj;

    }

    //获取小程序tocken
    public static JSONObject getAccessToken() throws Exception {
        String access_token = null;
        Long access_token_expire = 0L;
        Boolean conn_flag = true;
        JSONObject tokenInfo = new JSONObject();
        try {
            access_token = jedisClient.get("access_token_dg");
            access_token_expire = jedisClient.ttl("access_token_dg");
            if (access_token != null && access_token.length() > 0 && access_token_expire > tokenExpireTime) {
                tokenInfo.put("access_token", access_token);
                tokenInfo.put("access_token_expire", access_token_expire);
                return tokenInfo;
            }
        } catch (Exception e) {
            log.info("redis连接失败");
            conn_flag = false;
        }
        String url = accessTokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject access_token_json = WechatUtils.getUrlInfo(url, "GET", null);
        if (access_token_json != null) {
            access_token = access_token_json.getString("access_token");
            Long tokenExpireTime = access_token_json.getLongValue("expires_in");
            Long getTokenTime = System.currentTimeMillis();
            log.info("accessToken====>" + access_token);
            log.info("tokenExpireTime====>" + tokenExpireTime + "s");
            log.info("getTokenTime====>" + getTokenTime + "ms");
            if (conn_flag == true) {
                jedisClient.set("access_token_dg", access_token);
                jedisClient.expire("access_token_dg", 7000);
            }
        } else {
            log.info("====>从微信接口获取accessToken失败");
        }
        tokenInfo.put("access_token", access_token);
        tokenInfo.put("access_token_expire", 7000L);
        return tokenInfo;
    }



    /**
     * 获取公众号Oauth2tocken
     */

    public static Map<String, Object> getOauth2AccessToken(String code) {
        //拼接url
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?");
        url.append("appid=");//appid设置
        url.append(publicAppId);
        url.append("&secret=");//secret设置
        url.append(publicAppSecret);
        url.append("&code=");//code设置
        url.append(code);
        url.append("&grant_type=authorization_code");
        Map<String, Object> map = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            net.sf.json.JSONObject res = net.sf.json.JSONObject.fromObject(content);//把信息封装为json
            //把信息封装到map
            map = parseJSON2Map(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    /**
     * 获取公众号unionId
     */

    public static Map<String, Object> getPublicUnionId(String accessToken,String openid) {
        //拼接url
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?");
        url.append("access_token=");//appid设置
        url.append(accessToken);
        url.append("&openid=");//secret设置
        url.append(openid);
        url.append("&lang=zh_CN");//code设置
        Map<String, Object> map = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            net.sf.json.JSONObject res = net.sf.json.JSONObject.fromObject(content);//把信息封装为json
            //把信息封装到map
            map = parseJSON2Map(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    //获取公众号tocken
    public static JSONObject getPublicAccessToken() throws Exception {
        String access_token = null;
        Long access_token_expire = 0L;
        Boolean conn_flag = true;
        JSONObject tokenInfo = new JSONObject();
        try {
            access_token = jedisClient.get("access_token_pb");
            access_token_expire = jedisClient.ttl("access_token_pb");
            if (access_token != null && access_token.length() > 0 && access_token_expire > tokenExpireTime) {
                tokenInfo.put("access_token", access_token);
                tokenInfo.put("access_token_expire", access_token_expire);
                return tokenInfo;
            }
        } catch (Exception e) {
            log.info("redis连接失败");
            conn_flag = false;
        }
        String url = accessTokenUrl.replace("APPID", publicAppId).replace("APPSECRET", publicAppSecret);
        JSONObject access_token_json = WechatUtils.getUrlInfo(url, "GET", null);
        if (access_token_json != null) {
            access_token = access_token_json.getString("access_token");
            Long tokenExpireTime = access_token_json.getLongValue("expires_in");
            Long getTokenTime = System.currentTimeMillis();
            log.info("accessToken====>" + access_token);
            log.info("tokenExpireTime====>" + tokenExpireTime + "s");
            log.info("getTokenTime====>" + getTokenTime + "ms");
            if (conn_flag == true) {
                jedisClient.set("access_token_pb", access_token);
                jedisClient.expire("access_token_pb", 7000);
            }
        } else {
            log.info("====>从微信接口获取accessToken失败");
        }
        tokenInfo.put("access_token", access_token);
        tokenInfo.put("access_token_expire", 7000L);
        return tokenInfo;
    }

    /**
     * 获取永久二维码(数字) ticket
     * @param sceneId 场景Id
     * @return
     */
    public static String createForeverTicket(int sceneId) throws Exception {
        String ticket = null;
        String url = null;
        String accessToken = getPublicAccessToken().getString("access_token");
        TreeMap<String,String> params = new TreeMap<String,String>();
//        params.put("access_token", accessToken);
        //output data
        Map<String,Integer> intMap = new HashMap<String,Integer>();
        intMap.put("scene_id",sceneId);
        Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();
        mapMap.put("scene", intMap);
        //
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("action_name", "QR_LIMIT_SCENE");
        paramsMap.put("action_info", mapMap);
        String data = JSON.toJSONString(paramsMap);
        JSONObject result = getUrlInfo(createTicketPath.replace("TOKEN", accessToken),"POST",data);
        System.out.println("4444444444444444");
        System.out.println(result);
        if (result != null) {
            ticket = result.getString("ticket");
            url = result.getString("url");
        } else {
            log.info("====>从微信接口获取ticket失败");
        }
        return ticket;
    }

    /**
     * 获取二维码ticket后，通过ticket换取二维码图片展示
     * @param ticket
     * @return
     */
    public static String showQrcode(String ticket) throws IOException{
        Map<String,String> params = new TreeMap<String,String>();
        params.put("ticket", URLEncoder.encode(ticket, "utf-8"));
        String codeUrl = null;
        try {
            codeUrl = setParmas(params,showQrcodePath,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeUrl;
    }

    /**
     * 设置参数
     *
     * @param map
     *            参数map
     * @param path
     *            需要赋值的path
     * @param charset
     *            编码格式 默认编码为utf-8(取消默认)
     * @return 已经赋值好的url 只需要访问即可
     */
    public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {
        String result = "";
        boolean hasParams = false;
        if (path != null && !"".equals(path)) {
            if (map != null && map.size() > 0) {
                StringBuilder builder = new StringBuilder();
                Set<Map.Entry<String, String>> params = map.entrySet();
                for (Map.Entry<String, String> entry : params) {
                    String key = entry.getKey().trim();
                    String value = entry.getValue().trim();
                    if (hasParams) {
                        builder.append("&");
                    } else {
                        hasParams = true;
                    }
                    if(charset != null && !"".equals(charset)){
                        //builder.append(key).append("=").append(URLDecoder.(value, charset));
                        builder.append(key).append("=").append(URLEncoder.encode(value, charset));
                    }else{
                        builder.append(key).append("=").append(value);
                    }
                }
                result = builder.toString();
            }
        }
        return doUrlPath(path, result).toString();
    }

    /**
     * 设置连接参数
     *
     * @param path
     *            路径
     * @return
     */
    private static URL doUrlPath(String path, String query) throws Exception {
        URL url = new URL(path);
        if (org.apache.commons.lang.StringUtils.isEmpty(path)) {
            return url;
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(url.getQuery())) {
            if (path.endsWith("?")) {
                path += query;
            } else {
                path = path + "?" + query;
            }
        } else {
            if (path.endsWith("&")) {
                path += query;
            } else {
                path = path + "&" + query;
            }
        }
        return new URL(path);
    }

    /**
     *
     * 解密微信手机号
     *
     */

    public static String getWechatPhoneNumber(String encrypdata, String ivdata, String token){
        if(encrypdata.contains(" ")){
            encrypdata = encrypdata.replace(" ","+");
        }
        if(ivdata.contains(" ")){
            ivdata = ivdata.replace(" ","+");
        }
        byte[] dataByte = org.apache.commons.codec.binary.Base64.decodeBase64(encrypdata);
        String userRedisStr = jedisClient.get(userSession + token);
        UserForRedis userRedis =  FastJsonConvert.convertJSONToObject(userRedisStr, UserForRedis.class);
        // 加密秘钥
        byte[] keyByte = org.apache.commons.codec.binary.Base64.decodeBase64(userRedis.getWechatSessionKey());
        // 偏移量
        byte[] ivByte = org.apache.commons.codec.binary.Base64.decodeBase64(ivdata);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject result1 = JSONObject.parseObject(result);
                return result1.getString("phoneNumber");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
