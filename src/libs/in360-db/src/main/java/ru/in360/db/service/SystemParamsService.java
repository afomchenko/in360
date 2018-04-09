/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 08.04.18 21:23 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service;

import java.util.Optional;

public interface SystemParamsService {

    Optional<String> getParam(String paramKey);

    void setParameter(String paramKey, String value);
}
