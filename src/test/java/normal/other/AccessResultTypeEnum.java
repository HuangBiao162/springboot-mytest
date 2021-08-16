package normal.other;

import lombok.Getter;

import java.util.Objects;

/***
 * 资金类型枚举
 * @Author rlcheng
 * @Date 2020/4/13
 **/
@Getter
public enum AccessResultTypeEnum {

    /**
     * 准入通过
     */
    PERMIT(0),

    /**
     * 准入拒绝
     */
    REJECT(1),

    ;

    /**
     * 编码
     */
    private Integer code;


    AccessResultTypeEnum(int code) {
        this.code = code;

    }

    public static AccessResultTypeEnum getByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (AccessResultTypeEnum type : AccessResultTypeEnum.values()) {
            if (code.equals(type.getCode())) {
                return type;
            }
        }

        return null;
    }

}
