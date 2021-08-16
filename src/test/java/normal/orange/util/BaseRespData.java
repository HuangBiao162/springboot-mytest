package normal.orange.util;

import normal.orange.po.SelBindingCardListRespDTO;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 接收桔子请求响应实体对象
 *
 * @author biao_huang
 * @Date 2021/04/08
 */
@Data
public class BaseRespData<T> {

    private Boolean success;
    private String code;
    private String message;

    /**
     * 渠道订单号
     */
    private String orderNo;

    /**
     * 渠道 ID
     */
    private Integer channelId;

    /**
     * 订单总状态
     * <p>
     * 0 正常，10 取消，20 逾期， 40 结清
     */
    private Integer orderState;

    /**
     * result结果集
     */
    private T result;

    public static <K> BaseRespData <K> buildBaseRespData(JSONObject jsonObject, Class <K> clazz) {
        BaseRespData <K> respData = new BaseRespData <>();
        respData.setSuccess(jsonObject.getBoolean("success"));
        respData.setCode(jsonObject.getString("code"));
        respData.setMessage(jsonObject.getString("message"));
        respData.setOrderNo(jsonObject.getString("orderNo"));
        respData.setChannelId(jsonObject.getInteger("channelId"));
        respData.setOrderState(jsonObject.getInteger("orderState"));

        if (clazz == SelBindingCardListRespDTO.class) {
            JSONObject json = new JSONObject();
            json.put("bindingCardInfos",jsonObject.get("result"));
            jsonObject.put("result",json);
        }
        respData.setResult(jsonObject.getObject("result", clazz));


        /*//如果是查询绑卡银行卡列表，则需要结果映射
        if (clazz == SelBindingCardListRespDTO.class) {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            SelBindingCardListRespDTO respDTO = new SelBindingCardListRespDTO();
            List <BindingCardInfo> bindingCardInfos = new ArrayList();
            for (Object o : jsonArray) {
                bindingCardInfos.add((BindingCardInfo) o);
            }
            respDTO.setBindingCardInfos(bindingCardInfos);
            respData.setResult((K) respDTO);
        }*/

        return respData;
    }
}
