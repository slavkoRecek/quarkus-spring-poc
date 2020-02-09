package si.recek.quarkus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Bean
    public Jedis jedisClient(){
        Jedis client =  new Jedis("127.0.0.1", 6379);
        System.out.println(client.ping());
        return client;
    }
}
