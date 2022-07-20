package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import service.UtilizadoresService;

import login.Utilizador;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/


public class UtilizadoresCSVService implements UtilizadoresService{
		// separador de colunas do ficheiro
		private static final String SEPARADOR = ";";

		// path onde o ficheiro vai ser criado
		private static final Path ARQUIVO_SAIDA = Paths.get("./utilizadores.csv");

		// list onde os dados dos utilizadores vão ser guardados
		private List<Utilizador> utilizadores;

		// formato de data usado no ficheiro
		final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		
		//Metodo que vai carregar os dados do ficheiro
		public UtilizadoresCSVService() {
			carregaDados();
		}

		
		//Metodo para guardar um utilizador
		@Override
		public void guardarUtilizador(Utilizador utillizador) {
			utillizador.setId(ultimoId() + 1);
			utilizadores.add(utillizador);
			guardarDados();
		}


		//Metodo para atualizar um utilizador
		@Override
		public void atualizarUtilizador(Utilizador utilizador) {
			Utilizador utilizadorAntigo = fetchUtilizadoresPorId(utilizador.getId());
			utilizadorAntigo.setNome(utilizador.getNome());
			utilizadorAntigo.setPasswd(utilizador.getPasswd());
			guardarDados();
		}

		//Metodo para fazer fetch e todos os utilizadores
		@Override
		public List<Utilizador> fetchUtilizadores() {
			return utilizadores;
		}

		
		//Metodo para apagar um utilizador
		@Override
		public void apagarUtilizador(int id) {
			Utilizador utilizador = fetchUtilizadoresPorId(id);
			utilizadores.remove(utilizador);
			guardarDados();
		}

		
		
		// Metodo para procurar um utilizador por id
		public Utilizador fetchUtilizadoresPorId(int id) {
			return utilizadores.stream().filter(c -> c.getId() == id).findFirst()
					.orElseThrow(() -> new Error("Utilizador com id inserido não encontrado"));
		}
		
		
		// Metodo para procurar um utilizador por nome e password
		@Override
		public Utilizador fetchUtilizadoresPorNome(String nome,String passwd) {
			return utilizadores.stream().filter(c -> nome.equals(c.getNome()) && passwd.equals(c.getPasswd())).findAny()
					.orElseThrow(() -> new Error("Utilizador com o nome inserido não encontrado"));
		}
		

		// Metodo que guarda os dados da list utilizadores no ficheiro
		private void guardarDados() {
			StringBuffer sb = new StringBuffer();
			for (Utilizador c : utilizadores) {
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
		
		// Metodo que vai buscar o id mais alto e quando um novo utilizador é adicionado fazemos +1
		private int ultimoId() {
			return utilizadores.stream().mapToInt(Utilizador::getId).max().orElse(0);
		}

		// carrega os dados do ficheiro para a list utilizadores
		private void carregaDados() {
			try {
				if(!Files.exists(ARQUIVO_SAIDA)) {
					Files.createFile(ARQUIVO_SAIDA);
				}
				utilizadores = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		// transforma uma linha do ficheiro num objeto do tipo utilizador
		private Utilizador leLinha(String linha) {
			String colunas[] = linha.split(SEPARADOR);
			int id = Integer.parseInt(colunas[0]);
			Utilizador utilizador = new Utilizador();
			utilizador.setId(id);
			utilizador.setNome(colunas[1]);
			utilizador.setPasswd(colunas[2]);
		
			return utilizador;
		}
		
		// transforma um objeto utilizador numa linha para ser guardada no ficheiro
		private String criaLinha(Utilizador c) {
			String idStr = String.valueOf(c.getId());
			String linha = String.join(SEPARADOR, idStr, c.getNome(), c.getPasswd());
			
			return linha;
		}

		

	}

