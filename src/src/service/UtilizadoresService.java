package service;

import java.util.List;

import login.Utilizador;
import service.impl.UtilizadoresCSVService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public interface UtilizadoresService {
	
		// CREATE
		public void guardarUtilizador(Utilizador utilizador);
		
		// RETRIEVE
		public List<Utilizador> fetchUtilizadores();

		public Utilizador fetchUtilizadoresPorId(int id);
		
		public Utilizador fetchUtilizadoresPorNome(String nome,String passwd);
		
		// DELETE
		public void apagarUtilizador(int id);
		
		// UPDATE
		public void atualizarUtilizador(Utilizador utilizador);
		
		
		/*retorna implementação UtilizadoresCSVService, e todos os metodos aqui declarados 
		 * têm que estar criados no ficheiro UtilizadoresCSVService
		 */
		public static UtilizadoresService getNewInstance() {
			return new UtilizadoresCSVService();
	
		}

}
