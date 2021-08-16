package normal.thirdpart;

import com.alibaba.fastjson.JSONObject;
import com.feidee.common.util.HttpUtil;
import com.feidee.common.util.cryptograph.SimpleAES;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PRO_ThirdPartTest {

    public static final String PRO_URL = "https://riskinter.cardniu.com/risk-inter";
    private static String interUserName = "caifusuishouweili"; //接口调用方账户
    //adminInter *963.852  wujun2 12345678978945612320  caifusuishouweili 5d9bebd9a7b621d8
    private static String interPassword = "5d9bebd9a7b621d8";


    /**
     * 【融慧金科】
     */
    @Test
    public void ronghuiDuoTou(){
        /**
         * 多头 - "/antiFraud/ronghui/getDuoTouNum"
         * 消费分期信用 - "/antiFraud/ronghui/consumeStage/creditScore"
         * 高危借贷人群 - "/antiFraud/ronghui/highRisk/loanCrowd"
         * 反欺诈风险名单 - "/antiFraud/ronghui/getAntiFraud"
         */
        String result = HttpUtil.post(PRO_URL+"/antiFraud/ronghui/getDuoTouNum",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】多头：\n"+result);
        /*result = HttpUtil.post(PRO_URL+"/antiFraud/ronghui/consumeStage/creditScore",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】消费分期信用：\n"+result);
        result = HttpUtil.post(PRO_URL+"/antiFraud/ronghui/highRisk/loanCrowd",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】高危借贷人群：\n"+result);
        result = HttpUtil.post(PRO_URL+"/antiFraud/ronghui/getAntiFraud",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】反欺诈风险名单：\n"+result);*/
    }

    /**
     * 手机实名认证
     */
    @Test
    public void mobileVerify() {
        String result = HttpUtil.post(PRO_URL + "/personalInfo/verifyMobile", getParamMap(), getHeaderMap());
        System.out.println("手机实名认证接口========\n" + result);
    }

    /**
     * 测试百融多头接口
     */
    @Test
    public void baiRongMultiplateLoanTest() {
        String result = HttpUtil.post(PRO_URL+"/thirdpart/baiRongMultiplateLoan", getParamMap(), getHeaderMap());
        System.out.println("百融多头接口========\n" + result);
    }

    /**
     * 手机在网时长
     */
    @Test
    public void mobilePeriod() {
        String result = HttpUtil.post(PRO_URL + "/personalInfo/verifyMobileUsePeriod", getParamMap(), getHeaderMap());
        System.out.println("手机在网时长接口========\n" + result);
    }

    @Test
    public void bairong() {
        String result = HttpUtil.post(PRO_URL + "/bairong/creditCardScoreForDepartment2", getParamMap(), getHeaderMap());
        System.out.println("百融信用卡接口========\n" + result);
    }

    @Test
    public void bairongTest() {
        String result = HttpUtil.post("http://tg.feidee.net/risk-inter/bairong/creditCardScoreForDepartment2", getParamMap(), getHeaderMap());
        System.out.println("百融信用卡接口========\n" + result);
    }

    /**
     * 手机在网状态
     */
    @Test
    public void mobileStatus() {
        String result = HttpUtil.post(PRO_URL + "/personalInfo/verifyMobileStatus", getParamMap(), getHeaderMap());
        System.out.println("手机在网状态接口========\n" + result);
    }
    /**
     * 致诚阿福分
     */
    @Test
    public void aFuScore() {
        String result = HttpUtil.post(PRO_URL + "/antiFraud/zhicheng/getAfuAscord", getParamMap(), getHeaderMap());
        System.out.println("致诚阿福分接口========\n" + result);
    }

    /**
     * 银行卡验证
     */
    @Test
    public void bankVerify(){
        String result = HttpUtil.post(PRO_URL+"/personalInfo/verifyBankCard",getParamMap(),getHeaderMap());
        System.out.println("银行卡验证接口：\n"+result);
    }

    /**
     * 电话邦电话标签查询接口
     */
    @Test
    public void PhoneTag(){
        String result = HttpUtil.post(PRO_URL+"/personalInfo/queryPhoneTag",getParamMap(),getHeaderMap());
        System.out.println("电话邦电话标签查询接口：\n"+result);
    }

    /**
     * 百行欺诈分
     */
    @Test
    public void baiHangAntiScore() {
        String result = HttpUtil.post(PRO_URL + "/baihang/antiFraudInfo", getParamMap(), getHeaderMap());
        System.out.println("百行欺诈分接口========\n" + result);
    }

    /**
     * 白骑士
     */
    @Test
    public void baiqishi() {
        String result = HttpUtil.post(PRO_URL + "/blackList/getSsLoanBaiqishiResult", getParamMap(), getHeaderMap());
        System.out.println("白骑士欺诈分接口========\n" + result);
    }


    /**
     * 身份验证二要素
     */
    @Test
    public void verifyIdCardTest() {
        String result = HttpUtil.post("https://riskinter.cardniu.com/risk-api/idCardUnityVerify/verify", getParamMap(), getHeaderMap());
        System.out.println("身份验证二要素接口========\n" + result);
    }



    /**
     * 参数Map
     *
     * @return
     */
    public Map <String, String> getParamMap() {
        Map <String, String> paramMap = new HashMap <String, String>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idCard", "362201199002232211");
        jsonObject.put("userName", "王雪玲");
        jsonObject.put("custName", "王雪玲");
        jsonObject.put("loginphone", "18675204186");
        jsonObject.put("cardNo","6222032106000492687");
        /*jsonObject.put("idCard", "63212219950201567X");
        jsonObject.put("userName", "李玉");*/
        jsonObject.put("phones",new String[]{"18675204186"});
        /*jsonObject.put("idCard", "441323198609200599");
        jsonObject.put("userName", "陈亚雄");
        jsonObject.put("loginphone", "13826738869");
        jsonObject.put("cardNo","6216602000007144155");*/
        /*jsonObject.put("idCard", "43122919980220161X");
        jsonObject.put("custName", "吴长城");
        jsonObject.put("loginphone", "18576207356");
        jsonObject.put("cardNo","6216602000007144155");*/
        //jsonObject.put("talkingDataType","00");
        //jsonObject.put("merchantNo", "KANIUC01");
        /*jsonObject.put("idCard", "441323198609200599");
        jsonObject.put("userName", "王大柱");
        jsonObject.put("loginphone", "13711111116");*/
        jsonObject.put("userId","u_7wcepr");
        jsonObject.put("userType","0");
        jsonObject.put("userKey","456123");
        jsonObject.put("business","test");
        jsonObject.put("queryReason","2");
        String requestData = null; //base64 编码
        try {
            requestData = Base64.encodeBase64URLSafeString(jsonObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        paramMap.put("data", requestData);  //请求参数
        return paramMap;
    }

    /**
     * 得到请求头
     *
     * @return
     */
    public Map <String, String> getHeaderMap() {
        Map <String, String> headerMap = new HashMap <String, String>();
        String timestamp = String.valueOf(new Date().getTime());
        String authorization = SimpleAES.encrypt(timestamp + SimpleAES.encrypt(interUserName, interPassword) + timestamp, interPassword);
        headerMap.put("timestamp", timestamp);  //头部鉴权参数
        headerMap.put("userName", interUserName);
        headerMap.put("authorization", authorization);
        return headerMap;
    }

}
