package com.rafabene.demo.helidonmicroservice;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.deltaspike.core.api.config.ConfigProperty;


@Path("hello/")
public class CallHelidonMicroservice {

    private Client restClient = ClientBuilder.newBuilder().build();

    @Inject
    @ConfigProperty(name = "helidonURL")
    private String helidonURL;


	@GET
	@Path("/{name}")
	public String callHelidonMicroservice(@PathParam("name") String name){
        return restClient
            .target(helidonURL)
            .path("/greet/" + name)
            .request()
            .get(String.class);
	}

    
}
