package org.blackbird.rest;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.AbsenceType;
import org.blackbird.service.AbsenceTypeServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/api/v1/absences")
public class AbsenceTypeResource {
    @Inject
    private AbsenceTypeServiceImpl absenceTypeService;
    @GET
    public Uni<List<AbsenceType>> getAbsences() {
        return AbsenceType.listAll();
    }

    @GET
    @Path("{id}")
    public Uni<AbsenceType> getAbsenceById(@PathParam("id") long id) {
        return absenceTypeService.findById(id);
    }
}
