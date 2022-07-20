package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.TalentoExperiencia;
import service.TalentoExperienciaService;



/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public class TalentoExperienciaCSVService implements TalentoExperienciaService{
	
	
		// separador de colunas do ficheiro
		private static final String SEPARADOR = ";";

		// path onde o ficheiro vai ser criado
		private static final Path ARQUIVO_SAIDA = Paths.get("./talentoExperiencia.csv");

		// list onde as experiencias de cada talento serão guardadas
		private List<TalentoExperiencia> talentoExperiencias;

		// formato de data usado no ficheiro
		final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		
		// Metodo que carrega os dados do ficheiro
		public TalentoExperienciaCSVService() {
			carregaDados();
		}

		
		//Metodo para guardar uma experiencia associada a um talento
		@Override
		public void guardarTalentoExperiencia(TalentoExperiencia talentoExperiencia) {
			talentoExperiencia.setIdTalentoExperiencia(ultimoId() + 1);
			talentoExperiencias.add(talentoExperiencia);
			guardarDados();
		}


		// Metodo para atualizar uma experiencia associada a um talento
		@Override
		public void atualizarTalentoExperiencia(TalentoExperiencia talentoExperiencia) {
			TalentoExperiencia talentoExperienciaAntiga = fetchTalentoExperienciasPorId(talentoExperiencia.getIdTalentoExperiencia());
			talentoExperienciaAntiga.setTalentoId(talentoExperiencia.getTalentoId());
			talentoExperienciaAntiga.setTalentoNome(talentoExperiencia.getTalentoNome());
			talentoExperienciaAntiga.setTalentoEmail(talentoExperiencia.getTalentoEmail());
			talentoExperienciaAntiga.setTalentoExperienciaTitulo(talentoExperiencia.getTalentoExperienciaTitulo());
			talentoExperienciaAntiga.setTalentoExperienciaEmpresa(talentoExperiencia.getTalentoExperienciaEmpresa());
			talentoExperienciaAntiga.setTalentoExperienciaInicio(talentoExperiencia.getTalentoExperienciaInicio());
			talentoExperienciaAntiga.setTalentoExperienciaFim(talentoExperiencia.getTalentoExperienciaFim());
			guardarDados();
		}

		
		// Metodo para fazer fetch de todas as experiencias de todos os talentos
		@Override
		public List<TalentoExperiencia> fetchTalentoExperiencias() {
			return talentoExperiencias;
		}

		
		//Metodo para apagar uma experiencia associada a um talento pelo id da experiencia
		@Override
		public void apagarTalentoExperiencia(int idTalentoExperiencia) {
			TalentoExperiencia talentoExperiencia = fetchTalentoExperienciasPorId(idTalentoExperiencia);
			talentoExperiencias.remove(talentoExperiencia);
			guardarDados();
		}

		//Metodo para procurar uma experiencia associada a um talento pelo id da experiencia
		public TalentoExperiencia fetchTalentoExperienciasPorId(int idTalentoExperiencia) {
			return talentoExperiencias.stream().filter(c -> c.getIdTalentoExperiencia() == idTalentoExperiencia).findFirst()
					.orElseThrow(() -> new Error("Talento com id inserido não encontrado"));
		}
		
		//Metodo para procurar todas as experiencias associadas a um talento pelo id do talento
		public List<TalentoExperiencia> fetchTalentoExperienciasPorIdDoTalento(int idTalento) {
			return talentoExperiencias.stream().filter(c -> c.getIdTalentoExperiencia() == idTalento).collect(Collectors.toList());
		}
		
		//Metodo para procurar todas as experiencias associadas a um talento pelo nome do talento
		@Override
		public List<TalentoExperiencia> fetchTalentoExperienciasPorNome(String talentoNome) {
			return talentoExperiencias.stream().filter(c -> talentoNome.equals(c.getTalentoNome())).collect(Collectors.toList());
					
		}
		
		//Metodo para procurar todas as experiencias pelo nome da empresa
		@Override
		public List<TalentoExperiencia> fetchTalentoExperienciasPorNomeDeExperiencia(String talentoExperienciaEmpresa) {
			return talentoExperiencias.stream().filter(c -> talentoExperienciaEmpresa.equals(c.getTalentoExperienciaEmpresa())).collect(Collectors.toList());
					
		}
		

		//Metodo que guarda todos os dados da list talentoExperiencias no ficheiro
		private void guardarDados() {
			StringBuffer sb = new StringBuffer();
			for (TalentoExperiencia c : talentoExperiencias) {
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
		
		// Metodo que vai buscar o id mais alto e quando uma experiencia nova é adicionada faz +1
		private int ultimoId() {
			return talentoExperiencias.stream().mapToInt(TalentoExperiencia::getIdTalentoExperiencia).max().orElse(0);
		}

		// Metodo que carrega os dados do ficheiro para a list talentoExperiencias
		private void carregaDados() {
			try {
				if(!Files.exists(ARQUIVO_SAIDA)) {
					Files.createFile(ARQUIVO_SAIDA);
				}
				talentoExperiencias = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		// Metodo transforma uma linha do ficheiro para um objeto do tipo talentoExperiencia
		private TalentoExperiencia leLinha(String linha) {
			String colunas[] = linha.split(SEPARADOR);
			int id = Integer.parseInt(colunas[0]);
			TalentoExperiencia talentoExperiencia = new TalentoExperiencia();
			talentoExperiencia.setIdTalentoExperiencia(id);
			talentoExperiencia.setTalentoId(Integer.parseInt(colunas[1]));
			talentoExperiencia.setTalentoNome(colunas[2]);
			talentoExperiencia.setTalentoEmail(colunas[3]);
			talentoExperiencia.setTalentoExperienciaTitulo(colunas[4]);
			talentoExperiencia.setTalentoExperienciaEmpresa(colunas[5]);
			talentoExperiencia.setTalentoExperienciaInicio(colunas[6]);
			talentoExperiencia.setTalentoExperienciaFim(colunas[7]);
			
			return talentoExperiencia;
		}
		
		// Metodo que transforma um objeto talentoExperiencia numa linha para ser gravada no ficheiro
		private String criaLinha(TalentoExperiencia c) {
			String idStr = String.valueOf(c.getIdTalentoExperiencia());
			String linha = String.join(SEPARADOR, idStr, String.valueOf(c.getTalentoId()) ,c.getTalentoNome(), c.getTalentoEmail(), c.getTalentoExperienciaTitulo(), c.getTalentoExperienciaEmpresa(), c.getTalentoExperienciaInicio(), c.getTalentoExperienciaFim());
			
			return linha;
		}



}
