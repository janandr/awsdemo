package com.example.demo;
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

@SpringBootApplication
public class DemoApp
{
/*
  public static void main(String[] args) {
     System.out.println("Connected to Amazon Keyspaces! start");
        Region awsRegion = Region.of("eu-north-1"); // Replace with your AWS Region
        String keyspacesEndpoint = "cassandra.eu-north-1.amazonaws.com"; //  Endpoint (no port)

        // Programmatically configure the driver
        DriverConfigLoader configLoader = DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(5))
                .startProfile("slow")
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(30))
                .endProfile()
                .build();
       /* ProgrammaticDriverConfigLoaderBuilder configLoaderBuilder = DriverConfigLoader.programmaticBuilder()
                //.withString("basic.load-balancing-policy.local-datacenter", awsRegion.id()) // Local DC
                .withString("basic.request.timeout", "30 seconds")
                .withInt("basic.port", 9142)
                .withClass("advanced.ssl-engine-factory.class", "com.amazonaws.cassandra.ssl.DefaultSslEngineFactory") // Use AWS SSL
                .withClass("advanced.auth-provider.class", "com.amazonaws.cassandra.auth.SigV4AuthProvider")  // Use AWS SigV4
                ; 
                      

        try (CqlSession session = new CqlSessionBuilder()
                .withConfigLoader(configLoader)
                .addContactPoint(new InetSocketAddress(keyspacesEndpoint, 9142)) // Endpoint
              .withLocalDatacenter("eu-north-1")
               // .withAuthCredentialsProvider(DefaultCredentialsProvider.create()) // AWS Credentials
                .build()) {

            System.out.println("Connected to Amazon Keyspaces!");
            // Perform operations
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
     System.out.println("Connected to Amazon Keyspaces! end");
    }
/*
        public static void main(String[] args) {
             System.out.println("Hello");
        Region awsRegion = Region.of("eu-north-1"); // Replace with your AWS Region
        String keyspacesEndpoint = "cassandra.eu-north-1.amazonaws.com"; //  Endpoint (no port)

        // Programmatically configure the driver
        ProgrammaticDriverConfigLoaderBuilder configLoaderBuilder = DriverConfigLoader.programmaticBuilder()
                .withString("basic.load-balancing-policy.local-datacenter", awsRegion.id()) // Local DC
                .withString("basic.request.timeout", "30 seconds")
                .withInt("basic.port", 9142)
                .withClass("advanced.ssl-engine-factory.class", "com.amazonaws.cassandra.ssl.DefaultSslEngineFactory") // Use AWS SSL
                .withClass("advanced.auth-provider.class", "com.amazonaws.cassandra.auth.SigV4AuthProvider")  // Use AWS SigV4
                ;

        try (CqlSession session = new CqlSessionBuilder()
                .withConfigLoader(configLoaderBuilder.build())
                .addContactPoint(new InetSocketAddress(keyspacesEndpoint, 9142)) // Endpoint
                .withAuthCredentialsProvider(DefaultCredentialsProvider.create()) // AWS Credentials
                .build()) {

            System.out.println("Connected to Amazon Keyspaces!");
            // Perform operations
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
         System.out.println("Hello2");
    }
    */
        public static CqlSession createSession() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create(); // AWS SDK handles credentials

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra.eu-north-1.amazonaws.com", 9142))
               // .withCloudSecureConnectBundle(Paths.get("path/to/your/secure-connect-bundle.zip")) // Download from AWS Console
                .withAuthCredentials("janandraks-at-250740063307", "Zh+8oMMH+yPhZ0HFOOiDxxfWMWT2Pic8JVqlrzmxvVTebRiFttj+mbG2GXA=") // If using Cassandra authentication
     //           .withSslContext(null) // Or don't call this at all; the driver handles it with the bundle
              //  .withRegion(Region.of("your-aws-region")) // Specify your AWS region
                .build();
    }

    public static void main( String[] args )
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
    /*
    public static void main( String[] args )
    {
        SpringApplication.run(DemoApp.class, args);
         System.out.println("Hello start");
         try (CqlSession session = createSession()) {
            System.out.println("Successfully connected to AWS Serverless Keyspaces!");
            // Perform your Cassandra operations here
        } catch (Exception e) {
            System.out.println("Failed to connect: " + e.getMessage());
            e.printStackTrace();
        }

        /*
       //Use DriverConfigLoader to load your configuration file
        DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
        try (CqlSession session = CqlSession.builder()
                .withConfigLoader(loader)
                .build()) {

            ResultSet rs = session.execute("select * from system_schema.keyspaces");
            Row row = rs.one();
            System.out.println(row.getString("keyspace_name"));
        }  
         
       System.out.println("Hello");
    } */
}