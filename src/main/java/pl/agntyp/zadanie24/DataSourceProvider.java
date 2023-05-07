package pl.agntyp.zadanie24;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
class DataSourceProvider {
    private static DataSource dataSource;

    public DataSourceProvider() { }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    static DataSource getDataSource() throws NamingException, ClassNotFoundException {
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/budget?serverTimezone=UTC")
                .driverClassName("org.postgresql.Driver")
                .username("aga")
                .password("admin")
                .build();
    }

}
