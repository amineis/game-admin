package dev.amineis.gameadmin;

import dev.amineis.gameadmin.entity.Player;
import dev.amineis.gameadmin.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameadminApplication.class, args);
    }

    @Bean
    CommandLineRunner seedPlayers(PlayerRepository playerRepository) {
        return args -> {
            if (playerRepository.count() == 0) {
                playerRepository.save(Player.builder().username("ShadowHunter").country("US").build());
                playerRepository.save(Player.builder().username("NightWolf").country("DE").build());
                playerRepository.save(Player.builder().username("IcePhoenix").country("FR").build());
                playerRepository.save(Player.builder().username("BlazeMaster").country("BR").build());
                playerRepository.save(Player.builder().username("SilentStorm").country("KR").build());
                System.out.println("Seeded 5 players.");
            }
        };
    }
}
