package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.exception.SocietaException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;
@Service
public class SocietaServiceImpl implements SocietaService{
	
	@Autowired
	private SocietaRepository societaRepository;
	// questo mi serve per il findByExample2 che risulta 'a mano'
	// o comunque in tutti quei casi in cui ho bisogno di costruire custom query nel service
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Societa> listAllSocieta() {
		return (List<Societa>)societaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Societa caricaSingolaSocieta(Long id) {
		return societaRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	public void rimuovi(Societa societaInstance) {
		TypedQuery<Societa>query=entityManager.createQuery("select s from Societa s join fetch s.dipendenti d where s.id = ?1", Societa.class);
		query.setParameter(1, societaInstance.getId());
		if(query.getResultList().size()!=0) {
			throw new SocietaException("Errore: Questa società ha dei dipendenti");
		}
		societaRepository.delete(societaInstance);
	}

	@Override
	public List<Societa> findByExample(Societa example) {
		String query = "select s from Societa s where s.id = s.id ";

		if (StringUtils.isNotEmpty(example.getRagioneSociale()))
			query += " and s.ragioneSociale like '%" + example.getRagioneSociale() + "%' ";
		if (StringUtils.isNotEmpty(example.getIndirizzo()))
			query += " and s.indirizzo like '%" + example.getIndirizzo() + "%' ";
		if (example.getDataFondazione() != null)
			query += " and s.dataFondazione >= '" + example.getDataFondazione().toInstant() + "'";

		return entityManager.createQuery(query, Societa.class).getResultList();
	}

	@Override
	public List<Societa> cercaSocietaConDipendenteAventeRalMaggioreDi(int valore) {
		return societaRepository.findAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(valore);
	}


}
