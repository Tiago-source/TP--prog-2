package service;

import java.util.List;

import classes.Skill;
import service.impl.SkillsCSVService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public interface SkillsService {
	
			// CREATE
			public void guardarSkill(Skill skill);
			
			// RETRIEVE
			public List<Skill> fetchSkills();

			public Skill fetchSkillsPorId(int idSkill);
			
			public Skill fetchSkillsPorNome(String skillNome);
			
			// DELETE
			public void apagarSkill(int idSkill);
			
			// UPDATE
			public void atualizarSkill(Skill skill);
			
			
			/*retorna implementação SkillsCSVService, e todos os metodos aqui declarados 
			 * têm que estar criados no ficheiro SkillsCSVService
			 */
			public static SkillsService getNewInstance() {
				return new SkillsCSVService();
		
			}

}
