package it.dstech.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.models.Libro;
import it.dstech.repository.LibroRepository;

@Service
public class LibroService {

	@Autowired
    private LibroRepository libroRepository;
	

	public List<Libro> findAllLibri() {
        return libroRepository.findAll();
    }
	
	public boolean addLibro(Libro libro) {

		if (libroRepository.existsLibroByTitolo(libro.getTitolo()) && libroRepository.existsLibroByAutore(libro.getAutore())) {
			
			Libro sovrascriviLibro = libro;

			libroRepository.save(sovrascriviLibro);
		}

		Libro save = libroRepository.save(libro);
		return save != null;

	}

}
