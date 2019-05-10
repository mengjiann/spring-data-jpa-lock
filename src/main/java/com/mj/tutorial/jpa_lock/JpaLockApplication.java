package com.mj.tutorial.jpa_lock;

import com.mj.tutorial.jpa_lock.task.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JpaLockApplication implements CommandLineRunner {

	@Autowired
	private Tasks tasks;

	public static void main(String[] args) {
		SpringApplication.run(JpaLockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ExecutorService es = Executors.newFixedThreadPool(2);

		//user 1, reader
		es.execute(tasks::runUser1Transaction);

		//user 2, writer
		es.execute(tasks::runUser2Transaction);
	}
}
