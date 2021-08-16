package myentertainment.ZhaJinHua;

import lombok.extern.slf4j.Slf4j;
import myentertainment.ZhaJinHua.bo.PersonBO;
import myentertainment.ZhaJinHua.em.CardTypeEnum;
import myentertainment.ZhaJinHua.em.TransformEnum;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class ZhaJinHuaV2 {
    //定义牌面大小和牌的类型
    //public static String[] allCardValue = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public static String[] allCardValue = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
    //public static String[] allCardType = new String[]{"黑桃", "红桃", "梅花", "方块"};
    //public static String[] allCardType = new String[]{"♠", "♥", "♣", "♦"};
    public static String[] allCardType = new String[]{"-1", "-2", "-3", "-4"};
    //已经发出去的牌，用来去重随机产生重复的牌
    public static Set existCard = new HashSet <String>();
    static Integer firstCard;
    static Integer secondCard;
    static Integer thirdCard;
    //类型
    static String firstCardType;
    static String secondCardType;
    static String thirdCardType;

    public static void main(String[] args) throws Exception {
        //先获取牌
        PersonBO p1 = new PersonBO("黄彪");
        PersonBO p2 = new PersonBO("蒋其");
        PersonBO p3 = new PersonBO("徐荣");
        PersonBO p4 = new PersonBO("曾翔");
        //发牌
        PersonBO[] personBOS = getCard(p1, p2, p3,p4);
        //比较大小
        List <PersonBO> winner = winner(personBOS);
        addMoney(winner);

    }

    public static void addMoney(List <PersonBO> personBOS){

        personBOS = new ArrayList <>(personBOS);
        Boolean flag=true;
        BigDecimal moneyAll = new BigDecimal(0);
        Scanner scanner = new Scanner(System.in);
        List<String> listName;
        while (flag){
            for(int i=0;i<personBOS.size();i++){
                System.out.println(personBOS.get(i).getName()+" 加多少(单位：元，丢弃按'y')：");
                String scanerNext = scanner.nextLine();
                if("y".equals(scanerNext)){
                    listName = new ArrayList <>();
                    System.out.print(personBOS.get(i).getName()+" 已弃牌，目前还剩玩家：");
                    personBOS.remove(i);
                    for(PersonBO personBO : personBOS){
                        listName.add(personBO.getName());
                    }
                    i--;
                    System.out.println(listName);
                    if(personBOS.size()<=1){
                        System.out.println("游戏结束，玩家"+personBOS.get(i).getName()+"获胜，总奖金："+moneyAll+"元");
                        flag=false;
                        break;
                    }
                }else{
                    moneyAll = moneyAll.add(BigDecimal.valueOf(Long.valueOf(scanerNext)));
                    System.out.println(personBOS.get(i).getName()+" 加"+scanerNext+"元，当前奖池："+moneyAll+"元");
                }

            }

        }

    }

    /**
     * 获取并设置Person分数
     */
    public static PersonBO[] getScore(PersonBO... personList) {

        for (PersonBO personBO : personList) {
            ArrayList <String> cardList = personBO.getCards();
            //排序，先按牌字面大小进行排序，相同则按花色，黑红梅方
            Collections.sort(cardList, (o1, o2) -> {
                Integer one = Integer.valueOf(o1.substring(2));
                Integer two = Integer.valueOf(o2.substring(2));
                if (one.equals(two)) {
                    one = Integer.valueOf(o1.substring(0, 2));
                    two = Integer.valueOf(o2.substring(0, 2));
                }
                return one - two;
            });
            //获取花色和单张牌的大小
            ArrayList <String> colorList = new ArrayList <>();
            ArrayList <Integer> singleCardList = new ArrayList <>();
            for (String card : cardList) {
                String color = card.substring(0, 2);
                Integer singleCard = Integer.valueOf(card.substring(2));
                colorList.add(color);
                singleCardList.add(singleCard);
            }
            //设置牌型属性
            setCardTypeEnum(personBO, singleCardList, colorList);
            //设置分数
            setScore(personBO, singleCardList, colorList);
        }

        return personList;
    }

    /**
     * 判断赢家
     */
    public static List<PersonBO> winner(PersonBO... personList) {
        //获取分数
        personList = getScore(personList);
        //判断分数大小
        List <PersonBO> personBOS = Arrays.asList(personList);
        Collections.sort(personBOS, (o1, o2) -> {
            return (int) (o2.getScore() - o1.getScore());
        });

        for (PersonBO personBO : personBOS) {
            //System.out.println(personBO);
            transformValue(personBO);
            System.out.println(personBO);
        }

        return personBOS;
        //System.out.println("赢家为：" + personBOS.get(0).getName());

    }


    public static void transformValue(PersonBO personBO) {
        ArrayList <String> cardList = personBO.getCards();
        ArrayList <String> newCard = new ArrayList <>();
        //获取花色和单张牌的大小
        for (String cards : cardList) {
            String key = TransformEnum.getValueByKey(cards.substring(0, 2));
            String value = TransformEnum.getValueByKey(cards.substring(2));
            newCard.add(key + value);
        }
        personBO.setCards(newCard);


    }

    /**
     * 发牌
     *
     * @param personBOS
     * @return
     * @throws Exception
     */
    public static PersonBO[] getCard(PersonBO... personBOS) throws Exception {

        //洗牌
        List <String> allCards = new ArrayList <>(52);
        for (int i = 0; i < allCardType.length; i++) {
            String currentCardType = allCardType[i];
            for (int j = 0; j < allCardValue.length; j++) {
                String currentCardValue = allCardValue[j];
                Random rd = new Random();
                int index = rd.nextInt(allCards.size() + 1);
                allCards.add(index, currentCardType + allCardValue[j]);

            }
        }
        //限制玩家人数
        int currentPersonSize = personBOS.length;
        int maxPersonSize = 52 / 3;
        if (currentPersonSize > maxPersonSize) {
            System.out.println("参与人数太多啦，牌不够发啦！");
            return null;
        }
        //发牌
        //发牌使用队列弹出
        Queue <String> queue = new ArrayDeque <>();
        queue.addAll(allCards);
        for (int i = 0; i < 3; i++) {
            for (PersonBO personBO : personBOS) {
                personBO.setAddCard(queue.poll());
            }
        }
        return personBOS;
    }

    /**
     * 设置分数
     *
     * @param personBO
     * @param singleCardList
     * @param colorList
     */
    public static void setScore(PersonBO personBO, List <Integer> singleCardList, List <String> colorList) {
        Long score;
        //牌型分应占比最大
        Long resultTypeScore = personBO.getCardTypeEnum().score * 10000L;
        //相同牌型，第二比重应该为最大牌的值
        //PS：对子除外，对子直接比对子的大小
        Long maxCardScore = singleCardList.get(singleCardList.size() - 1) * 100L;
        if (personBO.getCardTypeEnum().equals(CardTypeEnum.DUI_ZI)) {
            Map <Integer, Object> map = new HashMap <>();
            for (int i = 0; i < 3; i++) {
                if (map.containsKey(singleCardList.get(i))) {
                    maxCardScore = singleCardList.get(i) * 100L;
                    break;
                } else {
                    map.put(singleCardList.get(i), i);
                }
            }
        }
        //相同牌型，最大牌也相同，则比较最大牌的花色
        Long maxCardColorScore = Integer.valueOf(colorList.get(colorList.size() - 1)) * 10L;

        personBO.setScore(resultTypeScore + maxCardScore + maxCardColorScore);
    }

    /**
     * 为Person对象设置牌型属性
     */
    public static void setCardTypeEnum(PersonBO personBO, List <Integer> singleCardList, List <String> colorList) {
        if (isJinTiao(singleCardList)) {
            personBO.setCardTypeEnum(CardTypeEnum.JIN_TIAO);
        } else if (isShunJin(singleCardList, colorList)) {
            personBO.setCardTypeEnum(CardTypeEnum.SHUN_JIN);
        } else if (isJinHua(colorList)) {
            personBO.setCardTypeEnum(CardTypeEnum.JIN_HUA);
        } else if (isShunZi(singleCardList)) {
            personBO.setCardTypeEnum(CardTypeEnum.SHUN_ZI);
        } else if (isDuiZi(singleCardList)) {
            personBO.setCardTypeEnum(CardTypeEnum.DUI_ZI);
        } else {
            personBO.setCardTypeEnum(CardTypeEnum.NORMAL);
        }
    }

    /**
     * 判断是否为金条
     */
    public static Boolean isJinTiao(List <Integer> singleCardList) {
        return singleCardList.get(0).equals(singleCardList.get(1)) && singleCardList.get(0).equals(singleCardList.get(2));
    }

    /**
     * 判断是否顺金
     */
    public static Boolean isShunJin(List <Integer> singleCardList, List <String> colorList) {
        Boolean isJinHua = false;
        Boolean isShunzi = false;
        //是否金花
        if (colorList.get(0).equals(colorList.get(1)) && colorList.get(0).equals(colorList.get(2))) {
            isJinHua = true;
        }
        //是否顺子
        if (Math.abs(singleCardList.get(0) - singleCardList.get(1)) == 1 && Math.abs(singleCardList.get(1) - singleCardList.get(2)) == 1) {
            isShunzi = true;
        }
        return isJinHua && isShunzi;
    }

    /**
     * 是否金花
     */
    public static Boolean isJinHua(List <String> colorList) {
        return colorList.get(0).equals(colorList.get(1)) && colorList.get(0).equals(colorList.get(2));
    }

    /**
     * 是否顺子
     */
    public static Boolean isShunZi(List <Integer> singleCardList) {
        return Math.abs(singleCardList.get(0) - singleCardList.get(1)) == 1 && Math.abs(singleCardList.get(1) - singleCardList.get(2)) == 1;
    }

    /**
     * 是否对子
     */
    public static Boolean isDuiZi(List <Integer> singleCardList) {
        if (singleCardList.get(0).equals(singleCardList.get(1))
                || singleCardList.get(0).equals(singleCardList.get(2))
                || singleCardList.get(1).equals(singleCardList.get(2))) {
            return true;
        }
        return false;
    }


}
