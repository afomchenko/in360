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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "panorama")
public class Panorama {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "title")
    @Size(max = 255)
    private String title;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "heading")
    private Double heading;

    @Column(name = "hlookat")
    private Double hlookat;

    @Column(name = "vlookat")
    private Double vlookat;

    @Column(name = "fov")
    private Double fov = 70.0D;

    @Column(name = "limitview")
    @Size(max = 40)
    private String limitview;

    @OneToOne(mappedBy = "panorama", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @Column(name = "preview_url")
    @Size(max = 255)
    private String previewUrl;

    @Column(name = "thumb_url")
    @Size(max = 255)
    private String thumbUrl;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "created_user")
    @Size(max = 80)
    private String createdUser;

    @ManyToMany(mappedBy = "panoramas")
    private Set<Tour> tours;

    @Column(name = "allow_auto_hotspots")
    private Boolean allowAutoHotspots = true;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getHlookat() {
        return hlookat;
    }

    public void setHlookat(Double hlookat) {
        this.hlookat = hlookat;
    }

    public Double getVlookat() {
        return vlookat;
    }

    public void setVlookat(Double vlookat) {
        this.vlookat = vlookat;
    }

    public Double getFov() {
        return fov;
    }

    public void setFov(Double fov) {
        this.fov = fov;
    }

    public String getLimitview() {
        return limitview;
    }

    public void setLimitview(String limitview) {
        this.limitview = limitview;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Boolean getAllowAutoHotspots() {
        return allowAutoHotspots;
    }

    public void setAllowAutoHotspots(Boolean allowAutoHotspots) {
        this.allowAutoHotspots = allowAutoHotspots;
    }

    public Set<Tour> getTours() {
        return tours;
    }
}
