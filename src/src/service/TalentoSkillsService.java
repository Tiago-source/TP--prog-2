package service;

import java.util.List;

import classes.TalentoSkills;
import service.impl.TalentoSkillsCSVService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public interface TalentoSkillsService {


				// CREATE
				public void guardarTalentoSkill(TalentoSkills talentoSkill);
				
				// RETRIEVE
				public List<TalentoSkills> fetchTalentoSkills();

				public TalentoSkills fetchTalentoSkillsPorId(int idTalentoSkill);
				
				public List<TalentoSkills> fetchTalentoSkillsPorIdDoTalento(int idTalento);
				
				public List<TalentoSkills> fetchTalentoSkillsPorNome(String talentoSkillNome);
				
				List<TalentoSkills> fetchTalentoSkillsPorNomeDeSkill(String talentoSkillNomeSkill);
				
				// DELETE
				public void apagarTalentoSkill(int idTalentoSkill);
				
				// UPDATE
				public void atualizarTalentoSkill(TalentoSkills talentoSkill);
				
				
				/*retorna implementação TalentoSkillsCSVService, e todos os metodos aqui declarados 
				 * têm que estar criados no ficheiro TalentoSkillsCSVService
				 */
				public static TalentoSkillsService getNewInstance() {
					return new TalentoSkillsCSVService();
			
				}

				
				
}
