/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 04.04.18 21:47 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import java.util.Optional;

public interface PopupWindow {

//    Optional<Float> getPopupWindowHeight();

//    Optional<Float> getPopupWindowWidth();

    default Optional<Boolean> isPopupWindowCentered() {
        return Optional.of(true);
    }

    default Optional<Boolean> isPopupWindowModal() {
        return Optional.of(true);
    }

    default Optional<Boolean> isPopupWindowResizable() {
        return Optional.of(false);
    }
}
