package com.aluracursos.screenmatch.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie,Long>{
    Optional<Serie>findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);

    @Query( value = "select * from SERIES where series.total_temporadas <=6 and series.evaluacion >= 7.5", nativeQuery=true)
    List<Serie> seriesPorTemporadaYEvaluacion(); 

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :nombreEpisodio, '%'))")
    List<Episodio> episodiosPorNombre(@Param("nombreEpisodio") String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s= :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);
}
