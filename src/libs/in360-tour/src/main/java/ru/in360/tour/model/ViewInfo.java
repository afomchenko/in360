/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import ru.in360.tour.model.constants.FOVType;
import ru.in360.tour.model.constants.LimitView;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "view")
public class ViewInfo {

    private Double hlookat;
    private Double vlookat;
    private Double fov;
    private FOVType fovtype = FOVType.MFOV;
    private Double maxpixelzoom;
    private Double fovmin;
    private Double fovmax;
    private LimitView limitview = LimitView.AUTO;

    public ViewInfo() {
        fov = 70D;
        hlookat = 0.0D;
        vlookat = 0.0D;
        maxpixelzoom = 1.0D;
        fovmin = 50D;
        fovmax = 120D;
    }

    @XmlAttribute
    public double getHlookat() {
        return hlookat;
    }

    public void setHlookat(double hlookat) {
        if (hlookat > 180) {
            this.hlookat = hlookat - 360;
        } else if (hlookat < -180) {
            this.hlookat = hlookat + 360;
        } else {
            this.hlookat = hlookat;
        }
    }

    @XmlAttribute
    public double getVlookat() {
        return vlookat;
    }

    public void setVlookat(double vlookat) {
        if (vlookat > 90D) {
            this.vlookat = vlookat - 180D;
        } else if (vlookat < -90D) {
            this.vlookat = vlookat + 180D;
        } else {
            this.vlookat = vlookat;
        }
    }

    @XmlAttribute
    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        if (fov > 179D) {
            this.fov = 179D;
        } else {
            this.fov = Math.abs(fov);
        }
    }

    @XmlAttribute
    public FOVType getFovtype() {
        return fovtype;
    }

    public void setFovtype(FOVType fovtype) {
        this.fovtype = fovtype;
    }

    @XmlAttribute
    public double getMaxpixelzoom() {
        return maxpixelzoom;
    }

    public void setMaxpixelzoom(double maxpixelzoom) {
        this.maxpixelzoom = Math.abs(maxpixelzoom);
    }

    @XmlAttribute
    public double getFovmin() {
        return fovmin;
    }

    public void setFovmin(double fovmin) {
        if (fovmin > 179D) {
            this.fovmin = 179D;
        } else {
            this.fovmin = Math.abs(fovmin);
        }
    }

    @XmlAttribute
    public double getFovmax() {
        return fovmax;
    }

    public void setFovmax(double fovmax) {
        if (fovmax > 179D) {
            this.fovmax = 179D;
        } else {
            this.fovmax = Math.abs(fovmax);
        }
    }

    @XmlAttribute
    public LimitView getLimitview() {
        return limitview;
    }

    public void setLimitview(LimitView limitview) {
        this.limitview = limitview;
    }
}
