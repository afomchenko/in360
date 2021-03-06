/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 08.04.18 21:23 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.in360.db.model.params.SystemParams;
import ru.in360.db.repo.SystemParamsRepo;
import ru.in360.db.service.SystemParamsService;

import java.util.Optional;

@Service
public class SystemParamsServiceImpl implements SystemParamsService {

    private final SystemParamsRepo systemParamsRepo;

    @Autowired
    public SystemParamsServiceImpl(SystemParamsRepo systemParamsRepo) {
        this.systemParamsRepo = systemParamsRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getParam(String paramKey) {
        return systemParamsRepo.findByParamKey(paramKey).map(SystemParams::getParamValue);
    }

    @Transactional
    @Override
    public void setParameter(String paramKey, String value) {
        systemParamsRepo.findByParamKey(paramKey)
                .ifPresentOrElse(params -> {
                            params.setParamValue(value);
                            systemParamsRepo.save(params);
                        },
                        () -> {
                            SystemParams systemParams = new SystemParams();
                            systemParams.setParamKey(paramKey);
                            systemParams.setParamValue(value);
                            systemParamsRepo.save(systemParams);
                        });

    }
}
