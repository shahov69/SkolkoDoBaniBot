package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.Instant;

/**
 * @author Alexander Shakhov
 */
@Entity
@Data
@NoArgsConstructor
public class Kirushizm {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kirushizm_seq")
    @SequenceGenerator(name = "kirushizm_seq", sequenceName = "kirushizm_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "txt", length = 2000)
    private String text;
    private String creator;
    private Instant created;
    private Boolean accepted;
}
