package com.footybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.footybot.model.Player;
import com.footybot.repository.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {
        Optional<Player> existing = playerRepository.findById(id);
        if (existing.isPresent()) {
            Player player = existing.get();
            player.setName(updatedPlayer.getName());
            player.setPosition(updatedPlayer.getPosition());
            player.setAge(updatedPlayer.getAge());
            player.setTeam(updatedPlayer.getTeam());
            return playerRepository.save(player);
        }
        return null;
    }
}
