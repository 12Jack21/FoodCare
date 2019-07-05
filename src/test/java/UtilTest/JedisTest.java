package UtilTest;

import baseTest.BaseTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import static org.junit.Assert.*;

public class JedisTest extends BaseTest {

    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.set("my","123");

        System.out.println(jedis.get("my"));
        assertEquals("123",jedis.get("my"));
        jedis.close();
    }

}
