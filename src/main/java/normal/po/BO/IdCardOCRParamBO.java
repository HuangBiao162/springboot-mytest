package normal.po.BO;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wei_zhou
 * @date 2019/8/9
 **/
@Data
public class IdCardOCRParamBO {

    private String userId;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 身份证照片地址
     */
    @JSONField(serialize = false)
    private String url;
    /**
     * 商户号
     */
    @JSONField(serialize = false)
    private String merCustId;
}
