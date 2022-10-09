package com.vttp2.miniproject01;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vttp2.miniproject01.models.User;
@Configuration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    @Value("${spring.redis.username}")
    private String redisUsername;
    @Value("${REDIS_PASSWORD}")
    private String redisPassword;

    @Bean("redislab")
    public RedisTemplate<String, String> initRedisTemplate() {
        // Configure the Redis database
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        //String redisPassword = System.getenv("spring.redis.password");
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDatabase);
        redisConfig.setUsername(redisUsername);
        redisConfig.setPassword(redisPassword);

        // Create an instance of the Jedis driver
        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();

        // Create a factory for jedis connection
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();

        // Create RedisTemplate
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);
        // Explain tomorrow
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, User> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        //String redisPassword = System.getenv("spring.redis.password");
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(redisPassword);

        //****required if using JSON files
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(User.class);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.info(">>>> redis host port > {redisHost} {redisPort}", redisHost, redisPort);

        RedisTemplate<String, User> template = new RedisTemplate<String, User>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonJsonSerializer);
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashValueSerializer(template.getValueSerializer());
        return template;
    }
}
