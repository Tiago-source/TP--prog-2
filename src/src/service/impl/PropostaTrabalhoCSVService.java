package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import service.PropostaTrabalhoService;
import classes.PropostaTrabalho;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public class PropostaTrabalhoCSVService implements PropostaTrabalhoService{
	
	
				// divisor de colunas no ficheiro
				private static final String SEPARADOR = ";";

				// path onde o ficheiro vai ser guardado
				private static final Path ARQUIVO_SAIDA = Paths.get("./propostas.csv");

				// List onde vamos guardar das propostas
				private List<PropostaTrabalho> propostas;

				// formato de data usado no ficheiro
				final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
				
				
				//Metodo que carrega os dados do ficheiro
				public PropostaTrabalhoCSVService() {
					carregaDados();
				}

				
				//Metodo para guardar uma proposta
				@Override
				public void guardarProposta(PropostaTrabalho propostaTrabalho) {
					propostaTrabalho.setIdProposta(ultimoId() + 1);
					propostas.add(propostaTrabalho);
					guardarDados();
				}

				// Metodo para atualizar uma proposta
				@Override
				public void atualizarProposta(PropostaTrabalho propostaTrabalho) {
					PropostaTrabalho propostaAntiga = fetchPropostasPorId(propostaTrabalho.getIdProposta());
					propostaAntiga.setIdEmpresa(propostaTrabalho.getIdEmpresa());
					propostaAntiga.setPropostaEmpresa(propostaTrabalho.getPropostaEmpresa());
					propostaAntiga.setPropostaNome(propostaTrabalho.getPropostaNome());
					propostaAntiga.setPropostaCategoria(propostaTrabalho.getPropostaCategoria());
					propostaAntiga.setPropostaHoras(propostaTrabalho.getPropostaHoras());
					propostaAntiga.setPropostaDescricao(propostaTrabalho.getPropostaDescricao());
					guardarDados();
				}
				
				//Metodo para fazer fetch de todas as propostas
				@Override
				public List<PropostaTrabalho> fetchPropostas() {
					return propostas;
				}


				
				//Metodo para apagar uma proposta
				@Override
				public void apagarProposta(int idProposta) {
					PropostaTrabalho propostaTrabalho = fetchPropostasPorId(idProposta);
					propostas.remove(propostaTrabalho);
					guardarDados();
				}

				
				//Metodo para procurar uma proposta por id
				public PropostaTrabalho fetchPropostasPorId(int idProposta) {
					return propostas.stream().filter(c -> c.getIdProposta() == idProposta).findFirst()
							.orElseThrow(() -> new Error("Utilizador com id inserido não encontrado"));
				}
				
				
				//Metodo para procurar uma proposta por nome
				@Override
				public PropostaTrabalho fetchPropostasPorNome(String propostaNome) {
					return propostas.stream().filter(c -> propostaNome.equals(c.getPropostaNome())).findAny()
							.orElseThrow(() -> new Error("Utilizador com o nome inserido não encontrado"));
				}
				

				// Metodo para guardar os dados da list propostas no ficheiro
				private void guardarDados() {
					StringBuffer sb = new StringBuffer();
					for (PropostaTrabalho c : propostas) {
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
				
				// Metodo que vai buscar o id mais alto e quando uma proposta nova for adicionada faz +1
				private int ultimoId() {
					return propostas.stream().mapToInt(PropostaTrabalho::getIdProposta).max().orElse(0);
				}

				// carrega os dados do ficheiro para a list propostas
				private void carregaDados() {
					try {
						if(!Files.exists(ARQUIVO_SAIDA)) {
							Files.createFile(ARQUIVO_SAIDA);
						}
						propostas = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				
				// transforma uma linha do ficheio num objeto do tipo proposta
				private PropostaTrabalho leLinha(String linha) {
					String colunas[] = linha.split(SEPARADOR);
					int id = Integer.parseInt(colunas[0]);
					PropostaTrabalho propostaTrabalho = new PropostaTrabalho();
					propostaTrabalho.setIdProposta(id);
					propostaTrabalho.setIdEmpresa(Integer.parseInt(colunas[1]));
					propostaTrabalho.setPropostaEmpresa(colunas[2]);
					propostaTrabalho.setPropostaNome(colunas[3]);
					propostaTrabalho.setPropostaCategoria(colunas[4]);
					propostaTrabalho.setPropostaHoras(Integer.parseInt(colunas[5]));
					propostaTrabalho.setPropostaDescricao(colunas[6]);
					
					return propostaTrabalho;
				}
				
				// transforma um objeto proposta numa lina para ser gravada no ficheiro
				private String criaLinha(PropostaTrabalho c) {
					String idStr = String.valueOf(c.getIdProposta());
					String linha = String.join(SEPARADOR, idStr,String.valueOf(c.getIdEmpresa()),c.getPropostaEmpresa() ,c.getPropostaNome(), c.getPropostaCategoria(), String.valueOf(c.getPropostaHoras()) , c.getPropostaDescricao());
					
					return linha;
				}

				
		
}
