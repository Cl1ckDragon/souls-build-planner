package com.soulsplanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
public class GameClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String name;

    @Column(name = "base_vigor", nullable = false)
    private int baseVigor;

    @Column(name = "base_mind", nullable = false)
    private int baseMind;

    @Column(name = "base_endurance", nullable = false)
    private int baseEndurance;

    @Column(name = "base_strength", nullable = false)
    private int baseStrength;

    @Column(name = "base_dexterity", nullable = false)
    private int baseDexterity;

    @Column(name = "base_intelligence", nullable = false)
    private int baseIntelligence;

    @Column(name = "base_faith", nullable = false)
    private int baseFaith;

    @Column(name = "base_arcane", nullable = false)
    private int baseArcane;

    @Column(name = "base_level", nullable = false)
    private int baseLevel;

    @JsonIgnore
    @OneToMany(mappedBy = "gameClass", fetch = FetchType.LAZY)
    private List<Build> builds = new ArrayList<>();
}
