package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.model.Ceasa;
import online.guessersoftware.casadoagricultorapi.webservice.repository.CeasaRepository;

@Service
public class CeasaService {

	@Autowired
	private CeasaRepository ceasaRepository;

	public Ceasa getCeasaByName(String name) {
		return ceasaRepository.findByName(name);
	}

}
