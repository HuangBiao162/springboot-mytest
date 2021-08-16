package myentertainment.ZhaJinHua.em;

public enum CardTypeEnum {

    JIN_TIAO(10, "金条"),
    SHUN_JIN(9, "顺金"),
    JIN_HUA(8, "金花"),
    SHUN_ZI(7, "顺子"),
    DUI_ZI(6, "对子"),
    NORMAL(5, "常规牌");

    public Integer score;
    public String cardType;

    CardTypeEnum(Integer score, String cardType) {
        this.score = score;
        this.cardType = cardType;
    }

    public static String getCardTypeByEnum(CardTypeEnum cardTypeEnum) {
        return cardTypeEnum.cardType;
    }

    public static String getCardTypeByscore(Integer score) {
        for (CardTypeEnum cardEnum : CardTypeEnum.values()) {
            if (cardEnum.score.equals(score)) {
                return cardEnum.cardType;
            }
        }
        return null;
    }

    public static Integer getScoreByCardType(String cardType) {
        for (CardTypeEnum cardEnum : CardTypeEnum.values()) {
            if (cardEnum.cardType.equals(cardType)) {
                return cardEnum.score;
            }
        }
        return null;
    }

}
