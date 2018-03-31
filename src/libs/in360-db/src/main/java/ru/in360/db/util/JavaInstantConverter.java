/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.util;

import javax.persistence.AttributeConverter;
import java.time.Instant;

public class JavaInstantConverter implements AttributeConverter<Instant, Long> {
    @Override
    public Long convertToDatabaseColumn(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }

    @Override
    public Instant convertToEntityAttribute(Long l) {
        return l != null ? Instant.ofEpochMilli(l) : null;
    }
}
