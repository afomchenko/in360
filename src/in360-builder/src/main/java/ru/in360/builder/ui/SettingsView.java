/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 31.03.18 17:16 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import javax.annotation.PostConstruct;

@SpringView(name = SettingsView.VIEW_NAME)
public class SettingsView extends VerticalLayout implements PopupWindowView {
    public static final String VIEW_NAME = "settings";
    private Window window;

    @PostConstruct
    void init() {
        addComponent(new Label("Welcome to in360 settings"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    @Override
    public void setParentWindow(Window window) {
        this.window = window;
    }
}