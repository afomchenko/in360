/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 09.03.18 14:25 Anton Fomchenko 360@in360.ru
 */

package ru.in360.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySources({@PropertySource("classpath:/ftp.properties")})
public class FtpConfig {

    private final Environment env;

    @Autowired
    public FtpConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SftpService sftpService() {
        Map<String, String> props = new HashMap<>();
        props.put(FtpProperties.SFTP_HOST, env.getProperty(FtpProperties.SFTP_HOST));
        props.put(FtpProperties.SFTP_PORT, env.getProperty(FtpProperties.SFTP_PORT));
        props.put(FtpProperties.SFTP_USER, env.getProperty(FtpProperties.SFTP_USER));
        props.put(FtpProperties.SFTP_PASSWORD, env.getProperty(FtpProperties.SFTP_PASSWORD));
        props.put(FtpProperties.SFTP_KNOWN_HOSTS, env.getProperty(FtpProperties.SFTP_KNOWN_HOSTS));
        return new SftpService(props);
    }
}
