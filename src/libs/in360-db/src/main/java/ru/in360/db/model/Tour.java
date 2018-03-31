/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "panoramas_in_tour",
            joinColumns = {@JoinColumn(name = "tour")},
            inverseJoinColumns = {@JoinColumn(name = "panorama")})
    private List<Panorama> panoramas;

    public Tour() {
    }

    public Tour(@Size(max = 255) @NotBlank String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Panorama> getPanoramas() {
        return panoramas;
    }

    public void setPanoramas(Collection<Panorama> panoramas) {
        if (this.panoramas == null) {
            this.panoramas = new ArrayList<>(panoramas);
        } else {
            this.panoramas.clear();
            this.panoramas.addAll(panoramas);
        }
    }
}
