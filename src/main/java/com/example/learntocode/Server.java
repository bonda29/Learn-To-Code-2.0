package com.example.learntocode;

import com.example.learntocode.models.DotEnv;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import static java.util.Arrays.stream;

@Log4j2
@SpringBootApplication
@ConfigurationPropertiesScan
public class Server {


    public static void main(final String[] args) {
        dotEnvSafeCheck();

        SpringApplication.run(Server.class, args);
    }

    private static void dotEnvSafeCheck() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        stream(DotEnv.values())
                .map(DotEnv::name)
                .filter(varName -> dotenv.get(varName, "").isEmpty())
                .findFirst()
                .ifPresent(varName -> {
                    log.error("[Fatal] Missing or empty environment variable: {}", varName);

                    System.exit(1);
                });
    }

}
