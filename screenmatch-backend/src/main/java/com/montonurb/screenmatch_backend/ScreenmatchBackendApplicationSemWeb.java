//package com.montonurb.screenmatch_backend;
//
//import com.montonurb.screenmatch_backend.principal.Principal;
//import com.montonurb.screenmatch_backend.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchBackendApplicationSemWeb implements CommandLineRunner {
//	@Autowired
//	SerieRepository serieRepository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchBackendApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Principal principal = new Principal(serieRepository);
//		principal.exibeMenu();
//	}
//
//}
