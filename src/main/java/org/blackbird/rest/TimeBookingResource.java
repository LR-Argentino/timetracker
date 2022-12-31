package org.blackbird.rest;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.TimeBooking;
import org.blackbird.service.TimeBookingServiceImpl;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/bookings")
public class TimeBookingResource {
    @Inject
    private TimeBookingServiceImpl bookingService;

    @GET
    public Uni<List<TimeBooking>> getTimeBookings() {
        return bookingService.getTimeBookings();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<TimeBooking> createTimeBooking(TimeBooking booking) {
        return bookingService.createTimeBooking(booking);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<TimeBooking> updateTimeBooking(@PathParam("id") long id, TimeBooking booking) {
        booking.id = id;
        return bookingService.updateTimeBooking(booking);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> deleteTimeBooking(@PathParam("id") long id) {
        return bookingService.deleteTimeBooking(id);
    }
}
