package com.lakshman.spark.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class HelloWorldSparkFreemarkerStyle {
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void main(String[] args) {

        // initializing freemarker configuration
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldSparkFreemarkerStyle.class, "/");

        // spark stack method to serve on passed route
        Spark.get("/",new Route() {

            public Object handle(final Request request,
                                 final Response response) {
                StringWriter writer = new StringWriter();
                try {

                    // creating a template from configuration and hello.ftl as freemarker template
                    Template helloTemplate = configuration.getTemplate("hello.ftl");

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", "Lakshman");

                    // passing map to template for processing ( where all substation happens)
                    helloTemplate.process(map, writer);

                } catch (Exception e) {
                    logger.error("Failed", e);

                    // spark's method which sends error code 500
                    halt(500);
                }
                return writer;
            }
        });
    }
}
