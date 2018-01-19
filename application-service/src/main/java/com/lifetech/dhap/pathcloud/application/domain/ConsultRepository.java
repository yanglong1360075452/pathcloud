package com.lifetech.dhap.pathcloud.application.domain;

import com.lifetech.dhap.pathcloud.application.domain.model.Consult;

import java.util.List;

public interface ConsultRepository {
    int deleteByPrimaryKey(Long id);

    int insert(Consult record);

    Consult selectByPrimaryKey(Long id);

    List<Consult> selectAll();

    int updateByPrimaryKey(Consult record);
}