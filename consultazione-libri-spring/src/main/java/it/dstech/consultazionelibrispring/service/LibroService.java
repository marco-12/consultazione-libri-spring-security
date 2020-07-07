package it.dstech.consultazionelibrispring.service;

import java.util.List;

import it.dstech.consultazionelibrispring.models.Libro;

public interface LibroService {

	public List<Libro> findAllLibri();
	
	public boolean addLibro(Libro libro);
	
	
	
}
