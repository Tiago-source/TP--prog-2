package service;

import java.util.List;

import classes.PropostaTrabalho;
import service.impl.PropostaTrabalhoCSVService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public interface PropostaTrabalhoService {
	
	
				// CREATE
				public void guardarProposta(PropostaTrabalho propostaTrabalho);
				
				// RETRIEVE
				public List<PropostaTrabalho> fetchPropostas();

				public PropostaTrabalho fetchPropostasPorId(int idProposta);
				
				public PropostaTrabalho fetchPropostasPorNome(String propostaNome);
				
				// DELETE
				public void apagarProposta(int idProposta);
				
				// UPDATE
				public void atualizarProposta(PropostaTrabalho propostaTrabalho);
				
				
				/*retorna implementação PropostaTrabalhoCSVService, e todos os metodos aqui declarados 
				 * têm que estar criados no ficheiro PropostaTrabalhoCSVService
				 */
				public static PropostaTrabalhoService getNewInstance() {
					return new PropostaTrabalhoCSVService();
			
				}

}
