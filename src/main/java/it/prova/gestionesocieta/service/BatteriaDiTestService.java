package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaException;
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
		
		societaService.rimuovi(nuovaSocieta);
		System.out.println("testInserisciNuovaSocieta........OK");
	}
	
	public void testFindByExample() throws ParseException{
		System.out.println("testFindByExample........Inizio");
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("industria" + nowInMillisecondi, "Via " + nowInMillisecondi, new SimpleDateFormat("dd-MM-yyyy").parse("11-01-2000"));
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");
		
		Societa societaExample = new Societa("industria", "Via",new SimpleDateFormat("dd-MM-yyyy").parse("11-01-2000"));
		
		List<Societa> result = societaService.findByExample(societaExample);
		
		if(result.size()!=1) {
			throw new RuntimeException(
					"testFindByExample...failed: non tutti gli elementi sono stati trovati");
		}
		
		societaService.rimuovi(nuovaSocieta);
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
		} catch (SocietaException e) {
			 e.printStackTrace();
		}
		
		dipendenteService.rimuovi(nuovoDipendente);
		societaService.rimuovi(nuovaSocieta);
		System.out.println("testRimuoviSocietà........OK");
	}
	
	public void testInserimentoDipendente() {
		System.out.println("testInserimentoDipendente........Inizio");
		
		Long nowInMillisecondi = new Date().getTime();
		
		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via " + nowInMillisecondi,new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");
		
		Dipendente nuovoDipendente= new Dipendente("Mattia", "Diraimo", 5000);
		if (nuovoDipendente.getId() != null)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: transient object con id valorizzato");
		
		nuovoDipendente.setSocieta(nuovaSocieta);
		
		dipendenteService.inserisciNuovo(nuovoDipendente);
		
		if (nuovoDipendente.getId() == null || nuovoDipendente.getId() < 1)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: inserimento fallito");
		
		dipendenteService.rimuovi(nuovoDipendente);
		societaService.rimuovi(nuovaSocieta);		
		System.out.println("testInserimentoDipendente........OK");		
	}
	
	public void testModificaDipendente() {
		System.out.println("testModificaDipendente........Inizio");
		
		Long nowInMillisecondi = new Date().getTime();
		
		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via " + nowInMillisecondi,new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		
		//INSERISCO LA SOCIETA
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");
		//CREO IL DIPENDENTE
		Dipendente nuovoDipendente= new Dipendente("Mattia", "Diraimo", 5000);
		if (nuovoDipendente.getId() != null)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: transient object con id valorizzato");
		//INSERISCO AL DIPENDENTE LA SOCIETA
		nuovoDipendente.setSocieta(nuovaSocieta);
		//INSERISCO IL DIPENDENTE NEL DB
		dipendenteService.inserisciNuovo(nuovoDipendente);
		if (nuovoDipendente.getId() == null || nuovoDipendente.getId() < 1)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: inserimento fallito");
		
		String cognomeBefore=nuovoDipendente.getCognome();
		
		nuovoDipendente.setCognome("WUCHONG");
		dipendenteService.aggiorna(nuovoDipendente);
		
		Dipendente dipendenteAfter = dipendenteService.caricaSingoloDipendente(nuovoDipendente.getId());
		
		if(dipendenteAfter.getCognome().equals(cognomeBefore)) {
			throw new RuntimeException("testModificaDipendente...failed: AGGIORNAMENTO fallito");
		}
		
		System.out.println("testModificaDipendente........OK");		
	}
	
	public void testListaDistintaSocietaConRalMaggioreDi() {	
		Societa nuovaSocieta = new Societa("Societa Impianti", "Via le pastine",new Date());
		societaService.inserisciNuovo(nuovaSocieta);
		Dipendente nuovoDipendente= new Dipendente("Mattia", "Diraimo", 30001, nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		
		
		Societa nuovaSocieta2 = new Societa("Societa TermoIdraulica", "Via torricola",new Date());
		societaService.inserisciNuovo(nuovaSocieta2);
		Dipendente nuovoDipendente2= new Dipendente("Luca", "Palamara", 30001, nuovaSocieta2);
		dipendenteService.inserisciNuovo(nuovoDipendente2);
		
		
		if(societaService.cercaSocietaConDipendenteAventeRalMaggioreDi(30000).size()!=2) {
			throw new RuntimeException("testListaDistintaSocietaConRalMaggioreDi Fallito");
		}
		
		dipendenteService.rimuovi(nuovoDipendente);
		dipendenteService.rimuovi(nuovoDipendente2);
		societaService.rimuovi(nuovaSocieta);
		societaService.rimuovi(nuovaSocieta2);		
	}
	
	public void testDipendentiPiuAnzianiDelleSocieta() throws ParseException{	
		Date dataFondazioneAzienda = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1980");
		Date dataInput = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990");
		
		Societa nuovaSocieta = new Societa("Societa Impianti", "Via le pastine",dataFondazioneAzienda);
		societaService.inserisciNuovo(nuovaSocieta);
		Dipendente nuovoDipendente= new Dipendente("Mattia", "lucaku", new Date(), 30001, nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		Dipendente nuovoDipendente2= new Dipendente("Franco", "Diraimo",new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1999"), 30001, nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente2);
		
		
		Dipendente risultato = dipendenteService.listaDipendentePiuAnzianoDelleSocietaFondatePrimaDel(dataInput);
		
		if(risultato.getId()!=nuovoDipendente2.getId()) {
			throw new RuntimeException("testDipendentiPiuAnzianiDelleSocieta Fallito");
		}
		
		dipendenteService.rimuovi(nuovoDipendente);
		dipendenteService.rimuovi(nuovoDipendente2);
		societaService.rimuovi(nuovaSocieta);
	}
}
