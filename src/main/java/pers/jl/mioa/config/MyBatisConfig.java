package pers.jl.mioa.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 *
 * @author: JL Du
 */

@Configuration
@EnableTransactionManagement
@MapperScan({"pers.jl.mioa.mbg.mapper","pers.jl.mioa.mapper"})
public class MyBatisConfig {
}

