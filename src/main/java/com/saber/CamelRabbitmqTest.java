package com.saber;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CamelRabbitmqTest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("platform-http:/rabbitmq")
                .log("send data to rabbitmq ")
                .setBody().simple("Hello Saber lee")
                .to("rabbitmq:camel-rabbitmq?queue=out&routingKey=camel-key&connectionFactory=#connectionFactory");


        from("direct:sample-camel-movie")
                .log("Request body with body ===> ${in.body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("rabbitmq:camel-rabbitmq?queue=out&routingKey=camel-key&connectionFactory=#connectionFactory")
                .process(exchange -> {
                    String body = exchange.getMessage().getBody(String.class);
                    ResponseDto responseDto = new ResponseDto();

                    responseDto.setBody(body);
                    responseDto.setCode(200);
                    responseDto.setMessage("message Publish successfully");
                    exchange.getMessage().setBody(responseDto);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)) ;



        from("rabbitmq:camel-rabbitmq?queue=in&routingKey=camel-key&connectionFactory=#connectionFactory")
                .log("receive message from rabbitmq ===> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


    }
}
