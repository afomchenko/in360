/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 18.03.18 21:41 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "";

    private final SpringViewProvider viewProvider;
    private VerticalLayout leftPanel;
    private VerticalLayout rightPanel;

    @Autowired
    public DefaultView(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @PostConstruct
    void init() {
        this.setSizeFull();
        leftPanel = new VerticalLayout();
        leftPanel.setStyleName("Metro");
        leftPanel.setHeight(100.0f, Unit.PERCENTAGE);
        leftPanel.setWidth(300, Unit.PIXELS);
        leftPanel.addComponent(addSelect());
        this.addComponent(leftPanel);
        rightPanel = new VerticalLayout();

        rightPanel.addComponent((Component) viewProvider.getView(PanoramaEditorView.VIEW_NAME));
        this.addComponent(rightPanel);
        rightPanel.setWidth(100, Unit.PERCENTAGE);
        rightPanel.setHeight(100, Unit.PERCENTAGE);

    }

    public NativeSelect<String> addSelect() {
        List<String> data = IntStream.range(0, 100).mapToObj(i -> "Panorama " + i).collect(Collectors.toList());

        NativeSelect<String> panoramaList = new NativeSelect<>("Select an option", data);
        panoramaList.setVisibleItemCount(data.size());
        panoramaList.setEmptySelectionAllowed(false);
        panoramaList.setSelectedItem(data.get(0));
        panoramaList.setWidth(100.0f, Unit.PERCENTAGE);
        panoramaList.setHeight(100.0f, Unit.PERCENTAGE);
        panoramaList.addStyleName(ValoTheme.TABLE_BORDERLESS);

        panoramaList.addValueChangeListener(event -> {
            this.rightPanel.removeAllComponents();
            this.rightPanel.addComponent((Component) viewProvider.getView(PanoramaEditorView.VIEW_NAME));
        });
        return panoramaList;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}