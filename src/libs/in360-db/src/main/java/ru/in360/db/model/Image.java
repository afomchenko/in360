/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type")
    @NotBlank
    @Size(max = 40)
    private String type;

    @Column(name = "multires")
    private Boolean multires;

    @Column(name = "tilesize")
    private Long tilesize;

    @Column(name = "progressive")
    private Boolean progressive;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("width ASC")
    private Set<ImageLevel> multiresLevels;

    @OneToMany(mappedBy = "imageMobile", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("width ASC")
    private Set<ImageLevel> mobileLevels;

    @Column(name = "sourceimage")
    private String sourceImagePath;

    @Column(name = "complete")
    private Boolean complete;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getMultires() {
        return multires;
    }

    public void setMultires(Boolean multires) {
        this.multires = multires;
    }

    public Long getTilesize() {
        return tilesize;
    }

    public void setTilesize(Long tilesize) {
        this.tilesize = tilesize;
    }

    public Boolean getProgressive() {
        return progressive;
    }

    public void setProgressive(Boolean progressive) {
        this.progressive = progressive;
    }

    public List<ImageLevel> getMultiresLevels() {
        return new ArrayList<>(multiresLevels);
    }

    public void setMultiresLevels(List<ImageLevel> multiresLevels) {
        if (this.multiresLevels == null) {
            this.multiresLevels = new LinkedHashSet<>();
        }
        this.multiresLevels.addAll(multiresLevels);
    }

    public List<ImageLevel> getMobileLevels() {
        return new ArrayList<>(mobileLevels);
    }

    public void setMobileLevels(List<ImageLevel> mobileLevels) {
        if (this.mobileLevels == null) {
            this.mobileLevels = new LinkedHashSet<>();
        }
        this.mobileLevels.addAll(mobileLevels);
    }

    public String getSourceImagePath() {
        return sourceImagePath;
    }

    public void setSourceImagePath(String sourceImagePath) {
        this.sourceImagePath = sourceImagePath;
    }

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
