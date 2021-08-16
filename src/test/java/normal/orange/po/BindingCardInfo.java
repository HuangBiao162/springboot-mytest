package normal.orange.po;

import lombok.*;

/**
 * 桔子--查询银行卡绑卡列表接口请求参数实体类
 *
 * @author Biao_Huang
 * @date 2021/04/08
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class BindingCardInfo {

    /**
     * 桔子卡 ID
     * <p>
     * 支付划扣接口使用
     */
    private Long cardId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 银行 code
     */
    private String bankCode;

    /**
     * 桔子用户 ID
     */
    private String userId;



}
