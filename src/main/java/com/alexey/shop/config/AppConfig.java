package com.alexey.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("secret.properties")
public class AppConfig {
}
