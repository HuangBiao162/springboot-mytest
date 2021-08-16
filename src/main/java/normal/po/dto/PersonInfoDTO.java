package normal.po.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;


/***
 * 用户个人信息dto
 *
 * @author Odz
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PersonInfoDTO extends BaseDTO implements Serializable {

    /** email */
    private String loginemail;
    /** ip */
    private String ip;
    /** qq */
    private String qqNum;
    /** 地址 */
    private String addresss;
    /** imei */
    private String imei;
    /** 电话号码数组 */
    private String[] phones;
    /** 银行代码 */
    private String bankCode;
    /** 测试特性参数 */
    private String isForTest;
    /** 产品代码，即调用方代码 */
    private String productCode;
    /** 外部appId */
    private String[] appIds;
    /** 申请渠道类型 */
    private String applyType;
    /** 设备标识字段类型 */
    private String deviceType;
    /** 设备标示，泛指全平台所有设备标识 */
    private String deviceId;
    /** 公司名称 */
    private String companyName;
    /** 加密类型 0：不加密  1：MD5（默认值）*/
    private String crypotoType;
    private String qq;
    private String wechat;
    private String bankAccount;
    /** 银行卡号，区别cardNoArr */
    private String[] cardNos;
    /**公司地址 */
    private String bizAddr;
    /**家庭地址 */
    private String homeAddr;
    /**户籍地址 */
    private String perAddr;
    /**申请地址 */
    private String applyAddr;
    /**其他地址 */
    private String othAddr;
    /**图片类型，ocr识别使用 */
    private String imageType;
    /** 合作伙伴账号 */
    private String partner;
    /**图片内容 */
    private byte[] bytes;
    /** userKey为数字,则为userId,字符串为userName*/
    private String userKey;
    /** 用户类型*/
    private String userType;
    /** 公司电话 */
    private String companyTel;
    /** 证据号码 */
    private String idNo;
    /** 证件类型 */
    private String idNoType;
    /** 查询原因 */
    private String queryReason;

    /** 是否为独立请求标识*/
    private Boolean isIndependentReq;

    private String idfa;

    /**业务方*/
    private String business;

    /**渠道*/
    private String channelCode;
    /**贷款类型*/
    private String loanType;
    /**talkingdata必传数据*/
    private String merchantNo;
    /**talkingdata参数类型，type为00，则说明入参为md5 32小写加密入参*/
    private String talkingDataType;

    private String custName;
    /**
     * 中智诚-【昊日】流量监控
     * 必传参数，设备信息
     */
    private JSONObject deviceMesg;
    /**
     * 上传的本地图片文件
     */
    private File file;

    /**
     * 图片网络地址
     */
    private String url;

    /**
     * 图片的id,在云端上传过图片可采用
     */
    private String imageId;

    /* 人脸对比接口 */
    @JSONField(name="comparison_type")
    private String comparisonType;
    @JSONField(name="face_image_type")
    private String faceImageType;

    private String idcardName;

    private String idcardNumber;

    private String delta;

    private String checkDelta;

    private String imageBest;

    private String image;

    /* 人脸对比接口 */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonInfoDTO that = (PersonInfoDTO) o;
        return Objects.equals(loginemail, that.loginemail) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(qqNum, that.qqNum) &&
                Objects.equals(addresss, that.addresss) &&
                Objects.equals(imei, that.imei) &&
                Arrays.equals(phones, that.phones) &&
                Objects.equals(bankCode, that.bankCode) &&
                Objects.equals(isForTest, that.isForTest) &&
                Objects.equals(productCode, that.productCode) &&
                Arrays.equals(appIds, that.appIds) &&
                Objects.equals(applyType, that.applyType) &&
                Objects.equals(deviceType, that.deviceType) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(crypotoType, that.crypotoType) &&
                Objects.equals(qq, that.qq) &&
                Objects.equals(wechat, that.wechat) &&
                Objects.equals(bankAccount, that.bankAccount) &&
                Arrays.equals(cardNos, that.cardNos) &&
                Objects.equals(bizAddr, that.bizAddr) &&
                Objects.equals(homeAddr, that.homeAddr) &&
                Objects.equals(perAddr, that.perAddr) &&
                Objects.equals(applyAddr, that.applyAddr) &&
                Objects.equals(othAddr, that.othAddr) &&
                Objects.equals(imageType, that.imageType) &&
                Objects.equals(partner, that.partner) &&
                Arrays.equals(bytes, that.bytes) &&
                Objects.equals(userKey, that.userKey) &&
                Objects.equals(userType, that.userType) &&
                Objects.equals(companyTel, that.companyTel) &&
                Objects.equals(idNo, that.idNo) &&
                Objects.equals(idNoType, that.idNoType) &&
                Objects.equals(queryReason, that.queryReason) &&
                Objects.equals(isIndependentReq, that.isIndependentReq) &&
                Objects.equals(idfa, that.idfa) &&
                Objects.equals(business, that.business) &&
                Objects.equals(channelCode, that.channelCode) &&
                Objects.equals(loanType, that.loanType) &&
                Objects.equals(merchantNo, that.merchantNo) &&
                Objects.equals(talkingDataType, that.talkingDataType) &&
                Objects.equals(custName, that.custName) &&
                Objects.equals(deviceMesg, that.deviceMesg) &&
                Objects.equals(file, that.file) &&
                Objects.equals(url, that.url) &&
                Objects.equals(imageId, that.imageId) &&
                Objects.equals(comparisonType, that.comparisonType) &&
                Objects.equals(faceImageType, that.faceImageType) &&
                Objects.equals(idcardName, that.idcardName) &&
                Objects.equals(idcardNumber, that.idcardNumber) &&
                Objects.equals(delta, that.delta) &&
                Objects.equals(checkDelta, that.checkDelta) &&
                Objects.equals(imageBest, that.imageBest) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), loginemail, ip, qqNum, addresss, imei, bankCode, isForTest, productCode, applyType, deviceType, deviceId, companyName, crypotoType, qq, wechat, bankAccount, bizAddr, homeAddr, perAddr, applyAddr, othAddr, imageType, partner, userKey, userType, companyTel, idNo, idNoType, queryReason, isIndependentReq, idfa, business, channelCode, loanType, merchantNo, talkingDataType, custName, deviceMesg, file, url, imageId, comparisonType, faceImageType, idcardName, idcardNumber, delta, checkDelta, imageBest, image);
        result = 31 * result + Arrays.hashCode(phones);
        result = 31 * result + Arrays.hashCode(appIds);
        result = 31 * result + Arrays.hashCode(cardNos);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
