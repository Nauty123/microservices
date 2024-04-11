// package com.example.config;

// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import javax.sql.DataSource;

// @Configuration
// public class DatabaseConfig {

//     @Bean
//     public DataSource dataSource() {
//         DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//         dataSourceBuilder.url("jdbc:postgresql://localhost:5432/ecommerce");
//         dataSourceBuilder.username("postgres");
//         dataSourceBuilder.password("pakistan");
//         return dataSourceBuilder.build();
//     }
// }

package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUsername);
        dataSourceBuilder.password(dbPassword);
        return dataSourceBuilder.build();
    }
}