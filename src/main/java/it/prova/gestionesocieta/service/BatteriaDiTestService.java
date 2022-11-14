package it.prova.gestionesocieta.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
@Service
public class BatteriaDiTestService {
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private SocietaService societaService;
	
	public void testInserisciNuovaSocieta() {
		System.out.println("testInserisciNuovaSocieta........Inizio");
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via " + nowInMillisecondi,new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");

		System.out.println("testInserisciNuovaSocieta........OK");
	}
	
	public void testFindByExample() {
		System.out.println("testFindByExample........Inizio");
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via " + nowInMillisecondi,new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");
		
		if(societaService.findByExample(nuovaSocieta).size()!=1) {
			throw new RuntimeException(
					"testFindByExample...failed: non tutti gli elementi sono stati trovati");
		}
		System.out.println("testFindByExample........OK");
	}
	
	public void testRimuoviSocietà() {
		System.out.println("testRimuoviSocietà........Inizio");
		
		Long nowInMillisecondi = new Date().getTime();
		
		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via " + nowInMillisecondi,new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");
		
		
		Dipendente nuovoDipendente= new Dipendente("Luca", "La rocca", 500);
		if (nuovoDipendente.getId() != null)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: transient object con id valorizzato");
		
		nuovoDipendente.setSocieta(nuovaSocieta);
		
		dipendenteService.inserisciNuovo(nuovoDipendente);
		if (nuovoDipendente.getId() == null || nuovoDipendente.getId() < 1)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: inserimento fallito");

		try {
			societaService.rimuovi(nuovaSocieta);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		System.out.println("testRimuoviSocietà........OK");
	}
	
	

}
