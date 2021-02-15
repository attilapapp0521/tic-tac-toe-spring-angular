package com.example.tictactoespringangular.repository;

import com.example.tictactoespringangular.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    Game getGameById(Long id);
}
