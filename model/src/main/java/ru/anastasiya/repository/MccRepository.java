package ru.anastasiya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anastasiya.entity.Mcc;

@Repository
public interface MccRepository extends JpaRepository<Mcc, Long> {
    Mcc findByMcc(Integer mcc);
}
