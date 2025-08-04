package com.paydaes.utils.database;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
public class DatabaseService {
    static String URL_PREFIX = "jdbc:h2:tcp://localhost:50001/mem:";

    public String create(final String dbname, final String username, final String password, List<Class> clz) {
        String url = URL_PREFIX+dbname;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Map<String, Object> settings =
                    Map.of("hibernate.connection.driver_class", "org.h2.Driver",
                            "hibernate.connection.url", url,
                            "hibernate.connection.username", username,
                            "hibernate.connection.password", password,
                            "hibernate.dialect", "org.hibernate.dialect.H2Dialect");

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            clz.forEach(metadataSources::addAnnotatedClass);
            Metadata metadata = metadataSources.buildMetadata();

            SchemaExport schemaExport = new SchemaExport();
            schemaExport.setFormat(true);
            schemaExport.createOnly(EnumSet.of(TargetType.STDOUT, TargetType.DATABASE), metadata);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return url;
    }

}
