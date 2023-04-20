package fr.simplon.sondage.dao;

import fr.simplon.sondage.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SondageRepository extends JpaRepository<Sondage, Long> {
}
