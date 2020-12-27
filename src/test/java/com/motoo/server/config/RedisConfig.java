package com.motoo.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

//@Configuration
@Slf4j
public class RedisConfig {
    private RedisServer redisServer;

    public RedisConfig(@Value("${spring.redis.port}") int redisPort) {
        log.info("redisTEST ::  redis init" );
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
