/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 31.03.18 19:09 Anton Fomchenko 360@in360.ru
 */

package ru.in360.buider.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"ru.in360.db", "ru.in360.util", "ru.in360.builder"})
public class BuilderConfig {

}
