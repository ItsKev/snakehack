/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

package com.github.stairch;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * RestInPeace class
 */
public class RestInPeace {
  
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI;
    public static final String protocol;
    public static final Optional<String> host;
    public static final Optional<String> port;
    
    static{
      protocol = "http://";
      host = Optional.ofNullable(System.getenv("HOSTNAME"));
      port = Optional.ofNullable(System.getenv("PORT"));
      BASE_URI = protocol + host.orElse("0.0.0.0") + ":" + port.orElse("4242") + "/" ;
    }
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig().packages("com.github.stairch.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("src/main/java/com/github/stairch/static");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/static");
        System.out.println("Your java starter snake is available at " + BASE_URI + "\nHit ctrl+c to stop it...");
    }
}

