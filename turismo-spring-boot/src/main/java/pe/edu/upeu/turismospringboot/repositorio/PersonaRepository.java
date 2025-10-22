package pe.edu.upeu.turismospringboot.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.turismospringboot.model.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
