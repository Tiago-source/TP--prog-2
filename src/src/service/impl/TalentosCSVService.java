package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import classes.Talento;
import service.TalentoService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class TalentosCSVService implements TalentoService {
	

				// separador de colunas do ficheiro
				private static final String SEPARADOR = ";";

				// path onde o ficheiro vai ser criado
				private static final Path ARQUIVO_SAIDA = Paths.get("./talentos.csv");

				// list que vai guardar os dados dos talentos
				private List<Talento> talentos;

				// formato de data usado no ficheiro
				final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

				
				// Metodo que carrega os dados do ficheiro
				public TalentosCSVService() {
					carregaDados();
				}

				
				// Metodo para guardar um novo talento
				@Override
				public void guardarTalento(Talento talento) {
					talento.setIdTalento(ultimoId() + 1);
					talentos.add(talento);
					guardarDados();
				}


				// Metodo para atualizar um talento
				@Override
				public void atualizarTalento(Talento talento) {
					Talento talentoAntigo = fetchTalentosPorId(talento.getIdTalento());
					talentoAntigo.setTalentoNome(talento.getTalentoNome());
					talentoAntigo.setTalentoPais(talento.getTalentoPais());
					talentoAntigo.setTalentoEmail(talento.getTalentoEmail());
					talentoAntigo.setTalentoTarifa(talento.getTalentoTarifa());
					guardarDados();
				}

				//Metodo para fazer fetch de todos os talentos na list
				@Override
				public List<Talento> fetchTalentos() {
					return talentos;
				}
				
				
				
				//Metodo para apagar um talento
				@Override
				public void apagarTalento(int idTalento) {
					Talento talento = fetchTalentosPorId(idTalento);
					talentos.remove(talento);
					guardarDados();
				}

				
				//Metodo para procurar um talento pelo id
				public Talento fetchTalentosPorId(int idTalento) {
					return talentos.stream().filter(c -> c.getIdTalento() == idTalento).findFirst()
							.orElseThrow(() -> new Error("Talento com id inserido não encontrado"));
				}
				
				//Metodo para procurar um talento pelo nome
				@Override
				public Talento fetchTalentosPorNome(String talentoNome) {
					return talentos.stream().filter(c -> talentoNome.equals(c.getTalentoNome())).findAny()
							.orElseThrow(() -> new Error("Talento com o nome inserido não encontrado"));
				}
				
				
				//Metodo para procurar um talento pelo pais
				@Override
				public List<Talento> fetchTalentosPorNomePais(String talentoPais) {
					return talentos.stream().filter(c -> talentoPais.equals(c.getTalentoPais())).collect(Collectors.toList());
				}
				

				// Metodo para guardar os dados da list talentos no ficheiro
				private void guardarDados() {
					StringBuffer sb = new StringBuffer();
					for (Talento c : talentos) {
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
				
				// Metodo que vai buscar o id mais alto e quando um novo talento e adicionado fazemos +1
				private int ultimoId() {
					return talentos.stream().mapToInt(Talento::getIdTalento).max().orElse(0);
				}

				// carrega os dados do ficheiro para a list talentos
				private void carregaDados() {
					try {
						if(!Files.exists(ARQUIVO_SAIDA)) {
							Files.createFile(ARQUIVO_SAIDA);
						}
						talentos = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				
				// transforma uma linha do ficheiro num objeto do tipo talento
				private Talento leLinha(String linha) {
					String colunas[] = linha.split(SEPARADOR);
					int id = Integer.parseInt(colunas[0]);
					Talento talento = new Talento();
					talento.setIdTalento(id);
					talento.setTalentoNome(colunas[1]);
					talento.setTalentoPais(colunas[2]);
					talento.setTalentoEmail(colunas[3]);
					talento.setTalentoTarifa(Integer.parseInt(colunas[4]));
				
					return talento;
				}
				
				// transforma um objeto talento numa linha para ser gravada no ficheiro
				private String criaLinha(Talento c) {
					String idStr = String.valueOf(c.getIdTalento());
					String linha = String.join(SEPARADOR, idStr, c.getTalentoNome(), c.getTalentoPais(), c.getTalentoEmail(), String.valueOf(c.getTalentoTarifa()));
					
					return linha;
				}

				
		
	}


