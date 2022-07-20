package controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import classes.PropostaSkills;
import classes.PropostaTrabalho;
import classes.Talento;
import classes.TalentoSkills;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.PropostaSkillsService;
import service.PropostaTrabalhoService;
import service.TalentoService;
import service.TalentoSkillsService;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class PropostaTalentosController implements Initializable {

	 @FXML
	    private TableView<PropostaTrabalho> tblPropostas;

	    @FXML
	    private TableColumn<PropostaTrabalho, Integer> clidProposta;

	    @FXML
	    private TableColumn<PropostaTrabalho, String> clEmpresaProposta;

	    @FXML
	    private TableColumn<PropostaTrabalho, String> clNomeProposta;

	    @FXML
	    private TableColumn<PropostaTrabalho, String> clCategoriaProposta;

	    @FXML
	    private TableColumn<PropostaTrabalho, Integer> clHorasProposta;

	    @FXML
	    private TableColumn<PropostaTrabalho, String> clDescricaoProposta;

	    @FXML
	    private TextField txtIdProposta;

	    @FXML
	    private TextField txtEmpresa;

	    @FXML
	    private TextField txtNomeProposta;

	    @FXML
	    private TextField txtCategoriaProposta;

	    @FXML
	    private TextField txtHorasProposta;

	    @FXML
	    private TextField txtDescricaoProposta;

	    @FXML
	    private TableView<Talento> tblTalentosProposta;

	    @FXML
	    private TableColumn<Talento, Integer> clIdTalento;

	    @FXML
	    private TableColumn<Talento, String> clNomeTalento;

	    @FXML
	    private TableColumn<Talento, String> clPaisTalento;

	    @FXML
	    private TableColumn<Talento, String> clEmailTalento;

	    @FXML
	    private TableColumn<Talento, Integer> clTarifaTalento;

	    @FXML
	    private Button btnLimpar;

	    @FXML
	    private Button btnMenuFromTalentosProposta;
	    
	    
	    TalentoSkillsService talentoSkillsService;
	    PropostaTrabalhoService propostaTrabalhoService;
	    PropostaSkillsService propostaSkillsService;
	    TalentoService talentoService;

	    
	    // Metodo para regressar ao menu principal
	    @FXML
	    void goToMenuFromTalentosProposta(ActionEvent event) throws IOException {
	    	
	     	FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
    	    Parent home = loader.load();
    	    Scene homeScene = new Scene(home);
    	    Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	    homeStage.setScene(homeScene);
    	    homeStage.setMaximized(false);
    	    homeStage.show();

	    }
	    
	    
	  
		    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
			 * e criamos novas instancias do talentoSkillsService, propostaTrabalhoService, propostaSkillsService
			 *  e do talentoService o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem a
			 * estrutura das tabelas, os bindings,carregamos os dados para a tabela das propostas
			 */
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				talentoSkillsService = TalentoSkillsService.getNewInstance();
				propostaTrabalhoService = PropostaTrabalhoService.getNewInstance();
				propostaSkillsService = PropostaSkillsService.getNewInstance();
				talentoService = TalentoService.getNewInstance();
				
				configuraColunasTblTalentos();
				configuraColunasTblPropostas();
				atualizaDadosTabelaPropostas();

				configuraBindings();
				
			}
			
			
			
			// Metodo para carregar o contuedo da tabela propostas
			private void atualizaDadosTabelaPropostas() {
				tblPropostas.getItems().setAll(propostaTrabalhoService.fetchPropostas());
				limpar();
			}
			
			
			// Metodo para carregar para a tabela dos talentos aptos ( que possuam as skills necessarias)
			private void atualizaDadosTabelaTalentosProposta() {
				
				
				int id = Integer.parseInt(txtIdProposta.getText());
				
				int num = propostaSkillsService.fetchPropostaSkillsPorIdProposta(id).size();
				List<PropostaSkills> skillsP = propostaSkillsService.fetchPropostaSkillsPorIdProposta(id);
				List<String> skills = new ArrayList<String>();
				
				for(int i = 0; i < num ;i++) {
					
					String in = skillsP.get(i).getSkillNome();
					skills.add(in);
				
				}
				
				String listString = String.join(",", skills);
				
				String[] words = listString.split(",");
				
		    	List<TalentoSkills> talentos3;
		    	List<TalentoSkills> talentos4 = new ArrayList<TalentoSkills>();
	    		
		    	for(int i = 0; i < words.length; i++){
		    	
		    		talentos3 = talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(words[i]);
		    		
		    		for(int j = 0; j< words.length; j++) {
		    			  		
		    		talentos4.addAll(talentos3);
		    			
		    		}
		    
		    	}
		    	
	
		    	List<TalentoSkills> Distinct = talentos4.stream().distinct().collect(Collectors.toList());
		    	
		    	List<String> nomes = new ArrayList<String>();
		    	for(int i = 0; i < Distinct.size(); i++) {
		    		
		    			
		    			nomes.add(Distinct.get(i).getTalentoSkillsNome());
		    			
		    	
		    		}
		    
		    	 
		    	List<Talento> talentos5 = new ArrayList<Talento>();
		    	Set<String> counts = new HashSet<>(nomes);
		        for (String s: counts) {
		        	
		            if(Collections.frequency(nomes, s) >= words.length) {
		            	talentos5.add(talentoService.fetchTalentosPorNome(s));
		            	
		            }
		        }
		        
		        tblTalentosProposta.getItems().setAll(talentos5);
		   }
			
			
			//Metodos para configurar a estrutura das tabelas
			private void configuraColunasTblPropostas() {
				clidProposta.setCellValueFactory(new PropertyValueFactory<>("idProposta"));
				clEmpresaProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaEmpresa"));
				clNomeProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaNome"));
				clCategoriaProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaCategoria"));
				clHorasProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaHoras"));
				clDescricaoProposta.setCellValueFactory(new PropertyValueFactory<>("PropostaDescricao"));
			}
			
			private void configuraColunasTblTalentos() {
				clIdTalento.setCellValueFactory(new PropertyValueFactory<>("idTalento"));
				clNomeTalento.setCellValueFactory(new PropertyValueFactory<>("talentoNome"));
				clPaisTalento.setCellValueFactory(new PropertyValueFactory<>("talentoPais"));
				clEmailTalento.setCellValueFactory(new PropertyValueFactory<>("talentoEmail"));
				clTarifaTalento.setCellValueFactory(new PropertyValueFactory<>("talentoTarifa"));
			}
			
			
			
			/*Metodo para limpar todos os text fields e a tabela de talentos e 
			 * tambem para desselecionar qualquer proposta previamente selecionada
			 */
			public void limpar(){
				tblPropostas.getSelectionModel().select(null);
				tblTalentosProposta.getItems().clear();
				txtIdProposta.setText("");
				txtEmpresa.setText("");
				txtNomeProposta.setText("");
				txtCategoriaProposta.setText("");
				txtHorasProposta.setText("");
				txtDescricaoProposta.setText("");
			}
			
			
			//Metodo para configurar os bindings
			private void configuraBindings() {
		
				
				// Boolean que indica se existe alguma proposta selecionada
				BooleanBinding algoSelecionado = tblPropostas.getSelectionModel().selectedItemProperty().isNull();
				
				//Ativa o botão limpar se exestir alguma proposta selecionada
				btnLimpar.disableProperty().bind(algoSelecionado);
				
				/*Metodo que quando alguma proposta é selecionda na tabela atribui os seus dados aos 
				 * respetivos text fields e atualiza a tabela talentosProposta
				 */
				tblPropostas.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
					if (n != null) {
						txtIdProposta.setText(String.valueOf(n.getIdProposta()));
						txtEmpresa.setText(n.getPropostaEmpresa());
						txtNomeProposta.setText(n.getPropostaNome());
						txtCategoriaProposta.setText(n.getPropostaCategoria());
						txtHorasProposta.setText(String.valueOf(n.getPropostaHoras()));
						txtDescricaoProposta.setText(n.getPropostaDescricao());
						atualizaDadosTabelaTalentosProposta();
					}
				});
	
	
			}	
			
			
}
