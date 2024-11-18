package com.montonurb.screenmatch_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.montonurb.screenmatch_backend.principal.Principal;
import com.montonurb.screenmatch_backend.repository.SerieRepository;

@SpringBootApplication
public class ScreenmatchBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchBackendApplication.class, args);
	}
}
