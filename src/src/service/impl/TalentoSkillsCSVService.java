package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.TalentoSkills;
import service.TalentoSkillsService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public class TalentoSkillsCSVService implements TalentoSkillsService {
	
	
	// separador de colunas do ficheiro
	private static final String SEPARADOR = ";";

	// path onde o ficheiro vai ser criado
	private static final Path ARQUIVO_SAIDA = Paths.get("./talentoSkills.csv");

	// list onde as skills associadas a um talento vão ser guardadas
	private List<TalentoSkills> talentoSkills;

	// formato de data usado no ficheiro
	final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

	
	// Metodo que carrega os dados do ficheiro
	public TalentoSkillsCSVService() {
		carregaDados();
	}

	
	//Metodo para guardar uma skill associada a um talento
	@Override
	public void guardarTalentoSkill(TalentoSkills talentoSkill) {
		talentoSkill.setIdTalentoSkills(ultimoId() + 1);
		talentoSkills.add(talentoSkill);
		guardarDados();
	}

	
	// Metodo para atualizar uma skill de um talento (anos de experiencia)
	@Override
	public void atualizarTalentoSkill(TalentoSkills talentoSkill) {
		TalentoSkills talentoSkillAntigo = fetchTalentoSkillsPorId(talentoSkill.getIdTalentoSkills());
		talentoSkillAntigo.setTalentoId(talentoSkill.getTalentoId());
		talentoSkillAntigo.setTalentoSkillsNome(talentoSkill.getTalentoSkillsNome());
		talentoSkillAntigo.setTalentoSkillsEmail(talentoSkill.getTalentoSkillsEmail());
		talentoSkillAntigo.setTalentoSkillsNomeSkill(talentoSkill.getTalentoSkillsNomeSkill());
		talentoSkillAntigo.setTalentoSkillAnosExperiencia(talentoSkill.getTalentoSkillAnosExperiencia());
		guardarDados();
	}

	
	//Metodo para fazer fetch de todas as skills associadas a talentos
	@Override
	public List<TalentoSkills> fetchTalentoSkills() {
		return talentoSkills;
	}

	
	//Metedo para apagar uma skill associada a um talento
	@Override
	public void apagarTalentoSkill(int idTalentoSkills) {
		TalentoSkills talentoSkill = fetchTalentoSkillsPorId(idTalentoSkills);
		talentoSkills.remove(talentoSkill);
		guardarDados();
	}
	
	
	// Metodo para procurar skills de talentos por id
	public TalentoSkills fetchTalentoSkillsPorId(int idTalentoSkills) {
		return talentoSkills.stream().filter(c -> c.getIdTalentoSkills() == idTalentoSkills).findFirst()
				.orElseThrow(() -> new Error("Talento com id inserido não encontrado"));
	}
	
	// Metodo para procurar skills de talentos por id do talento
	public List<TalentoSkills> fetchTalentoSkillsPorIdDoTalento(int idTalento) {
		return talentoSkills.stream().filter(c -> c.getTalentoId() == idTalento).collect(Collectors.toList());
	}
	
	
	// Metodo para procurar skills de talentos por nome do talento
	@Override
	public List<TalentoSkills> fetchTalentoSkillsPorNome(String talentoSkillNome) {
		return talentoSkills.stream().filter(c -> talentoSkillNome.equals(c.getTalentoSkillsNome())).collect(Collectors.toList());
				
	}
	
	// Metodo para procurar skills de talentos por nome de skill
	@Override
	public List<TalentoSkills> fetchTalentoSkillsPorNomeDeSkill(String talentoSkillNomeSkill) {
		return talentoSkills.stream().filter(c -> talentoSkillNomeSkill.equals(c.getTalentoSkillsNomeSkill())).collect(Collectors.toList());
				
	}
	

	// Metodo para guardar os dados da list talentoSkills no ficheiro
	private void guardarDados() {
		StringBuffer sb = new StringBuffer();
		for (TalentoSkills c : talentoSkills) {
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
	
	// Metodo que vai buscar o id mais alto e quando uma nova skillTalento é adicionada fazemos +1
	private int ultimoId() {
		return talentoSkills.stream().mapToInt(TalentoSkills::getIdTalentoSkills).max().orElse(0);
	}

	// carrega os dados do ficheiro para a list talentoSkills
	private void carregaDados() {
		try {
			if(!Files.exists(ARQUIVO_SAIDA)) {
				Files.createFile(ARQUIVO_SAIDA);
			}
			talentoSkills = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// transforma uma linha do ficheiro num objeto do tipo talentoSkill
	private TalentoSkills leLinha(String linha) {
		String colunas[] = linha.split(SEPARADOR);
		int id = Integer.parseInt(colunas[0]);
		TalentoSkills talentoSkill = new TalentoSkills();
		talentoSkill.setIdTalentoSkills(id);
		talentoSkill.setTalentoId(Integer.parseInt(colunas[1]));;
		talentoSkill.setTalentoSkillsNome(colunas[2]);
		talentoSkill.setTalentoSkillsEmail(colunas[3]);
		talentoSkill.setTalentoSkillsNomeSkill(colunas[4]);
		talentoSkill.setTalentoSkillAnosExperiencia(Integer.parseInt(colunas[5]));
	
		return talentoSkill;
	}
	
	// transforma um objeto talentoSkill numa linha para ser gravada no ficheiro
	private String criaLinha(TalentoSkills c) {
		String idStr = String.valueOf(c.getIdTalentoSkills());
		String linha = String.join(SEPARADOR, idStr,String.valueOf(c.getTalentoId()) ,c.getTalentoSkillsNome(), c.getTalentoSkillsEmail(), c.getTalentoSkillsNomeSkill(), String.valueOf(c.getTalentoSkillAnosExperiencia()));
		
		return linha;
	}


}
