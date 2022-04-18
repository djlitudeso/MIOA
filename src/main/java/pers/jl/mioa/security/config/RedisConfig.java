package pers.jl.mioa.security.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import pers.jl.mioa.common.config.BaseRedisConfig;

/**
 * Redis配置类
 *
 * @author JL
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
