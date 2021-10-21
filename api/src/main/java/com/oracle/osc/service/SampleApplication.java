package com.oracle.osc.service;

import oracle.spectra.context.ApplContextJerseyFilter;
import oracle.ucp.jdbc.PoolDataSource;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptor;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("/")
public class SampleApplication extends Application {

    private PoolDataSource ds;

    @Inject
    public SampleApplication(@Named("ApplicationDBDS") PoolDataSource myDataSource) {
        ds = myDataSource;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(SampleResource.class, ApplContextJerseyFilter.class);
    }

    public void init(@Observes @Priority(Interceptor.Priority.APPLICATION) @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("SampleApplication init .....");
    }
}
