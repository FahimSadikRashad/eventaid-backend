package com.example.eventlybackend.evently.config;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**

 The {@code JacksonConfig} class is a configuration class for Jackson Object Mapper.

 It defines a bean for customizing the Jackson2ObjectMapperBuilder.
 */

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        builder.featuresToDisable(SerializationFeature.);
        return builder;
    }
}

