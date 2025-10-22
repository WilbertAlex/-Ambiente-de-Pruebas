package pe.edu.upeu.turismospringboot.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.turismospringboot.model.entity.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    long count();
}
