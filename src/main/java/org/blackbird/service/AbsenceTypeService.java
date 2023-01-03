package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.AbsenceType;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AbsenceTypeService implements AbsenceTypeServiceImpl{

    /***
     *
     * @param id
     * @return
     */
    @Override
    public Uni<AbsenceType> findById(long id) {
        return AbsenceType.<AbsenceType>findById(id)
                .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id,"Abswesenheitstyp"));
    }
}
