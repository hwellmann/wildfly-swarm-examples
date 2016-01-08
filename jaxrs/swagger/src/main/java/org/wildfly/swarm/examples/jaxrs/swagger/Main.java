package org.wildfly.swarm.examples.jaxrs.swagger;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.swagger.SwaggerArchive;
import org.wildfly.swarm.swagger.SwaggerFraction;

import java.io.File;

/**
 * @author Lance Ball
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "swagger-app.war");
        deployment.addClass(TimeResource.class);

        SwaggerArchive archive = deployment.as(SwaggerArchive.class);
        archive.register("org.wildfly.swarm.examples.jaxrs.swagger");

        deployment.as(ZipExporter.class).exportTo(new File("beforeAdd.jar"), true);
        deployment.addAllDependencies();
        deployment.as(ZipExporter.class).exportTo(new File("afterAdd.jar"), true);
        container
                .fraction(LoggingFraction.createDefaultLoggingFraction())
                .start()
                .deploy(deployment);
    }
}
