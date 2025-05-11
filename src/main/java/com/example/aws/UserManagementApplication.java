package com.example.aws;

/*
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// Main class to run the Spring Boot application
@SpringBootApplication
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }
}
*/

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.example.aws.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.query.Query;

import java.security.NoSuchAlgorithmException;

import java.util.UUID;

import static org.springframework.data.cassandra.core.query.Criteria.where;

@SpringBootApplication
public class UserManagementApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementApplication.class);

	public static void main(String[] args) throws NoSuchAlgorithmException, java.io.IOException {

		System.out.println("starting");
		SpringApplication.run(UserManagementApplication.class, args);
		// use Java-based bean metadata to register an instance of a com.datastax.oss.driver.api.core.CqlSession
		CqlSession cqlSession = new AppConfig().session();

		// You can also configure additional options such as TTL, consistency level, and lightweight transactions when using InsertOptions and UpdateOptions
		InsertOptions insertOptions = org.springframework.data.cassandra.core.InsertOptions.builder().
				consistencyLevel(ConsistencyLevel.LOCAL_QUORUM).
				build();

		// The CqlTemplate can be used within a DAO implementation through direct instantiation with a SessionFactory reference or be configured in
		// the Spring container and given to DAOs as a bean reference. CqlTemplate is a foundational building block for CassandraTemplate
		CassandraOperations template = new CassandraTemplate(cqlSession);

		User resultOne = template.selectOne(Query.empty().limit(1), User.class);
		System.out.println(resultOne.toString());

		cqlSession.close();
		System.exit(0);
	}

}
