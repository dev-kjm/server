package com.motoo.server.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Profile({"local","dev","real"})
@Slf4j
public class RedisConfig {
    private RedisServer redisServer;

    public RedisConfig(@Value("${spring.redis.port}") int redisPort) {
        log.info("redis ::  redis init" );
        redisServer = new RedisServer(redisPort);
    }

    @PostConstruct
    public void startRedis() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }


}
