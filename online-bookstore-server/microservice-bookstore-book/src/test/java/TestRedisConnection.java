import com.onlinebookstore.BookServerApplication;
import com.onlinebookstore.util.RandomUtils;
import com.onlinebookstore.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/17 14:47
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookServerApplication.class})
public class TestRedisConnection {

    static class User {
        private Long id;
        private String telephone;
        private String nickname;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", telephone='" + telephone + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickName) {
            this.nickname = nickName;
        }
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RandomUtils randomUtils;

    @Test
    public void testRandom() {
        for (int i = 0; i < 10; i++) {
            System.out.println(randomUtils.getInt(10));
        }
    }

    @Test
    public void set() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        System.out.println(redisUtils.set("testList", list, 30));
    }

    @Test
    public void contextLoads() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        User user = new User();
        user.setId(10008L);
        user.setTelephone("12312312311");
        user.setNickname("Korbin");
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
