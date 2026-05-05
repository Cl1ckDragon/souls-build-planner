package com.soulsplanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weapons")
@Getter
@Setter
@NoArgsConstructor
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String name;

    @Column(name = "weapon_type")
    private String weaponType;

    private Double weight;

    @JsonIgnore
    @OneToMany(mappedBy = "weapon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BuildWeapon> buildWeapons = new ArrayList<>();
}
