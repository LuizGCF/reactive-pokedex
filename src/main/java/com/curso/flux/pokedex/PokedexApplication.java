package com.curso.flux.pokedex;

import com.curso.flux.pokedex.model.Pokemon;
import com.curso.flux.pokedex.repository.PokedexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations,
						   PokedexRepository repository){
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon(null, "Gligar", "Ground", "Earthquake", 6.09),
					new Pokemon(null, "Blissey", "Normal", "Seismic Toss", 90.05))
					.flatMap(repository::save);

			pokemonFlux.thenMany(repository.findAll())
					.subscribe(System.out::println);

		};
	}

}
