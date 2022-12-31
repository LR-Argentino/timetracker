package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.TimeBooking;

import java.util.List;

public interface TimeBookingServiceImpl {
    Uni<TimeBooking> findById(long id);
    Uni<List<TimeBooking>> getTimeBookings();
    Uni<TimeBooking> createTimeBooking(TimeBooking booking);
    Uni<TimeBooking> updateTimeBooking(TimeBooking booking);
    Uni<Void> deleteTimeBooking(long id);
}
