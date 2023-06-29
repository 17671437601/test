package MkeyExample;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;


public class MkeyTest {
    private static Logger logger = Logger.getLogger(MkeyTest.class);

    //云平台测试
    private static String SIGN_ALGORITHMS = "SHA256WithRSA";
    private static String privatekey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHwpN7M6Xhja7sfaB0K3y2MBd4xLfhOF64mv0mtEZfqaWvphadXAimnfru8Y4sf0wDb9LYjacXRTkRMhZaZq5ZRCNFB+G8HnNb1y9Vr+7VqvT3wlRV0n+Jar+rEFguW293kH6wOLPQVNkmL4xEy/ecI81gfqR0wSHbyVWaBJ4o7w8qYVFc3is0vswl/bpKE2/4ngjEGDNuYdS0oXZR1TOjEEtNPEws9944yHyfYnbJgbTXkO78hlRUUu4tA8UnSjLrO0m0I6aQkuibPHKjMj247q2629L5IQpn+mGpgxmqUQmzSlKf5wg3uE0Ss1Bea8/AneSDBx2tzjsEs2qKn8cfAgMBAAECggEAM18GbaVCjNIPMf/rmmnmPA6AoztVFU0+Un6bcmzfAE3ymz+u6QatY1b+YDJZiS72NYq37yfS5XRVPtOEL9sQ+EhXTETKP2QKZONNTxBOwN166tHHFd6cUgRp2LJLm+cPi9/KgKZELH4e2Vs+qb3AyX2mtm/VjTSTulY6JRjAPF6EIyQqiLIinyw9TUXNqBs1kT5ma/50YPO2pSXWiOcXX9WWL7ECCNLCfKVRCKlEuaHuIozDuuwGHBrALAdqjWxrj0IMywCZIoVbBke+U7+KCKhKsc5eqsUJu8YfoVR3hxVhUHjlqIFbuAlLvrMqYZd3HgT1O3RSp/eOyXRMGsW0kQKBgQDHvA1p9Qv7K62WYOwwHEIg5Qym5uK4ClhvWUUQPE63bhzrkLkfNlDEOFpsnIk1EonvJHbfzW4pKR36AjbGBv9xxdgntkee+35pN8d8MT2eNMyHdsF6TDToKqPmqKHcxXmnpOCtC9Cam1wjBU+e7gdSoAeNWJeKUzg79HN7M7uD6QKBgQCuAPkd1G7GHkhMiXur1Td6nTmzwz9zPeHHycAE2r1epH22VUfKTu/oDgmky/VKO3JJuc1txt2bHs90iTLRDmcMy9l+6Ix9UJzmanAIRzhAVgmwz1liUNLbnJGl0YxCq0qlQA3FUUmOP36C2cvHcJm/hRIPG2O2nT1DLKsHvgU1xwKBgGL6mPciHU7aBUrZOxJYXpjoiQ2Iq1+imNKNPYFfMW8LHT/LV0HVa87hUkYyiHgJeNtOia48olO5cYZ8ZGJcA2iBL632UaXCYZGIt82epTdqWNTkj9qoOyu2PcTHWncKUVA3j7ORgE2tocolDqDmujBC55svBOHifaKQcE3khA9JAoGAf/fLPjrrNN00gsVhpJ/sa0qSEzh2w4QxNkOT6n4MYzxKD/xsDcc7/MfGI+K0BOHvTXVONXvZoqloHOaB7unOs8R/sivIlqjgmzyQJCZsojQkcFot/HZAfK6LFw4jPyzGev2+ou0DUZA0tHsEuSqAiC+PdnjIPpcpZOvG5KzFHCUCgYEAuLEUmIzQ7ex85Ulag2qfGj0CDMAq1R/rmwrk+1d2aP9GY890HwcyN5dlZGy8/Z/lMlzOGo1kDhhhwOfbiYSit00FdLChVux0wh8nyqqB4IuTy0S0ZeTNjWnjftpNaPXdHh5mpUXXKygCp3UT65HC6coaqoD3Vv8jE32svsz/uPE=";
    private static String appId = "3";
    private static String id = "17671437601";
    private static String url = "https://device.mkeysec.net/openapi/";

    public static void main(String[] args) {
        push();
        batchSign();
//        expire();
//        getUserCert();
    }

    /**
     * 推送签名
     */
    public static void push() {
        String bizSn = "488cf272-36f9-4037-ba61-d9fe348ac0a2";
        String inData = "%%%*+-create";
        String callback_url = "http://127.0.0.1/";
        String mode = "redirect";
        String desc = "test";
        try {
            // 原文SHA256摘要处理
            String msg = GetSHA256FormString(inData);
            // 组织请求数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            map.put("id", id);
            map.put("bizSn", bizSn);
            map.put("msg", URLEncoder.encode(msg, "UTF-8"));
            map.put("url", URLEncoder.encode(callback_url, "UTF-8"));
            map.put("mode", mode);
            map.put("desc", URLEncoder.encode(desc, "UTF-8"));

            // 对msg做签名
            byte[] signData = signature(privatekey, msg);
            String sign = new String(Base64.encode(signData));
            map.put("sign", URLEncoder.encode(sign, "UTF-8"));

            logger.info("request: " + map.toString());
            String resp = HttpClientUtil.doPost(url + "/v1/push/sign", map, "UTF-8");
            logger.info("response == " + URLDecoder.decode(resp, "UTF-8"));
        } catch (Exception e) {
            logger.error("异常  == "+e);
            e.printStackTrace();
        }
    }

    /**
     * 批量推送签名
     */
    public static void batchSign() {
        String bizSn = "488cf272-36f9-4037-ba61-d9fe348ac0a2";
        String inData = "%%%*+-create";
        try {
            // 原文SHA256摘要处理
            String msg = GetSHA256FormString(inData);
            // 组织请求数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            map.put("id", id);

            JSONArray data = new JSONArray();
            JSONObject da = new JSONObject();
            da.put("bizSn", bizSn);
            da.put("msg", msg);
            da.put("msgWrapper", "0");
            da.put("desc", "123855522");
            data.add(da);

            map.put("data", URLEncoder.encode(data.toString(), "UTF-8"));

            // 对msg做签名
            byte[] signData = signature(privatekey, data.toString());
            String sign = new String(Base64.encode(signData));
            map.put("sign", URLEncoder.encode(sign, "UTF-8"));

            logger.info("request: " + map.toString());
            String resp = HttpClientUtil.doPost(url + "/v1/batch/sign", map, "UTF-8");
            logger.info("response == " + URLDecoder.decode(resp, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户证书
     */
    public static void getUserCert() {
        try {
            // 组织请求数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            map.put("id", id);
            // 对msg做签名
            String indata = "appId=" + appId + "&id=" + id;
            byte[] signData = signature(privatekey, indata);
            String sign = new String(Base64.encode(signData));
            map.put("sign", URLEncoder.encode(sign, "UTF-8"));

            logger.info("map=" + map.toString());
            String resp = HttpClientUtil.doPost(url + "/v1/user/cert", map, "UTF-8");
            logger.info("response == " + URLDecoder.decode(resp, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户印章
     */
    public static void getUserSeal() {
        try {
            // 组织请求数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            map.put("id", id);
            // 对msg做签名
            String indata = "appId=" + appId + "&id=" + id;
            byte[] signData = signature(privatekey, indata);
            String sign = new String(Base64.encode(signData));
            map.put("sign", URLEncoder.encode(sign, "UTF-8"));

            logger.info("map=" + map.toString());
            String resp = HttpClientUtil.doPost(url + "/v1/user/seal", map, "UTF-8");
            logger.info("response == " + URLDecoder.decode(resp, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取免密时间
     */
    public static void expire() {
        try {
            // 组织请求数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", appId);
            map.put("id", id);
            // 对msg做签名
            String indata = "appId=" + appId + "&id=" + id;
            byte[] signData = signature(privatekey, indata);
            String sign = new String(Base64.encode(signData));
            map.put("sign", URLEncoder.encode(sign, "UTF-8"));

            logger.info("map=" + map.toString());
            String resp = HttpClientUtil.doPost(url + "/v1/user/expire", map, "UTF-8");
            logger.info("response == " + URLDecoder.decode(resp, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] signature(String privateKey, String data) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);

            Signature oSig = Signature.getInstance(SIGN_ALGORITHMS);
            oSig.initSign(priKey);
            oSig.update(data.getBytes("UTF-8"));
            return oSig.sign();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String GetSHA256FormString(String inData) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(inData.getBytes());
            result = new String(BASE64EncoderStream.encode(messageDigest));
            System.out.println("BASE64EncoderStream1: " + result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
