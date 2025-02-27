package itmo.service.controllers;


import itmo.utils.mapper.SpaceMarineFilterParamsParser;
import itmo.utils.mapper.SpaceMarineSortParamsParser;
import itmo.utils.mapper.SpaceMarineValidator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JNDIConfig {

    private static final InitialContext jndiContext = getJndiContext();

    public static SpaceMarineFilterParamsParser spaceMarineFilterParamsParser() {
        try {
            return (SpaceMarineFilterParamsParser) jndiContext.lookup("ejb:/utils-0.0.1-SNAPSHOT/SpaceMarineFilterParamsParserImpl!itmo.utils.mapper.SpaceMarineFilterParamsParser");
        } catch (NamingException e) {
            throw new RuntimeException("Error while looking up SpaceMarineFilterParamsParser", e);
        }
    }

    public static SpaceMarineSortParamsParser spaceMarineSortParamsParser() {
        try {
            return (SpaceMarineSortParamsParser) jndiContext.lookup("ejb:/utils-0.0.1-SNAPSHOT/SpaceMarineSortParamsParserImpl!itmo.utils.mapper.SpaceMarineSortParamsParser");
        } catch (NamingException e) {
            throw new RuntimeException("Error while looking up SpaceMarineSortParamsParser", e);
        }
    }

    public static SpaceMarineValidator spaceMarineValidator() {
        try {
            return (SpaceMarineValidator) jndiContext.lookup("ejb:/utils-0.0.1-SNAPSHOT/SpaceMarineValidatorImpl!itmo.utils.mapper.SpaceMarineValidator");
        } catch (NamingException e) {
            throw new RuntimeException("Error while looking up SpaceMarineValidatorImpl", e);
        }
    }

    private static InitialContext getJndiContext() {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

        try {
            return new InitialContext(jndiProps);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

}