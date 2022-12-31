package org.blackbird.rest;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Notification;
import org.blackbird.service.NotificationServiceImpl;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/notifications")
public class NotificationResource {
    @Inject
    private NotificationServiceImpl notificationService;

    @GET
    public Uni<List<Notification>> getNotifications() {
        return notificationService.getNotifications();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Notification> createNotification(Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<Notification> updateNotification(@PathParam("id") long id, Notification notification) {
        notification.id = id;
        return notificationService.updateNotification(notification);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> deleteNotification(@PathParam("id") long id) {
        return notificationService.deleteNotification(id);
    }

    @GET
    @Path("{id}")
    public Uni<Notification> getNotificationById(@PathParam("id") long id) {
        return notificationService.findById(id);
    }
}
