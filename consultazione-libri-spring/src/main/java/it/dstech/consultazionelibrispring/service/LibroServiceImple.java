package it.dstech.consultazionelibrispring.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.consultazionelibrispring.models.Libro;
import it.dstech.consultazionelibrispring.repository.LibroRepository;

@Service
public class LibroServiceImple implements LibroService {
	@Autowired
    private LibroRepository libroRepository;
	
	@Override
	public List<Libro> findAllLibri() {
        return libroRepository.findAll();
    }
	
	@Override
	public boolean addLibro(Libro libro) {

		if (libroRepository.existsLibroByTitolo(libro.getTitolo()) && libroRepository.existsLibroByAutore(libro.getAutore())) {
			
			Libro sovrascriviLibro = libro;

			libroRepository.save(sovrascriviLibro);
		}

		Libro save = libroRepository.save(libro);
		return save != null;

	}

}
