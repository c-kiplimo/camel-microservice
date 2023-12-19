package com.collicode.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;


@Component
public class MyFirstTimerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
        //transformation
        //log
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My Constant Message")
                .log("${body}")
//                .transform().constant("Time now is " + LocalDateTime.now())
                .bean(new SimpleLoggingProcessingComponent(),"process")
                .log("${body}")
                .bean(new GetCurrentTimeBean(),"getCurrentTime")
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");

    }
    @Component
    class GetCurrentTimeBean {
        public String getCurrentTime() {
            return "Time now is " + LocalDateTime.now();
        }
    }
    @Component
    class SimpleLoggingProcessingComponent {
        private Logger logger = Logger.getLogger(SimpleLoggingProcessingComponent.class.getName());
      public void process(String message) {
         logger.info("SimpleLoggingProcessingComponent {} "+ message);
      }
    }
    class SimpleLoggingProcessor implements Processor {
        private Logger logger = Logger.getLogger(SimpleLoggingProcessingComponent.class.getName());
        @Override
        public void process(org.apache.camel.Exchange exchange) throws Exception {
            logger.info("SimpleLoggingProcessor {} "+ exchange.getMessage().getBody());
        }
    }
}
