package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.PropostaSkills;
import service.PropostaSkillsService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class PropostaSkillsCSVService implements PropostaSkillsService{

				//Separador de colunas no ficheiro
				private static final String SEPARADOR = ";";

				//Path onde o ficheiro vai ser criado
				private static final Path ARQUIVO_SAIDA = Paths.get("./skillsProposta.csv");

				// List que vai conter os dados
				private List<PropostaSkills> skillsProposta;

				// formato de data usado no ficheiro
				final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

				//Metodo que carrefa os dados do ficheiro para a list
				public PropostaSkillsCSVService() {
					carregaDados();
				}

				
				
				//Metodo para guardar uma skill associada a uma proposta
				@Override
				public void guardarSkillProposta(PropostaSkills propostaSkills) {
					propostaSkills.setIdSkillProposta(ultimoId() + 1);
					skillsProposta.add(propostaSkills);
					guardarDados();
				}

				
				//Metodo para atualizar uma skill associada a uma proposta
				@Override
				public void atualizarSkillProposta(PropostaSkills skill) {
					PropostaSkills skillAntiga = fetchSkillsPropostaPorId(skill.getIdSkillProposta());
					skillAntiga.setSkillNome(skill.getSkillNome());
					skillAntiga.setSkillAnos(skill.getSkillAnos());
					skillAntiga.setIdProposta(skill.getIdProposta());
					guardarDados();
				}

				//Metodo para fazer fetch das skills guardadas na list skillsProposta
				@Override
				public List<PropostaSkills> fetchSkillsProposta() {
					return skillsProposta;
				}


				// Metodo para apagar uma skill associada a uma proposta
				@Override
				public void apagarSkillProposta(int idSkill) {
					PropostaSkills skill = fetchSkillsPropostaPorId(idSkill);
					skillsProposta.remove(skill);
					guardarDados();
				}

				// Metodo para procurar uma skill associada a uma proposta por id
				public PropostaSkills fetchSkillsPropostaPorId(int idSkill) {
					return skillsProposta.stream().filter(c -> c.getIdSkillProposta() == idSkill).findFirst()
							.orElseThrow(() -> new Error("Proposta com id inserido não encontrado"));
				}
				
				
				// Metodo para procurar uma skill associada a uma proposta por nome
				@Override
				public PropostaSkills fetchSkillsPropostaPorNome(String skillNome) {
					return skillsProposta.stream().filter(c -> skillNome.equals(c.getSkillNome())).findAny()
							.orElseThrow(() -> new Error("Utilizador com o nome inserido não encontrado"));
				}
				
				
				// Metodo para procurar uma skill associada a uma proposta por id da proposta
				@Override
				public List<PropostaSkills> fetchPropostaSkillsPorIdProposta(int idProposta) {
					return skillsProposta.stream().filter(c -> c.getIdProposta() == idProposta).collect(Collectors.toList());
							
				}
				
				

				// Metodo para guardar os dados da list skillsProposta para o ficheiro
				private void guardarDados() {
					StringBuffer sb = new StringBuffer();
					for (PropostaSkills c : skillsProposta) {
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
				
				
				// Metodo que vai buscar o id mais alto e quando uma nova skill é adicionada faz +1
				private int ultimoId() {
					return skillsProposta.stream().mapToInt(PropostaSkills::getIdSkillProposta).max().orElse(0);
				}

				// carrega os dados do ficheiro para a list skillsProposta
				private void carregaDados() {
					try {
						if(!Files.exists(ARQUIVO_SAIDA)) {
							Files.createFile(ARQUIVO_SAIDA);
						}
						skillsProposta = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				
				// transforma uma linha do ficheiro num objeto do tipo SkillsProposta
				private PropostaSkills leLinha(String linha) {
					String colunas[] = linha.split(SEPARADOR);
					int id = Integer.parseInt(colunas[0]);
					PropostaSkills propostaSkills = new PropostaSkills();
					propostaSkills.setIdSkillProposta(id);
					propostaSkills.setSkillNome(colunas[1]);
					propostaSkills.setSkillAnos(colunas[2]);
					propostaSkills.setIdProposta(Integer.parseInt(colunas[3]));
					
					return propostaSkills;
				}
				
				// transforma um objeto do tipo SkillsProposta numa linha para ser  gravada no ficheiro
				private String criaLinha(PropostaSkills c) {
					String idStr = String.valueOf(c.getIdSkillProposta());
					String linha = String.join(SEPARADOR, idStr, c.getSkillNome(), c.getSkillAnos(), String.valueOf(c.getIdProposta()));
					
					return linha;
				}

				
		
	
	
}
