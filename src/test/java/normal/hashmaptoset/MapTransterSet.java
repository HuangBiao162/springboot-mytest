package normal.hashmaptoset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapTransterSet {



    public static void main(String[] args) {
        MyHashSet<Integer> myHashSet = new MyHashSet<>();
        HashSet set = new HashSet();
        myHashSet.add(1);
        myHashSet.add(2);
        myHashSet.add(3);
        System.out.println( myHashSet.all() );
        System.out.println( myHashSet.size() );
        System.out.println(myHashSet.remove(1));
        System.out.println(myHashSet.remove(2));
        System.out.println( myHashSet.size() );
        System.out.println( myHashSet.all() );

    }

}

class MyHashSet<E> {
    Map <E, Object> hashMap = new HashMap <>();

    public void add(E key) {
        hashMap.put(key, new Object());
    }

    public Object remove(Object key) {
        return hashMap.remove(key);
    }

    public int size(){
        return hashMap.size();
    }

    public Set<E> all(){
        return hashMap.keySet();
    }

}

