package com.itniuma.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.itniuma.controller", "com.itniuma.config"})
@EnableWebMvc
public class SpringMvcConfig {
}
