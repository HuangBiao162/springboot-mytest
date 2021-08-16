package myentertainment.ZhaJinHua.bo;

import lombok.Data;
import myentertainment.ZhaJinHua.ZhaJinHuaV2;
import myentertainment.ZhaJinHua.em.CardTypeEnum;

import java.util.ArrayList;

/**
 * 一个人应该有自己的名字，一副牌
 * 牌应该有黑红梅方+数字/英文
 */
@Data
public class PersonBO {
    String name;
    ArrayList <String> cards;
    String addCard;
    Long score;
    CardTypeEnum cardTypeEnum;
    //积分
    Long integral;

    public void setAddCard(String addCard) {
        this.cards.add(addCard);
    }

    public PersonBO() {
        this.cards = new ArrayList <>();
    }

    public PersonBO(String name) {
        this.cards = new ArrayList <>();
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "他的牌是：" + cards + " --牌分：" + this.getScore() + " --牌种类：" + CardTypeEnum.getCardTypeByEnum(cardTypeEnum);
    }


}
