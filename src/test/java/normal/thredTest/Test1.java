package normal.thredTest;

public class Test1 {

    public static int count=0;

    public static void incr(){
        try {
            Thread.sleep(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            new Thread(()->{incr();}).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

}
