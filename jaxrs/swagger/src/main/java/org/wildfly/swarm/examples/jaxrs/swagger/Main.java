package org.wildfly.swarm.examples.jaxrs.swagger;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.swagger.SwaggerFraction;

/**
 * @author Bob McWhirter
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "swagger-app.war");
        deployment.addClass(TimeResource.class);
        deployment.addAllDependencies();
        container
                .fraction(LoggingFraction.createDebugLoggingFraction())
                .fraction(new SwaggerFraction())
                .start()
                .deploy(deployment);
    }
}
