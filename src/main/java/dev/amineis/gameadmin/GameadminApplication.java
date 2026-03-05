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
        playerRepository.save(Player.builder().username("ThunderStrike").country("JP").build());
        playerRepository.save(Player.builder().username("CrimsonViper").country("CA").build());
        playerRepository.save(Player.builder().username("NovaRider").country("GB").build());
        playerRepository.save(Player.builder().username("AquaKnight").country("AU").build());
        playerRepository.save(Player.builder().username("StormBreaker").country("SE").build());
        playerRepository.save(Player.builder().username("VenomPulse").country("MX").build());
        playerRepository.save(Player.builder().username("GhostFalcon").country("IT").build());
        playerRepository.save(Player.builder().username("IronSpecter").country("ES").build());
        playerRepository.save(Player.builder().username("SolarFlare").country("IN").build());
        playerRepository.save(Player.builder().username("PhantomEdge").country("NL").build());
        playerRepository.save(Player.builder().username("CyberWarden").country("SG").build());
        playerRepository.save(Player.builder().username("TitanArrow").country("ZA").build());
        playerRepository.save(Player.builder().username("NeonSamurai").country("NO").build());
        playerRepository.save(Player.builder().username("FrostNova").country("PL").build());
        playerRepository.save(Player.builder().username("RapidClaw").country("AR").build());
        System.out.println("Seeded 20 players.");
      }
    };
  }
}
