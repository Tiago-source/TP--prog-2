package controllers;




import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.Utilizador;
import javafx.scene.Node;
import service.UtilizadoresService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/
public class UtilizadoresController implements Initializable {


	 @FXML
	    private TextField txtNome;

	    @FXML
	    private TextField txtPasswd;

	    @FXML
	    private Button btnLogin;

	    @FXML
	    private Button btnRegistar;
	    

        @FXML
        private TextField txtNewNome;

        @FXML
        private TextField txtNewPasswd;

        @FXML
        private Button btnSaveNewUser;

        @FXML
        private Button btnBackLogin;

	  
	    
        private UtilizadoresService utilizadorService;

        
        
        
        /* Metodo que verifica se existe algum utilizador registado com o nome e palavra passe inseridas
         * caso não exista emite um alert com a mensagem de dados incorretos e 
         * caso exista exibe um alert com a mensagem bem vindo e redireciona o utilizador para o menu principal
         */
        @FXML
	    void login(ActionEvent event) throws IOException {
	    	
	    	String nome =txtNome.getText();
	    	String passwd =txtPasswd.getText();
		   
		    if(utilizadorService.fetchUtilizadoresPorNome(nome,passwd) != null) {
		    	
		      	FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
	            Parent home = loader.load();
	            Scene homeScene = new Scene(home);
	            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	            homeStage.setScene(homeScene);
	            homeStage.setMaximized(false);
	            homeStage.show();
		    	
		    	Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Information Dialog");
	            alert.setHeaderText(null);
	            alert.setContentText("Login Efetuado com sucesso");
	            alert.showAndWait();
	            
		    } else {
		    	
		    
		    	Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Information Dialog");
	            alert.setHeaderText(null);
	            alert.setContentText("Dados incorretos !!");
	            alert.showAndWait();
		    	
		    	
		    }
		    

	    }
        
        //Metodo para navegar para a pagina de registo de um novo utilizador
        @FXML
        void goToRegistar(ActionEvent event) throws IOException {
         	
         	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/registar.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();
        	
        	
        }

        
       
        // Metodo para regressar da pagina de registo para a pagina de login
        @FXML
        void back(ActionEvent event) throws IOException {
        	
         	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/login.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

        }

        
        // Metodo para registar um novo utilizador
        @FXML
        void registar(ActionEvent event) throws IOException {
        	
        	String nome =txtNewNome.getText();
	    	String passwd =txtNewPasswd.getText();
	    	
        	Utilizador c = new Utilizador();
        	c.setNome(nome);
        	c.setPasswd(passwd);
		    utilizadorService.guardarUtilizador(c); 
		    
		    FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/login.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Utilizador registado com sucesso");

            alert.showAndWait();
		   
        }
        
        
    
        /*Metodo utilizado ao iniciar uma nova instancia do utilizadorService
      	* o que nos permite utilizar os metodos criados nos mesmos. 
      	*/
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			utilizadorService = UtilizadoresService.getNewInstance();
	
		}
	
 
}
