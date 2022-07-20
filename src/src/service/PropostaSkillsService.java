package service;

import java.util.List;

import classes.PropostaSkills;
import service.impl.PropostaSkillsCSVService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public interface PropostaSkillsService {
	
				// CREATE
				public void guardarSkillProposta(PropostaSkills propostaSkill);
				
				// RETRIEVE
				public List<PropostaSkills> fetchSkillsProposta();

				public PropostaSkills fetchSkillsPropostaPorId(int idSkillProposta);
				
				public PropostaSkills fetchSkillsPropostaPorNome(String skillPropostaNome);
				
				public List<PropostaSkills> fetchPropostaSkillsPorIdProposta(int idProposta);
				
				// DELETE
				public void apagarSkillProposta(int idSkillProposta);
				
				// UPDATE
				public void atualizarSkillProposta(PropostaSkills propostaSkill);
				
				/*retorna implementação PropostaSkillsCSVService, e todos os metodos aqui declarados 
				 * têm que estar criados no ficheiro PropostaSkillsCSVService
				 */
				public static PropostaSkillsService getNewInstance() {
					return new PropostaSkillsCSVService();
			
				}

}
