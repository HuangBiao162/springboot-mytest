package normal.po;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Data
@Builder
public class UserPO {
    private String name;
    private Integer age;
    private String[] girlFriends;

    public UserPO(String name, Integer age, String[] girlFriends) {
        this.name = name;
        this.age = age;
        this.girlFriends = girlFriends;
    }

    public UserPO() {
    }



    @Override
    public int hashCode() {
        int result = Objects.hash(name, age);
        result = 31 * result + Arrays.hashCode(girlFriends);
        return result;
    }
}
