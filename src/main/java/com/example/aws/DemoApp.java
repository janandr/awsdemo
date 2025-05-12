package com.example.aws;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
// add the following imports to your project
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Paths;
import java.net.InetSocketAddress;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import java.time.Duration;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApp
{
    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }

    public static CqlSession createSession() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create(); // AWS SDK handles credentials

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra.eu-north-1.amazonaws.com", 9142))
                .withAuthCredentials("janandraks-at-250740063307", "Zh+8oMMH+yPhZ0HFOOiDxxfWMWT2Pic8JVqlrzmxvVTebRiFttj+mbG2GXA=") // If using Cassandra authentication
                .build();
    }

    public static void getAws()
    {
        //Use DriverConfigLoader to load your configuration file
        DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
        try (CqlSession session = CqlSession.builder()
                .withConfigLoader(loader)
                .build()) {

            ResultSet rs = session.execute("select * from my_keyspace.users");
            Row row = rs.one();
            System.out.println(row);
            System.out.println(row.getString("username"));
        }
    }
  
}
