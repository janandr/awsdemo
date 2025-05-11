package com.example.aws;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {
    private final String username = "janandr";
    private final String password = "Apoorva19$";
    Resource resource = new ClassPathResource("application.conf");
    
    @Primary
    public @Bean
    CqlSession session() throws NoSuchAlgorithmException, java.io.IOException {
    File driverConfig = resource.getFile();
        return CqlSession.builder().
                withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
                withAuthCredentials(username, password).
                withSslContext(SSLContext.getDefault()).
                withKeyspace("my_keyspace").
                build();
    }
}