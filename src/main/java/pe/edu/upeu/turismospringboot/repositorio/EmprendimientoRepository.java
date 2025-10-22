package pe.edu.upeu.turismospringboot.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.turismospringboot.model.entity.Emprendimiento;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    long count();

    @Query("SELECT fc.categoria.nombre, COUNT(e) FROM Emprendimiento e JOIN e.familiaCategoria fc GROUP BY fc.categoria.nombre")
    List<Object[]> countByCategoria();

    Optional<Emprendimiento> findByNombre(String nombre);

    @Query("SELECT e FROM Emprendimiento e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Emprendimiento> buscarPorNombre(@Param("nombre") String nombre);
}
