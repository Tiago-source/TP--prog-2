package service;

import java.util.List;

import classes.TalentoExperiencia;
import service.impl.TalentoExperienciaCSVService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public interface TalentoExperienciaService {
	

			// CREATE
			public void guardarTalentoExperiencia(TalentoExperiencia talentoExperiencia);
			
			// RETRIEVE
			public List<TalentoExperiencia> fetchTalentoExperiencias();

			public TalentoExperiencia fetchTalentoExperienciasPorId(int idTalentoExperiencia);
			
			public List<TalentoExperiencia> fetchTalentoExperienciasPorIdDoTalento(int idTalento);
			
			public List<TalentoExperiencia> fetchTalentoExperienciasPorNome(String talentoNome);
			
			List<TalentoExperiencia> fetchTalentoExperienciasPorNomeDeExperiencia(String talentoExperienciaEmpresa);
			
			// DELETE
			public void apagarTalentoExperiencia(int idTalentoExperiencia);
			
			// UPDATE
			public void atualizarTalentoExperiencia(TalentoExperiencia talentoExperiencia);
			
			
			/*retorna implementação TalentoExperienciaCSVService, e todos os metodos aqui declarados 
			 * têm que estar criados no ficheiro TalentoExperienciaCSVService
			 */
			public static TalentoExperienciaService getNewInstance() {
				return new TalentoExperienciaCSVService();
		
			}

}
