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
        Class.forName("org.postgresql.Driver");
        return DataSourceBuilder
                .create()
                .build();
    }

//    static DataSource getDataSource() throws NamingException {
//        if (dataSource == null) {
//            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:comp/env/");
//            dataSource = (DataSource) envContext.lookup("jdbc/budget");
//        }
//        return dataSource;
//    }
}
