package normal.po.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Description: 基础数据传输对象
 * CreateDate:  2018/6/1
 *
 * @author yimeng
 * @version 1.0
 */
@Data
public abstract class BaseDTO implements Serializable {

    /** 卡牛用户唯一标识 */
    private String userId;
    /** 设备udid */
    private String udid;
    /** 随手记用户唯一标识 */
    private String fname;
    /** queryId */
    private String queryId;
    /** 用户身份证号 */
    private String idCard;
    /** 用户名 */
    private String userName;
    /** 手机号 */
    @JSONField(alternateNames = "phone")
    private String loginphone;
    /** 用户数据来源 */
    private String source;
    /** 入库时间 */
    private Date createTime;
    /** 银行账户 */
    private String cardNo;
    /**
     * 对外提供的接口对应的服务代码,如 risk-inter中某个url对应的服务代码
     */
    private String serviceCode;
    /**
     * 调用方code:即分配个某个部分或者某个人的账号,这个账号用来调用风控对外提供的服务(serviceCode)
     */
    private String callerCode;

    /**
     * 是否不请求缓存直接请求三方,1直接请求三方，会提前删除缓存，0走正常调用
     */
    private Integer cache;

    /**
     * 是否删除缓存
     * @return 根据cache字段判断是否需要删除，为1的时候返回true
     */
    public boolean whetherDeleteCache() {
        return this.cache != null && Objects.equals(this.cache, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return Objects.equals(userId, baseDTO.userId) &&
                Objects.equals(udid, baseDTO.udid) &&
                Objects.equals(fname, baseDTO.fname) &&
                Objects.equals(queryId, baseDTO.queryId) &&
                Objects.equals(idCard, baseDTO.idCard) &&
                Objects.equals(userName, baseDTO.userName) &&
                Objects.equals(loginphone, baseDTO.loginphone) &&
                Objects.equals(source, baseDTO.source) &&
                Objects.equals(createTime, baseDTO.createTime) &&
                Objects.equals(cardNo, baseDTO.cardNo) &&
                Objects.equals(serviceCode, baseDTO.serviceCode) &&
                Objects.equals(callerCode, baseDTO.callerCode) &&
                Objects.equals(cache, baseDTO.cache);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, udid, fname, idCard, userName, loginphone, source, createTime, cardNo, serviceCode, callerCode, cache);
    }
}
