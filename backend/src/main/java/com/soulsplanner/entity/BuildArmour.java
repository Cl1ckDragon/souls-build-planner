package com.soulsplanner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "build_armour")
@Getter
@Setter
@NoArgsConstructor
public class BuildArmour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_id", nullable = false)
    private Build build;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armour_piece_id", nullable = false)
    private ArmourPiece armourPiece;
}
