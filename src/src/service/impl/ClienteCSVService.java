package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.Cliente;
import service.ClienteService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public class ClienteCSVService implements ClienteService {
	
				//Separador de colunas no ficheiro
				private static final String SEPARADOR = ";";

				//Path onde o ficheiro  vai ser criado
				private static final Path ARQUIVO_SAIDA = Paths.get("./clientes.csv");

				//List para guardar os clientes
				private List<Cliente> clientes;

				// formato de data usado no ficheito
				final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

				
				//Metodo carregar os dados do ficheiro
				public ClienteCSVService() {
					carregaDados();
				}

				
				//Metodo para guardar um cliente
				@Override
				public void guardarCliente(Cliente cliente) {
					cliente.setIdCliente(ultimoId() + 1);
					clientes.add(cliente);
					guardarDados();
				}


				//Metodo para atualizar um cliente
				@Override
				public void atualizarCliente(Cliente cliente) {
					Cliente clienteAntigo = fetchClientesPorId(cliente.getIdCliente());
					clienteAntigo.setClienteNome(cliente.getClienteNome());
					clienteAntigo.setClienteEmail(cliente.getClienteEmail());
					guardarDados();
				}

				
				//Metodo para fazer fetch dos clientes previamente carregado para a list clientes
				@Override
				public List<Cliente> fetchClientes() {
					return clientes;
				}

				
				// Metodo para apagar um cliente
				@Override
				public void apagarCliente(int idCliente) {
					Cliente cliente = fetchClientesPorId(idCliente);
					clientes.remove(cliente);
					guardarDados();
				}

				// Metodo para fazer fetch de um cliente por id
				public Cliente fetchClientesPorId(int idCliente) {
					return clientes.stream().filter(c -> c.getIdCliente() == idCliente).findFirst()
							.orElseThrow(() -> new Error("Cliente com id inserido não encontrado"));
				}
				
				
				// Metodo para fazer fetch de um cliente por nome do cliente
				@Override
				public Cliente fetchClientesPorNome(String clienteNome) {
					return clientes.stream().filter(c -> clienteNome.equals(c.getClienteNome())).findAny()
							.orElseThrow(() -> new Error("Cliente com o nome inserido não encontrado"));
				}
				

				// Metodo para guardar os dados na list clientes para o ficheiro
				private void guardarDados() {
					StringBuffer sb = new StringBuffer();
					for (Cliente c : clientes) {
						String linha = criaLinha(c);
						sb.append(linha);
						sb.append(System.getProperty("line.separator"));
					}
					try {
						Files.delete(ARQUIVO_SAIDA);
						Files.write(ARQUIVO_SAIDA, sb.toString().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				
				//Metodo que vai buscar o id mais alto onde depois qaundo criado um novo cliente adcionamos +1
				private int ultimoId() {
					return clientes.stream().mapToInt(Cliente::getIdCliente).max().orElse(0);
				}

				// Carrega os dados do ficheiro para a list clientes
				private void carregaDados() {
					try {
						if(!Files.exists(ARQUIVO_SAIDA)) {
							Files.createFile(ARQUIVO_SAIDA);
						}
						clientes = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				
				// Tranforma uma linha do ficheiro num objeto do tipo cliente
				private Cliente leLinha(String linha) {
					String colunas[] = linha.split(SEPARADOR);
					int id = Integer.parseInt(colunas[0]);
					Cliente cliente = new Cliente();
					cliente.setIdCliente(id);
					cliente.setClienteNome(colunas[1]);
					cliente.setClienteEmail(colunas[2]);
				
					return cliente;
				}
				
				// Transforma um objeto do tipo cliente numa linha para gravar no ficheiro
				private String criaLinha(Cliente c) {
					String idStr = String.valueOf(c.getIdCliente());
					String linha = String.join(SEPARADOR, idStr, c.getClienteNome(), c.getClienteEmail());
					
					return linha;
				}

				

}
