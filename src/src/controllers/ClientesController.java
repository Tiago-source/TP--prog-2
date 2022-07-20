package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Cliente;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ClienteService;


/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */

public class ClientesController implements Initializable {
	
	@FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, Integer> clidCliente;

    @FXML
    private TableColumn<Cliente, String> clNomeCliente;

    @FXML
    private TableColumn<Cliente, String> clEmailCliente;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtEmailCliente;

    @FXML
    private Button btnSaveCliente;

    @FXML
    private Button btnAtualizarCliente;

    @FXML
    private Button btnApagarCliente;

    @FXML
    private Button btnLimparCliente;

    @FXML
    private Button btnMenuFromClientes;

	private ClienteService clienteService;

    
    
    // Metodo para regressar ao menu principal
    @FXML
    void goToMenuFromClientes(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
        Parent home = loader.load();
        Scene homeScene = new Scene(home);
        Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        homeStage.setScene(homeScene);
        homeStage.setMaximized(false);
        homeStage.show();
    }



    	/*Metodo utilizado ao iniciar a aplicação onde carregamos os dados para a tabela dos clientes,ativamos os binds
    	 * e criamos uma nova instancia do clienteService o que nos permite utilizar os metodos criados no mesmo
    	 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			clienteService = ClienteService.getNewInstance();

			configuraColunas();
			configuraBindings();
			atualizaDadosTabela();
		}
	
   
		// Metodo associado ao butão guardar que regista um novo cliente
		public void guardarCliente() {
			Cliente c = new Cliente();
			getValores(c);
			clienteService.guardarCliente(c);
			atualizaDadosTabela();
		}

		// Metodo associado ao butão editar que atualiza um cliente ja registado ao selecionar o mesmo na tabela
		public void atualizarCliente() {
			Cliente c = tblClientes.getSelectionModel().getSelectedItem();
			getValores(c);
			clienteService.atualizarCliente(c);
			atualizaDadosTabela();
		}

		// Metodo associado ao butão apagar que apaga um cliente do sistema
		public void apagarCliente() {
			Cliente c = tblClientes.getSelectionModel().getSelectedItem();
			clienteService.apagarCliente(c.getIdCliente());
			atualizaDadosTabela();
		}

		// Metodo limpar que limpa todos nos text fields e desmarca o cliente selecionado na tabela
		public void limpar() {
			tblClientes.getSelectionModel().select(null);
			txtIdCliente.setText("");
			txtNomeCliente.setText("");
			txtEmailCliente.setText("");
		
		}
		
		// Metodo que vai buscar os valores inseridos nos text fields que vão ser usados para guardar ou atualizar um cliente
		private void getValores(Cliente c) {
			c.setClienteNome(txtNomeCliente.getText());
			c.setClienteEmail(txtEmailCliente.getText());
		}


		/* Metodo que vamos executar sempre que registamos,atualizamos ou apagamos um cliente
		 *  para vermos sempre os dados atualizados
		 */
		private void atualizaDadosTabela() {
			tblClientes.getItems().setAll(clienteService.fetchClientes());
			limpar();
		}

		// Metodo para configurar as colunas da tabela clientes
		private void configuraColunas() {
			clidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
			clNomeCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
			clEmailCliente.setCellValueFactory(new PropertyValueFactory<>("clienteEmail"));
		}

		// Bindings da pagina
		private void configuraBindings() {
			
			// boolean para verificar se os campos estão todos preenchidos
			BooleanBinding camposPreenchidos = txtNomeCliente.textProperty().isEmpty()
					.or(txtEmailCliente.textProperty().isEmpty());
			
			// boolean para verificar se há algum cliente selecionado na tabela
			BooleanBinding algoSelecionado = tblClientes.getSelectionModel().selectedItemProperty().isNull();
			
			//Botões que serão ativados se os campos forem preenchidos ou algum cliente for selecionado na tabela
			btnApagarCliente.disableProperty().bind(algoSelecionado);
			btnAtualizarCliente.disableProperty().bind(algoSelecionado);
			btnLimparCliente.disableProperty().bind(algoSelecionado.and(camposPreenchidos));
			
			/*Botão para registar cliente que só é ativado se os campos estiverem preenchidos e
			 *  não estiver nada selecionado na tabela
			 */
			btnSaveCliente.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
			
			/*Bind para que quando algum cliente for selecionado na tabela os text fields sejam preenchidos
			 *  com os respetivos dados do mesmo
			 */
			tblClientes.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
				if (n != null) {
					txtIdCliente.setText(String.valueOf(n.getIdCliente()));
					txtNomeCliente.setText(n.getClienteNome());
					txtEmailCliente.setText(n.getClienteEmail());
				}
			});
		}
		
    

	
}
