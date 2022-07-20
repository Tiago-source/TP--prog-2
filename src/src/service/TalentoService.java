package service;

import java.util.List;

import classes.Talento;
import service.impl.TalentosCSVService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public interface TalentoService {
		
				// CREATE
				public void guardarTalento(Talento talento);
				
				// RETRIEVE
				public List<Talento> fetchTalentos();

				public Talento fetchTalentosPorId(int idTalento);
				
				public Talento fetchTalentosPorNome(String talentoNome);
				
				public List<Talento> fetchTalentosPorNomePais(String talentoPais);
				
				// DELETE
				public void apagarTalento(int idTalento);
				
				// UPDATE
				public void atualizarTalento(Talento talento);
				
				
				/*retorna implementação TalentosCSVService, e todos os metodos aqui declarados 
				 * têm que estar criados no ficheiro TalentosCSVService
				 */
				public static TalentoService getNewInstance() {
					return new TalentosCSVService();
			
				}

				
				
}

