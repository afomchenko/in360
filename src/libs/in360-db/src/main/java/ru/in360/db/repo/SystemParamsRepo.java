/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 08.04.18 21:20 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.in360.db.model.params.SystemParams;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemParamsRepo extends JpaRepository<SystemParams, String> {

    List<SystemParams> findAll();

    Optional<SystemParams> findByParamKey(@Param("paramKey") String paramKey);
}
