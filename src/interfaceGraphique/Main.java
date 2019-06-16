package interfaceGraphique;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class Main extends Application {
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
	// Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("app.fxml"));
            
            VBox root = (VBox) loader.load();
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			Main.primaryStage = primaryStage;
			primaryStage.setOnCloseRequest(confirmCloseEventHandler);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Quand l'utilisateur veut quiiter l'appli il lui montre
	 * une boite de dialogue pour confirmer
	 */
	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
		Alert confirmQuitter = new Alert(AlertType.CONFIRMATION, "Voulez-vous quitter l'application?");
		Button exitButton = (Button) confirmQuitter.getDialogPane().lookupButton(
                ButtonType.OK
        );

		confirmQuitter.setHeaderText("Quitter");
		confirmQuitter.initModality(Modality.APPLICATION_MODAL);
		confirmQuitter.initOwner(primaryStage);
		
		Optional<ButtonType> closeResponse = confirmQuitter.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }      
	};
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	public static void main(String[] args) {
		launch("Open file");
	}
}