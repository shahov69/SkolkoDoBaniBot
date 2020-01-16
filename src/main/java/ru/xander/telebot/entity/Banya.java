package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author Alexander Shakhov
 */
@Entity
@Data
@NoArgsConstructor
public class Banya {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banya_seq")
    @SequenceGenerator(name = "banya_seq", sequenceName = "banya_sequence", allocationSize = 1)
    private Long id;
    @NotNull
    private Long chatId;
    private String chatName;
    private Instant start;
    private Instant finish;
    private String picture;

}
