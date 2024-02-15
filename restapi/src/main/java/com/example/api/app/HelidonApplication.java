package com.example.api.app;

import com.example.api.dispatcher.LeadDispatcher;

import com.example.api.dispatcher.StaticContentResource;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("/")
public class HelidonApplication extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(LeadDispatcher.class, StaticContentResource.class);
    }

    public void init(@Observes @Priority(Interceptor.Priority.APPLICATION) @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("SampleApplication init .....");
    }
}
