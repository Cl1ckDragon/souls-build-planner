package com.soulsplanner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "build_weapons")
@Getter
@Setter
@NoArgsConstructor
public class BuildWeapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_id", nullable = false)
    private Build build;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    /** 1–3 = right hand, 4–6 = left hand */
    @Column(nullable = false)
    private int slot;

    /** e.g. Fire, Bleed, None */
    @Column(nullable = false)
    private String infusion = "None";
}
