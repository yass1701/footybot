package com.footybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footybot.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // You can add custom queries later
}
