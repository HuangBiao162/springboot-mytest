package normal.secrity;

import normal.util.BaiHangEnDecryptUtil;
import normal.util.ThreeDESUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLEncoder;

public class decryptThreeDESECBTest {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) throws Exception {
        final String key = "cf410f84904a44cc8a7f48fc4134e8f9";
        // 加密流程
        String telePhone = "15629551180";
        JSONObject paramsJSON = new JSONObject();
        paramsJSON.put("telePhone","15629551180");
        paramsJSON.put("name","黄彪");
        paramsJSON.put("sex","男");
        JSONObject requestJSON = new JSONObject();
        requestJSON.put("request",paramsJSON);
        System.out.println("原数据：" + requestJSON);
        ThreeDESUtil threeDES = new ThreeDESUtil();
        String telePhone_encrypt = "";
        telePhone_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(JSON.toJSONString(requestJSON), "UTF-8"), key);
        System.out.println("加密后：" + telePhone_encrypt);// nWRVeJuoCrs8a+Ajn/3S8g==

        // 解密流程
        String tele_decrypt = threeDES.decryptThreeDESECB(telePhone_encrypt, key);
        System.out.println("解密后：" + tele_decrypt);

        System.out.println("========");
        String encryptData = BaiHangEnDecryptUtil.encryptRequestParams(key, JSON.toJSONString(requestJSON));
        String decryptData = BaiHangEnDecryptUtil.decryptResponse(encryptData, key);
        System.out.println("加密后："+encryptData);
        System.out.println("解密后："+decryptData);
    }


}
