/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 13.04.18 21:56 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.config;

public enum SystemParamKeys {
    IMAGE_PROCESSOR_RAW_IMAGE_DIRECTORY("image.processor.raw.image.directory"),
    IMAGE_PROCESSOR_USE_SFTP("image.processor.use.sftp"),
    SFTP_HOST("sftp.host"),
    SFTP_PORT("sftp.port"),
    SFTP_LOGIN("sftp.login"),
    SFTP_PASSWORD("sftp.password"),
    ;

    private String key;

    SystemParamKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

