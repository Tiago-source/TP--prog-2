package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.Skill;
import service.SkillsService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/



public class SkillsCSVService implements SkillsService{

			// separador de colunas no ficheiro
			private static final String SEPARADOR = ";";

			// path onde o ficheiro vai ser criado
			private static final Path ARQUIVO_SAIDA = Paths.get("./skills.csv");

			// list onde os dados das skills vão ser guardados
			private List<Skill> skills;

			// formato de data usado no ficheiro
			final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			
			//Metodo que carrega os dados do ficheiro
			public SkillsCSVService() {
				carregaDados();
			}

			
			//Metodo para guardar uma skill
			@Override
			public void guardarSkill(Skill skill) {
				skill.setIdSkill(ultimoId() + 1);
				skills.add(skill);
				guardarDados();
			}


			// Metodo para atualizar uma skill
			@Override
			public void atualizarSkill(Skill skill) {
				Skill skillAntiga = fetchSkillsPorId(skill.getIdSkill());
				skillAntiga.setSkillNome(skill.getSkillNome());
				skillAntiga.setSkillArea(skill.getSkillArea());
				guardarDados();
			}

			
			// Metodo para fazer fetch de todas as skills
			@Override
			public List<Skill> fetchSkills() {
				return skills;
			}


			
			// Metodo para apagar uma skill
			@Override
			public void apagarSkill(int idSkill) {
				Skill skill = fetchSkillsPorId(idSkill);
				skills.remove(skill);
				guardarDados();
			}

			
			// Metodo para procurar uma skill por id
			public Skill fetchSkillsPorId(int idSkill) {
				return skills.stream().filter(c -> c.getIdSkill() == idSkill).findFirst()
						.orElseThrow(() -> new Error("Utilizador com id inserido não encontrado"));
			}
			
			
			// Metodo para procurar uma skill por nome
			@Override
			public Skill fetchSkillsPorNome(String skillNome) {
				return skills.stream().filter(c -> skillNome.equals(c.getSkillNome())).findAny()
						.orElseThrow(() -> new Error("Utilizador com o nome inserido não encontrado"));
			}
			

			
			// Metodo para guardar os dados que estão na list skills para o ficheiro
			private void guardarDados() {
				StringBuffer sb = new StringBuffer();
				for (Skill c : skills) {
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
			
			// Metodo que vai buscar o id mais alto e quando uma nova skill for adicionada faz +1
			private int ultimoId() {
				return skills.stream().mapToInt(Skill::getIdSkill).max().orElse(0);
			}

			// carrega os dados do ficheiro para a list skills
			private void carregaDados() {
				try {
					if(!Files.exists(ARQUIVO_SAIDA)) {
						Files.createFile(ARQUIVO_SAIDA);
					}
					skills = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			
			// transforma uma linha do ficheiro num objeto do tipo skill
			private Skill leLinha(String linha) {
				String colunas[] = linha.split(SEPARADOR);
				int id = Integer.parseInt(colunas[0]);
				Skill skill = new Skill();
				skill.setIdSkill(id);
				skill.setSkillNome(colunas[1]);
				skill.setSkillArea(colunas[2]);
			
				return skill;
			}
			
			// transforma um objeto skill numa linha para ser gravada no ficheiro
			private String criaLinha(Skill c) {
				String idStr = String.valueOf(c.getIdSkill());
				String linha = String.join(SEPARADOR, idStr, c.getSkillNome(), c.getSkillArea());
				
				return linha;
			}

			
	
}
