package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Alexander Shakhov
 */
@Entity
@Table(name = "crown")
@Data
@NoArgsConstructor
public class CrownEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crown_seq")
    @SequenceGenerator(name = "crown_seq", sequenceName = "crown_sequence", allocationSize = 1)
    private Long id;
    private String territory;
    private Integer confirmedToday;
    private Integer deathsToday;
    private Integer recoveriesToday;
    private Integer confirmedYesterday;
    private Integer deathsYesterday;
    private Integer recoveriesYesterday;
    private Boolean today;
    private byte[] flag;
}
