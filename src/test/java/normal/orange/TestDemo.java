package normal.orange;

import normal.orange.util.RSAUtil;
import normal.po.UserPO;
import normal.util.AESUtils_suixin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

public class TestDemo {
    String URL = "https://sandbox-zx-api-platform.juzishuke.com";//桔子接口地址
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC5Dt6OPSLK30pfUwtXhKdbJdVAtRsdUlCuZTkNvmjSye_w9ZIdI9cU-VV6sDVz9csbIv2FVYL7aHxrZc-Y6ZQg15fFrHWU_8-LSKe4EcI9FZgcCs4Sti_QlZyP-lStprPZ3V05-6wWfvsYaB07UoQG_t_gKOxlvteeRtGofLoE548qOu3Ynk-dwTsMeMInBbomZrIXROxXVar2mxdHfrhADrWsBXbaI8RQQOO2D139xr1vBSoc-1NtHIxiC74ejX4KpMyIBlMZ4nSiEVx8FS5e7Yy5MQqDtCuWo2ntuCrSqKPsAmuA5X1uFBVY6MvGreKDcBQO0K1Y3-kkxRZBcVKRAgMBAAECggEBAKlUeMJQIIjffg7xTtx67iOR36rAzJms21RoHKd9xi6yRlIGcfcl90UizFth5tc_XeRrxOx_DUX8GnSvCSDTAg53F1Hx9vkqhX7eMVFVNpVsL-3g2ST3_ZJvg32ewmLOuZytWAOZTKKc-CciBTVl0JcSGknjSEPlNyiFhowT7KZUqIE_jgOslSqKwI2Pn-30VHcb9znQlv_pjS1Abn8XFDX4RnvRnWFzzyUz7028jqmvD2fhT1Jlv0zIFkATlMnldCyzre2UdftOgZ3zWM2jli6jyF1F2OnxmsEPOqrWxGW0DFIthhuaJ0IBSBV0AMWQpEw8JY16xXLSoEhXfprrrU0CgYEA5tO271i5fwl_c4pwaa-F62yZtZML1h7-PHaAGBSJzG3dxxXzQ6cmmd-QuOvX8S1s7VLkLBJCw4q1robe8WeqPvtfeDPGEkyb1rIFTIjmor5kfcTGlTkEQ97eImHh1XNT819DMhjOy_htRTjkTVgVPOm8nsbYMSNElFAucOHkbMMCgYEAzT1d2FivQ9IV30JoDO7B4JTJWh6uVgkSmQDpbi_a3tAreWfXbXiPbIdcTNl5fIF4GwBfeiBVrQbU-ii1pdSGwxhoOdWhXu3lx2jCB7TenycQo1ZAkd6SdooHOEeq8kyiiPduKKD7DmMGCqD3j-PN-MLmPSzRfgi0d4VEUGJJHhsCgYAECGTkz58GCitw4FVQc2GamF4jHvuQ9R-p_MQGJ620paK_TLHgV7ZD7_T-VhiCvTFvAAi-gF8sPWspLnAMtavyx7-GQRlPzojWemu_R5EH_N2SmPKDPujFKsqkvSMN8KLh2KM659YNGD0IfZL3ivdsBgVmnMDAMwaKtM_q5vdvswKBgCISjgHctzwLJJZr79o1yPn_uW28qjSeTPgZrqccSrmy1YTSOHF0EltyHWCkugZxd_8DkNY1iAOxnqnpGD7viW_aHXN8g82-sObp_UxKOqsxURHMv-t3h9kmEQzG2RpgnpseeKtGDmfVCKu7DV5V81vY_2AokJ7b0xoEhdLSflEBAoGAcokF5zPFPTUgOYC6CHOQJBjAGn-M9SQl0sOaz71v9eDxpsoGgap51MBAoHZD30Xto3zEyjs9_PhBFOFaZCmF3ygwMB3V5KOghxFR3FUzhhQOAQXEyN27Hc-KSvytqk2TvNp4jmXRah4JCHqULTvPpLXt9szeEyU0kePhuzYVg38";//渠道方生成的私钥

    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuQ7ejj0iyt9KX1MLV4SnWyXVQLUbHVJQrmU5Db5o0snv8PWSHSPXFPlVerA1c_XLGyL9hVWC-2h8a2XPmOmUINeXxax1lP_Pi0inuBHCPRWYHArOErYv0JWcj_pUraaz2d1dOfusFn77GGgdO1KEBv7f4CjsZb7XnkbRqHy6BOePKjrt2J5PncE7DHjCJwW6JmayF0TsV1Wq9psXR364QA61rAV22iPEUEDjtg9d_ca9bwUqHPtTbRyMYgu-Ho1-CqTMiAZTGeJ0ohFcfBUuXu2MuTEKg7QrlqNp7bgq0qij7AJrgOV9bhQVWOjLxq3ig3AUDtCtWN_pJMUWQXFSkQIDAQAB"; //渠道方生成的公钥   ，需要发给桔子用于配置
    //桔子服务生成的专属交互公钥，渠道方保存并使用
    String juziPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsxr0tlK9XNP01dikpmChVZXNNlgq/BWG+327RwOsyxDopTUXHPq7/5Nr/aWkosEWyr8yXpIVd+TUg13csI7uNbocxW9Dno0LX/fXdyZ+qCxOG7F0o/0/YYrtuEpaEz3aSEAkYJe4hkYyApAQPhXTa3+84b6J3gkCB90CT1x8OCRKczF5WWf/PYp8WHnbWCkDymz1bjEU+Iu9bsGWG/y9UNh9uzFswy+yM7s6M+ueHKT8VHFozxRh3pTbsxhlRSTsRRFJnRI253UCayixgfmROk1VyyAtuY2TovycdWcHTGMesWmOTKJR9es+NyiDWho6QLpPBC1KLCZwDcC6O6V6bQIDAQAB";



    /**
     * 桔子接口测试例子，请求方自己的私钥加密，桔子收到后用请求方的公钥解密，
     * 然后把响应信息用桔子私钥加密，请求方收到响应用信息桔子公钥解密。
     */
    @Test
    public void test() {

        String paramJson = "{\"channelId\":xxxxx}";//桔子分配
        String appid = "xxxxxxx";//桔子分配

        String miData = "{\"appId\":appid,\"sign\":\""
                + RSAUtil.buildRSASignByPrivateKey(paramJson, privateKey) + "\","
                + "\"data\":\"" + RSAUtil.buildRSAEncryptByPrivateKey(paramJson, privateKey)
                + "\"}"; //封装请求数据json串：appid, 密文, 签名

        System.out.println("入参请求数据json串：" + miData);
        String result = null;
        try { //发送请求
            //result = HttpPostUtil.postJsonText(URL, miData); //注意：http请求中设置header属性Content-Type:text/plain
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("响应加密信息json：" + result);
        JSONObject jsonresult = JSONObject.parseObject(result);
        System.out.println("响应明文数据json：" + RSAUtil
                .buildRSADecryptByPublicKey(jsonresult.getString("result"), juziPublicKey));
    }


    @Test
    public void publicKeyTest(){
        // 1. 验签
        String data="aMMEwFwgi8DsSdAP4DWDpPPM96c6Dbcx5WLij9TcKvZ5Bdzu4cXhupJ-7qUYt725VJZZU-iFNYcgZU-FB6E7Ls1Kb1EqJb0mzeepltmnJEIfcCN1z_SVZ_u8ZNxVbkDrE_Fri9-OvyBIJeupRyHWVEw5tKzLc5iIH7lGpPnpE5y_WdvSqkn3IkUvVUcngQVc0VGkb9XkzMkGCyu_fMsZ4MAF8JVeWh4SNVkptHNTscnf3DGvOSGai08cdCdGMZs7xa_Jxs8YiNGFB-CDYJIGqu71SDWN531s-EGdwvakzSPCjfKb_fDW6W47cEb8-YE5JlScplCrpsq0fCk_0omBHg";
        String sign="J2y8LlEx3gQVdccygG-MnAfNxM7Wex7ndBSK9_wIDL-n9AyR4HFV-LIwsz65bLV1uOMpUxq2Cb-KtA3jTMdBuBlOCuiWOROdBEWNOI3gnpNnyl1OKkKT5az5GFl1ua5jYjqSiCCaNoNq1yaUX5aDuJvw1o1XO5ZUlyJN9hJelhupx9vHdHrW7WqMXcx3i69ZKX56xojXK1RW-GpegafKma8oewzrkMUsHDNO5OQB1gqgssHZbkS0I48nRh9Sy9BUFZbzEMtT3QILIwdunBnNs32Bn9t0VcK8jW0y5DNRzYF2rSm4nnBhWc_zBqqe3oTA3YvuJb3CCAA_4NZyktcXJA";
        String decData = RSAUtil.buildRSADecryptByPublicKey(data, juziPublicKey);
        String decSign = RSAUtil.buildRSADecryptByPublicKey(sign, juziPublicKey);
        boolean b = RSAUtil.buildRSAverifyByPublicKey(decData, juziPublicKey, sign);
        System.out.println(b);
    }

    @Test
    public void ttt(){
        String a=null;
        UserPO aa=null;
        System.out.println(JSON.toJSONString(a));
        System.out.println(JSON.toJSONString(aa));
        String[] ss = {"1","2","3"};
        tttt();
    }

    public void tttt(String... sss){
        String str = "hi";
        System.out.println(sss);
        System.out.println(sss.hashCode());
        if(sss.length>0){
            System.out.println(str+sss[0]);
            System.out.println((str+sss[0]).hashCode());
        }

    }

    public static void main(String[] args) throws IOException {
        Map <String, String> map = RSAUtil.initRSAKey(2048);
        for (Map.Entry <String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"="+entry.getValue());
        }


        String juziPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsxr0tlK9XNP01dikpmChVZXNNlgq/BWG+327RwOsyxDopTUXHPq7/5Nr/aWkosEWyr8yXpIVd+TUg13csI7uNbocxW9Dno0LX/fXdyZ+qCxOG7F0o/0/YYrtuEpaEz3aSEAkYJe4hkYyApAQPhXTa3+84b6J3gkCB90CT1x8OCRKczF5WWf/PYp8WHnbWCkDymz1bjEU+Iu9bsGWG/y9UNh9uzFswy+yM7s6M+ueHKT8VHFozxRh3pTbsxhlRSTsRRFJnRI253UCayixgfmROk1VyyAtuY2TovycdWcHTGMesWmOTKJR9es+NyiDWho6QLpPBC1KLCZwDcC6O6V6bQIDAQAB";

        RSAUtil.buildRSADecryptByPublicKey("sjXBsChR_1adyM6SZ6JP1S8JynKXvDQJGkOG4dGZMpbsJv8RqvyxVqpLKLEc96iWkmJBUZEgJkbJnbtmEZNGdAshgBwTpnYbBa8Ad5o2ipFMg2LdvU9wgVsUD8JfIoow0IYu-Wm0YQmB3efsdco42P3j8O9G-NP3E3WnXQGyd8aaJ3d2ybs4D6Yv8TeX5ZzPlmi46Hv86RgSpp8AYze4abn5iej9b6pfkHtfnjrJaQp0uENNo-B7fYCla11EpKbyc_PzeJDNyBXDzAoRjxrob1j63_4SnCz60rsDtFdtexnUL1ehr_enn77FCxblUzZAZ8ScX-iKdr_cg0ds-oC6NA",juziPublicKey);




        /*File file = new File("D:\\privateKey.txt");
        if(!file.exists()){
            file.createNewFile();
        }else{
            file.delete();
        }

        OutputStream outputStream  = new FileOutputStream(file);

        StringBuilder sb = new StringBuilder();
        for(Map.Entry <String, String> entry : map.entrySet()){
            sb.append(entry.getKey()+"："+entry.getValue()+"\n\n\n");

            String context = sb.toString();
            byte[] contextBytes = context.getBytes("UTF-8");
            outputStream.write(contextBytes);
            outputStream.flush();
        }


        outputStream.close();*/

    }

    @Test
    public void test1() throws Exception{
        UserPO user = new UserPO();
        user.setName("张三");
        setAge(user);
        System.out.println(user.toString());

        String decrypt = AESUtils_suixin.decrypt("431126199805233233", "spousCertNo");
        String spousCertNo = AESUtils_suixin.encrypt(decrypt, "spousCertNo");
        System.out.println("decrypt==="+decrypt);
        System.out.println("spousCertNo==="+spousCertNo);

    }

    public void setAge(UserPO user){
        user.setAge(18);
    }


}
