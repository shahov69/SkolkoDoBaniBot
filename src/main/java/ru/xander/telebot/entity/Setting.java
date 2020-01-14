package ru.xander.telebot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xander.telebot.dto.SettingName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Alexander Shakhov
 */
@Entity
@Data
@NoArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setting_seq")
    @SequenceGenerator(name = "setting_seq", sequenceName = "setting_sequence", allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SettingName name;
    private String value;
    private String defaultValue;
}
