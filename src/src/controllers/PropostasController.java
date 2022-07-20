package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import classes.Cliente;
import classes.PropostaSkills;
import classes.PropostaTrabalho;
import classes.Skill;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ClienteService;
import service.PropostaSkillsService;
import service.PropostaTrabalhoService;
import service.SkillsService;



/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */

public class PropostasController implements Initializable{
	
	@FXML
    private TableView<PropostaTrabalho> tblPropostas;

    @FXML
    private TableColumn<PropostaTrabalho, Integer> clIdProposta;
    
    @FXML
    private TableColumn<PropostaTrabalho, String> clPropostaEmpresa;
    
    @FXML
    private TableColumn<PropostaTrabalho, String> clNomeProposta;

    @FXML
    private TableColumn<PropostaTrabalho, String> clCategoriaProposta;

    @FXML
    private TableColumn<PropostaTrabalho, String> clHorasProposta;

    @FXML
    private TableColumn<PropostaTrabalho, String> clDescriçãoProposta;

    @FXML
    private TextField txtIdProposta;

    @FXML
    private TextField txtNomeProposta;

    @FXML
    private TextField txtCategoriaProposta;

    @FXML
    private TextField txtHorasProposta;

    @FXML
    private TextField txtDescricaoProposta;

    @FXML
    private ComboBox<String> comboSkills;
    
    @FXML
    private ComboBox<String> comboClientes;

    @FXML
    private TextField txtExperienciaSkill;

    @FXML
    private TableView<PropostaSkills> tblSkillsProposta;

    @FXML
    private TableColumn<PropostaSkills, Integer> clIdSkillProposta;

    @FXML
    private TableColumn<PropostaSkills, String> clNomeSkillProposta;

    @FXML
    private TableColumn<PropostaSkills, String> clAnosSkillProposta;
    
    @FXML
    private TableColumn<PropostaSkills, String> clIdProposta2;
    
    @FXML
    private Button btnSaveProposta;

    @FXML
    private Button btnAtualizarProposta;

    @FXML
    private Button btnApagarProposta;

    @FXML
    private Button btnLimparProposta;

    @FXML
    private Button btnMenuFromPropostas;
    
    @FXML
    private Button btnSaveSkillProposta;
    
    
    private PropostaSkillsService propostaSkillsService;
    private PropostaTrabalhoService propostaTrabalhoService;
    private SkillsService skillsService;
    private ClienteService clienteService; 
    
    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do propostaSkillsService, propostaTrabalhoService,skillsService e do
		 * clienteService o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem a
		 * estrutura das tabelas, os bindings, inicializamos as comboboxs,carregamos os dados para a tabela das
		 * propostas e definimos o estado da combobox skills como disable pois está so estará ativa quando uma proposta
		 * estiver selecionada
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			propostaSkillsService = PropostaSkillsService.getNewInstance();
			propostaTrabalhoService = PropostaTrabalhoService.getNewInstance();
			skillsService = SkillsService.getNewInstance();
			clienteService = ClienteService.getNewInstance();
			
			configuraColunasPropostas();
			configuraColunasPropostaSkills();
			configuraBindings();
			
			comboSkills.setDisable(true);
			addComboSkills();
			addComboClientes();
			atualizaDadosTabelaPropostas();
		

		}
		
    
    // Metodo para regressar ao menu principal
    @FXML
    void goToMenuFromPropostas(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
        Parent home = loader.load();
        Scene homeScene = new Scene(home);
        Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        homeStage.setScene(homeScene);
        homeStage.setMaximized(false);
        homeStage.show();

    }
    
    
    // Metodos para adicionar dados ás comboboxs
	private void addComboClientes() {
    	List<Cliente> clientes = clienteService.fetchClientes();
    		 for (int i = 0; i < clientes.size(); i++) {
    	  
    			 comboClientes.getItems().addAll(clientes.get(i).getIdCliente()+" - "+clientes.get(i).getClienteNome());
    		 }
    }
    
    
	private void addComboSkills() {
    	List<Skill> skills2 = skillsService.fetchSkills();
    		 for (int i = 0; i < skills2.size(); i++) {
    	  
    			 comboSkills.getItems().addAll(skills2.get(i).getSkillNome());
    		 }
    }
    
	
	// Metodo para guardar uma proposta de trabalho e no fim atualizar a tabela
    public void guardarPropostaTrabalho() {
		PropostaTrabalho c = new PropostaTrabalho();
		getValoresProposta(c);
		propostaTrabalhoService.guardarProposta(c);
		atualizaDadosTabelaPropostas();
	}
    
    
    /* Metodo para guardar uma skill associada a uma proposta e depois 
     * atualizar a tabela que contem todas as skills associadas a essa proposta
     */
    public void guardarPropostaSkill() {
		PropostaSkills c = new PropostaSkills();
		getValoresSkill(c);
		propostaSkillsService.guardarSkillProposta(c);
		atualizaDadosTabelaPropostaSkills();
	}
    
    
    
    // Metodo para atualizar uma proposta e depois atualizar os dados da tabela
	public void atualizarProposta() {
		PropostaTrabalho c = tblPropostas.getSelectionModel().getSelectedItem();
		getValoresProposta(c);
		propostaTrabalhoService.atualizarProposta(c);
		atualizaDadosTabelaPropostas();
	}


	// Metodo para apagar uma proposta do sistema
	public void apagarProposta() {
		PropostaTrabalho c = tblPropostas.getSelectionModel().getSelectedItem();
		int idProposta = c.getIdProposta();

		apagarSkillsProposta(idProposta);
		
		
		for(int i = 0; i <= propostaSkillsService.fetchPropostaSkillsPorIdProposta(idProposta).size();i++) {	
		
				apagarSkillsProposta(idProposta);
		
		}
		
		if(propostaSkillsService.fetchPropostaSkillsPorIdProposta(idProposta).size() == 0) {
			propostaTrabalhoService.apagarProposta(c.getIdProposta());
		}
		
		atualizaDadosTabelaPropostas();
		tblSkillsProposta.getItems().clear(); 
		limpar();
	}
	
	
	// Metodo que apaga do ficheiro propostaSkills todas as skills associadas á proposta apagada
	public void apagarSkillsProposta(int idProposta) {
		
		for(int i = 0 ; i < propostaSkillsService.fetchSkillsProposta().size(); i++) {
	
			int id = propostaSkillsService.fetchSkillsProposta().get(i).getIdProposta();


			if(id == idProposta) {
				int idApagar =  propostaSkillsService.fetchSkillsProposta().get(i).getIdSkillProposta();
				propostaSkillsService.apagarSkillProposta(idApagar);
			}
		}
		
	
	}
	
	
	
	
	/* Metodo que limpa todos os text fields, limpa a tabela skillsProposta, retira os objetos
	 *  seleciondas nas comboboxs e na tabela propostas
	 */
	public void limpar() {
		tblPropostas.getSelectionModel().select(null);
		tblSkillsProposta.getItems().clear();
		comboClientes.getSelectionModel().select(null);
		comboSkills.getSelectionModel().select(null);
		txtIdProposta.setText("");
		txtNomeProposta.setText("");
		txtCategoriaProposta.setText("");
		txtHorasProposta.setText("");
		txtDescricaoProposta.setText("");
		limparSkill();
	}
	
	
	
	public void limparSkill() {
		comboSkills.getSelectionModel().select(null);
		txtExperienciaSkill.setText("");
	}
	
	
	/* Metodo que vai buscar os valores que se encontram nos text fields e os associa aos 
	 * respetivos atributos de uma proposta de trabalho
	 */
	private void getValoresProposta(PropostaTrabalho c) {
		
		String Cliente = comboClientes.getValue().toString();
		int idEmpresa = Integer.parseInt(Cliente.replaceAll("[\\D]", ""));
		c.setIdEmpresa(idEmpresa);
		c.setPropostaEmpresa(clienteService.fetchClientesPorId(idEmpresa).getClienteNome());
		c.setPropostaNome(txtNomeProposta.getText());
		c.setPropostaCategoria(txtCategoriaProposta.getText());
		c.setPropostaHoras(Integer.parseInt(txtHorasProposta.getText()));
		c.setPropostaDescricao(txtDescricaoProposta.getText());
	}
	
	
	/* Metodo que vai buscar os valores que se encontram nos text fields e os associa aos 
	 * respetivos atributos de uma skill associada a uma proposta
	 */
	private void getValoresSkill(PropostaSkills c) {
		
		c.setSkillNome(comboSkills.getValue().toString());
		c.setSkillAnos(txtExperienciaSkill.getText());
		c.setIdProposta(Integer.parseInt(txtIdProposta.getText()));
		c.setIdSkillProposta(0);
	}



	/* Metodo que chamamos quando registamos,atualizamos ou apagamos uma proposta de trabalho
	 * para atualizar os dados da tabela e os vermos sempre atualizados
	 */
	private void atualizaDadosTabelaPropostas() {
		tblPropostas.getItems().setAll(propostaTrabalhoService.fetchPropostas());
		limpar();
	}
	
	
	
	/* Metodo que chamamos quando adicionamos uma skill a uma proposta
	 * para atualizar os dados da tabela e os vermos sempre atualizados
	 */
	private void atualizaDadosTabelaPropostaSkills() {
		int idPt = Integer.parseInt(txtIdProposta.getText());
		tblSkillsProposta.getItems().setAll(propostaSkillsService.fetchPropostaSkillsPorIdProposta(idPt));
		limparSkill();
	
	}

	// Metodos para configurar a estrutura das tabelas
	private void configuraColunasPropostas() {
		clIdProposta.setCellValueFactory(new PropertyValueFactory<>("idProposta"));
		clPropostaEmpresa.setCellValueFactory(new PropertyValueFactory<>("PropostaEmpresa"));
		clNomeProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaNome"));
		clCategoriaProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaCategoria"));
		clHorasProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaHoras"));
		clDescriçãoProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaDescricao"));
	}

	private void configuraColunasPropostaSkills() {
		clIdSkillProposta.setCellValueFactory(new PropertyValueFactory<>("idSkillProposta"));
		clNomeSkillProposta.setCellValueFactory(new PropertyValueFactory<>("skillNome"));
		clAnosSkillProposta.setCellValueFactory(new PropertyValueFactory<>("skillAnos"));
		clIdProposta2.setCellValueFactory(new PropertyValueFactory<>("idProposta"));
	}
	
	
	// Metodo para confgurar os bindings
	private void configuraBindings() {
		
		// Boolean para verificar se os campos estão preenchidos
		BooleanBinding camposPreenchidos = txtNomeProposta.textProperty().isEmpty()
				.or(txtCategoriaProposta.textProperty().isEmpty()).or(txtHorasProposta.textProperty().isEmpty()).or(txtDescricaoProposta.textProperty().isEmpty().or(comboClientes.getSelectionModel().selectedItemProperty().isNull()));
		// Boolean para verificar se os campos necessarios á skill estão preenchidos
		BooleanBinding skillPreenchida = comboSkills.getSelectionModel().selectedItemProperty().isNull().or(txtExperienciaSkill.textProperty().isEmpty()).or(tblPropostas.getSelectionModel().selectedItemProperty().isNull());
		
		// Boolean para verificar se existe alguma proposta selecionada na tabela
		BooleanBinding algoSelecionado = tblPropostas.getSelectionModel().selectedItemProperty().isNull();
		
		// Botões que são ativados qaundo os campos necessarios estão preenchidos ou está uma propota selecionada na tabela
		btnApagarProposta.disableProperty().bind(algoSelecionado);
		btnAtualizarProposta.disableProperty().bind(algoSelecionado);
		btnLimparProposta.disableProperty().bind(algoSelecionado.and(camposPreenchidos));
		btnSaveSkillProposta.disableProperty().bind(skillPreenchida);
		
		/*Botão para guardar proposta que é ativado quando os campos necessarios estão preenchidos e 
		 * não esta nenhuma proposta selecionada na tabela
		 */
		btnSaveProposta.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
		
	
		/* Metodo que verifica se existe alguma proposta selecionada na tabela e atribui os dados da tabela selecionada
		 * aos respetivos text fields, atualiza tambem os conteudo da tabela das skills associadas a esta proposta e ativa tambem
		 * a combobox das skills para que se possam adicionar mais skills ,caso necessario, á proposta.
		 * Caso não esteja nenhuma proposta selecionada a combobox das skills volta a desativar
		 */
		tblPropostas.getSelectionModel().selectedItemProperty().addListener((b, o ,n) -> {
		if (n != null) {
			comboSkills.setDisable(false);
			comboClientes.setValue(n.getPropostaEmpresa());
			txtIdProposta.setText(String.valueOf(n.getIdProposta()));
			txtNomeProposta.setText(n.getPropostaNome());
			txtCategoriaProposta.setText(n.getPropostaCategoria());
			txtHorasProposta.setText(String.valueOf(n.getPropostaHoras()));
			txtDescricaoProposta.setText(n.getPropostaDescricao());
			atualizaDadosTabelaPropostaSkills();
			}else if(n==null) {
				comboSkills.setDisable(true);
			}
		});
		
		

	}
	



}
