package liar.gamemvcservice.common.config;

import liar.gamemvcservice.common.aop.RedisLockAspectAop;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class RedisAopConfig {

    @Bean
    public RedisLockAspectAop redisLockAspect(RedissonClient redissonClient) {
        return new RedisLockAspectAop(redissonClient);
    }

}
