package com.example.ductuanUET.database;

import com.example.ductuanUET.models.Product;
import com.example.ductuanUET.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //Product productA = new Product("macbook pro 16 inch", 2020, 2300.0, "");
                //Product productB = new Product("ipad air green", 2021, 599.0, "");
                //logger.info("insert data: " + productRepository.save(productA));
                //logger.info("insert data: " + productRepository.save(productB));
            }
        };
    }

}
