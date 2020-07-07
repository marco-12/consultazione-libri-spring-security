package it.dstech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dstech.models.Libro;


@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
	
	List<Libro> findAllLibri();
	
	
	boolean existsLibroByAutore(String autore);

	boolean existsLibroByTitolo(String titolo);
	
}

