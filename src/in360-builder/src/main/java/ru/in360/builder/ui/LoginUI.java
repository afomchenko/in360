/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 18.03.18 22:46 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringUI(path = "/login")
@Title("in360 tour builder login")
@Theme("valo")
public class LoginUI extends UI {

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    private TextField user;
    private PasswordField password;
    private VerticalLayout fields;
    private VerticalLayout errors;
    private Button loginButton = new Button("Login", this::loginButtonClick);

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        user = new TextField("User:");
        user.setWidth("300px");

        password = new PasswordField("Password:");
        password.setWidth("300px");
        password.setValue("");

        errors = new VerticalLayout();
        fields = new VerticalLayout(user, password, loginButton, errors);
        fields.setCaption("Please login to access the application");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setFocusedComponent(user);

        setContent(uiLayout);
    }

    public void loginButtonClick(Button.ClickEvent e) {
        errors.removeAllComponents();
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getValue(), password.getValue());
            Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            //redirect to main application
            getPage().setLocation("/");
        } catch (BadCredentialsException ex) {
            errors.addComponent(new Label("Wrong username/password"));
        }
    }

}