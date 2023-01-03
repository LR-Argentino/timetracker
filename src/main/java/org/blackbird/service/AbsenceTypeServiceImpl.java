package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.AbsenceType;

public interface AbsenceTypeServiceImpl {
    Uni<AbsenceType> findById(long id);
}
