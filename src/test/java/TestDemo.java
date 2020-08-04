import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.util.JDBCUtils;
import com.lhjl.travel.util.JedisUtil;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class TestDemo {
    public static int showreturn() {
        try {
            return (int) 1.2;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        } finally {
            return 33;
        }
    }

    @Test
    public void test1() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        List<User> list = jdbcTemplate.query("select * from tab_user", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(list);
    }

    @Test
    public void test2() {
        String url = "/travel/user/add?name=lhjl&&id=1";
        int i = url.lastIndexOf("/") + 1;
        int j = url.indexOf("?");
        String methodName = url.substring(i, j);
        System.out.println(methodName);
    }

    @Test
    public void test3() throws JsonProcessingException {
        boolean flag = true;
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(flag);
        System.out.println(s);
    }
}
