package com.saber;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/messaging")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    ProducerTemplate producerTemplate;

    private static final Logger log = LoggerFactory.getLogger(MovieResource.class);

    @POST
    @Path("/movie")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response camelRabbitSendMovie(Movie movie){

        Exchange exchangeResponse =producerTemplate.send("direct:sample-camel-movie", exchange -> {
           exchange.getMessage().setBody(movie);
        });

        Integer statusCode = exchangeResponse.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE,Integer.class);

        ResponseDto responseDto = exchangeResponse.getMessage().getBody(ResponseDto.class);

        log.info("Receive body with statusCode {} === {} ",statusCode,responseDto);

        return Response.ok(responseDto).build();
    }
}
