package com.soulsplanner.repository;

import com.soulsplanner.entity.ArmourPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArmourPieceRepository extends JpaRepository<ArmourPiece, Long> {
    List<ArmourPiece> findByGameId(Long gameId);
    List<ArmourPiece> findByGameIdAndSlot(Long gameId, String slot);
}
