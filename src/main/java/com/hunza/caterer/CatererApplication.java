package com.hunza.caterer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CatererApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CatererApplication.class, args);
    }
}
