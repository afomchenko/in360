/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 18.03.18 21:41 Anton Fomchenko 360@in360.ru
 */

package ru.in360.buider.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainUI extends UI {


    @Autowired
    SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        this.setNavigator(navigator);

        // Set default view
        NavigationStateManager stateManager = new Navigator.UriFragmentManager(getPage());
        stateManager.setState(DefaultView.VIEW_NAME);

    }
}