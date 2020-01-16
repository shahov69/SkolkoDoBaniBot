package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Alexander Shakhov
 */
@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@ToString
public class User {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isBot;
    private String langCode;
    private Integer cityId;
}
