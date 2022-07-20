package service;

import java.util.List;

import classes.Cliente;
import service.impl.ClienteCSVService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public interface ClienteService {
		
				// CREATE
				public void guardarCliente(Cliente cliente);
				
				// RETRIEVE
				public List<Cliente> fetchClientes();

				public Cliente fetchClientesPorId(int idCliente);
				
				public Cliente fetchClientesPorNome(String clienteNome);
				
				// DELETE
				public void apagarCliente(int idCliente);
				
				// UPDATE
				public void atualizarCliente(Cliente cliente);
				
				
				/*retorna implementação ClienteCSVService, e todos os metodos aqui declarados 
				 * têm que estar criados no ficheiro ClienteCSVService
				 */
				public static ClienteService getNewInstance() {
					return new ClienteCSVService();
			
				}

}
