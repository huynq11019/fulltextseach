package com.example.hibernatesearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@Slf4j

@SpringBootApplication
public class HibernateSearchApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HibernateSearchApplication.class);
        Environment env = application.run(args).getEnvironment();
//        SpringApplication.run(HibernateSearchApplication.class, args);
        logApplicationStartup(env);
    }
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";

        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }

        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}{}\n\t"
                        + "External: \t{}://{}:{}{}\n\t"
                        + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), protocol, serverPort, contextPath,
                protocol, hostAddress,

                serverPort, contextPath, env.getActiveProfiles());

//        log.info(Labels.getLabels(LabelKey.MESSAGE_SERVER_HAS_BEEN_STARTED));
    }

}
