package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.xander.telebot.entity.Kirushizm;

import java.util.List;

/**
 * @author Alexander Shakhov
 */
public interface KirushizmRepo extends JpaRepository<Kirushizm, Long> {
    @Query(nativeQuery = true, value = "select * from kirushizm k where k.accepted = true order by random() limit 1")
    Kirushizm findRandom();

    @Query("from Kirushizm k where upper(k.text) like upper(concat('%', :filter, '%')) order by k.id")
    List<Kirushizm> findByText(@Param("filter") String filter);
}
