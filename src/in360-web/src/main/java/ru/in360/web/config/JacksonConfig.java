/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.web.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(Instant.class, InstantDeserializer.INSTANCE);
        return new Jackson2ObjectMapperBuilder()
                .findModulesViaServiceLoader(true)
                .featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .modulesToInstall(javaTimeModule, jacksonXmlModule);
    }

    public static class InstantDeserializer extends JsonDeserializer<Instant> {

        static final InstantDeserializer INSTANCE = new InstantDeserializer();

        private InstantDeserializer() {
        }

        @Override
        public Instant deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            ObjectCodec oc = parser.getCodec();
            JsonNode node = oc.readTree(parser);
            return Instant.ofEpochMilli(node.asLong());
        }
    }
}
