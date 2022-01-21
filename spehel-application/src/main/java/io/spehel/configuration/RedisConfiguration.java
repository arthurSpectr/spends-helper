package io.spehel.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value("${spehel.redis.host}")
    private String host;

    @Value("${spehel.redis.port}")
    private int port;

    @Bean
    LettuceConnectionFactory jedisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory
                = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(host);
        lettuceConnectionFactory.setPort(port);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
