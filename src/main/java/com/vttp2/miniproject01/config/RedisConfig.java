package com.vttp2.miniproject01.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.vttp2.miniproject01.models.Employee;

@Configuration
public class RedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean(name = "orderListRedisConfig")
    @Scope("singleton")
    public RedisTemplate<String, Employee> redisTemplate() {
        //set up the configuration into RedisStandaloneConfiguration object
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        //need to have .get() as it is an Optional<Integer>
        config.setPort(redisPort.get());
        config.setPassword(redisPassword);
        
        //Jedis is java client for redis, create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.info("redis host port > {redisHost} {redisPort}", redisHost, redisPort);
        
        //create template
        RedisTemplate<String, Employee> template = new RedisTemplate<String, Employee>();
        template.setConnectionFactory(jedisFac);

        //all the serializers
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(Employee.class);
        template.setValueSerializer(jackson2JsonJsonSerializer);
        return template;
    }
    
}
