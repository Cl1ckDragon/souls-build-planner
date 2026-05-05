package com.soulsplanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "armour_pieces")
@Getter
@Setter
@NoArgsConstructor
public class ArmourPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String name;

    /** head | chest | hands | legs */
    @Column(nullable = false)
    private String slot;

    private Double weight;

    @JsonIgnore
    @OneToMany(mappedBy = "armourPiece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BuildArmour> buildArmours = new ArrayList<>();
}
