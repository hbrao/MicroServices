package com.example.api;


import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.config.MetaConfig;
import io.helidon.config.spi.ConfigSource;
import io.helidon.microprofile.server.Server;
import java.util.function.Supplier;

import static io.helidon.config.ConfigSources.classpath;

/** Main method simulating trigger of main method of the server. */
public final class Main {

    private Main() {}

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {

        Config config = buildHelidonConfig();
        registerDataSources();
        Server server = startServer(config);
        System.out.println("Started server on http://" + server.host() + ":" + server.port());
    }


    /**
     * Start the server.
     *
     * @return the created {@link Server} instance
     */
    public static Server startServer(Config config) {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server
                .builder()
                .config(config)
                .build()
                .start();
    }

    /**
     * This method is used to configure the Config object from the default config file
     *
     * @return Config
     */
    private static io.helidon.config.Config buildHelidonConfig() {
        return MetaConfig.config(io.helidon.config.Config.create(getSources()));
    }

    private static Supplier<? extends ConfigSource>[] getSources() {
        return new Supplier[] {
                classpath("meta-config.yaml").build(),
                () -> classpath("META-INF/microprofile-config.properties").build(),
                ConfigSources::environmentVariables
        };
    }

    private static void registerDataSources() {
        System.out.println("Registering data sources ....");
    }
}
