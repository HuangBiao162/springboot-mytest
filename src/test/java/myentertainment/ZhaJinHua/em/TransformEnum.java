package myentertainment.ZhaJinHua.em;

public enum TransformEnum {

    HEI_TAO("-1", "♠"),
    HONG_TAO("-2", "♥"),
    MEI_HUA("-3", "♣"),
    FANG_KUAI("-4", "♦"),


    J("11", "J"),
    Q("12", "Q"),
    K("13", "K"),
    A("14", "A");

    public String key;
    public String value;

    TransformEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (TransformEnum transformEnum : TransformEnum.values()) {
            if (transformEnum.key.equals(key)) {
                return transformEnum.value;
            }
        }
        return key;
    }

    public static String getKeyByValue(String value) {
        for (TransformEnum transformEnum : TransformEnum.values()) {
            if (transformEnum.value.equals(value)) {
                return transformEnum.key;
            }
        }
        return value;
    }

}
