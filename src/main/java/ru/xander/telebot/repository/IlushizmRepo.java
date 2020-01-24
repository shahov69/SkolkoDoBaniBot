package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.xander.telebot.entity.Ilushizm;

import java.util.List;

/**
 * @author Alexander Shakhov
 */
public interface IlushizmRepo extends JpaRepository<Ilushizm, Long> {
    @Query(nativeQuery = true, value = "select * from ilushizm i where i.accepted = true order by random() limit 1")
    Ilushizm findRandom();

    @Query("from Ilushizm i where upper(i.text) like upper(concat('%', :filter, '%')) order by i.id")
    List<Ilushizm> findByText(@Param("filter") String filter);

    @Query("from Ilushizm i where i.accepted = false order by i.id")
    List<Ilushizm> findUnaccepted();
}
