package com.example.api.helidon;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

import com.example.api.components.ObjectQueryParams;
import com.example.api.dispatcher.LeadDispatcher;
import com.example.api.dispatcher.SwaggerUI;
import com.example.api.exception.NotFoundExceptionManager;

@ApplicationScoped
@ApplicationPath("/api")
public class RESTConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(LeadDispatcher.class, SwaggerUI.class, NotFoundExceptionManager.class, ObjectQueryParams.class);
    }

    public void init(@Observes @Priority(Interceptor.Priority.APPLICATION) @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("SampleApplication init .....");
    }
}
