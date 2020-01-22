package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Alexander Shakhov
 */
@Entity
@Data
@NoArgsConstructor
public class Omen {
    @Id
    private Integer day;
    private String title;
    @Column(length = 2000)
    private String allTitles;
    @Column(length = 2000)
    private String description;
    @Column(length = 2000)
    private String omens;
    @Column(length = 2000)
    private String names;
    @Column(length = 2000)
    private String dreams;
    @Column(length = 2000)
    private String talismans;
}
