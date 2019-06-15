package interfaceGraphique;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import gestion.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MatchView implements Initializable {
	
	private static DataManager dm;
	
	@FXML
	private Label lblMinutes, lblSecondes, lblNomMatch, lblInfoMatch, lblJoueurSelec, lblInfoJoueur;
	
	@FXML
	private Button btnArret, btnPause, btnPlay, btnVue;
	
	@FXML
	private Pane pane3D;
	
	@FXML
	private MenuItem menuItemOpen, menuItemClose, menuItemQuit, menuItemHeatMap;
	
	@FXML
	private ComboBox<VitesseLecture> comboVitLecture;
	
	@FXML
	private Slider sliderLecture;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		chargerOptionComboBox();
		
		menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				openFile();	
			}
		});
		
		menuItemQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				quitter();
			}
		});
		
		menuItemClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				fermerFichier();
			}
		});

	}
	
	public static DataManager getDataManager() {
		if(dm == null)
			throw(new NullPointerException("DataManager pas initialisé"));
		return dm;
	}
	
	private void openFile() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Choissisez un fichier de donnée CSV");
		File selectedFile = fc.showOpenDialog(Main.getPrimaryStage());
		if (selectedFile == null) {
			return;
		}
		dm = new DataManager(selectedFile);
		dm.findEnregistrements();
		chargerInfos();
	}
	
	private void chargerInfos() {
		lblNomMatch.setText(dm.getTitreMatch());
		StringBuilder sb = new StringBuilder();
		sb.append("Nombre d'enregistrements = " + dm.getEnregisrements().size() + "\n");
		sb.append("Durée du match = " + milisecToFormatTime(dm.getEnregisrements().size() * 50) + "\n");
		lblInfoMatch.setText(sb.toString());
	}
	
	private String milisecToFormatTime(long millisecondsSinceEpoch) {
		Instant instant = Instant.ofEpochMilli ( millisecondsSinceEpoch );
		ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "mm:ss" );
		String output = formatter.format ( zdt );

		return output;
	}
	
	/**
	 * Affiche une boite de dialogue avant de quitter l'application 
	 */
	private void quitter() {
		Alert confirmQuitter = new Alert(AlertType.CONFIRMATION, "Voulez-vous quitter l'application?");
		Button exitButton = (Button) confirmQuitter.getDialogPane().lookupButton(
                ButtonType.OK
        );
		confirmQuitter.setHeaderText("Quitter");
		
		Optional<ButtonType> closeResponse = confirmQuitter.showAndWait();
        if (ButtonType.OK.equals(closeResponse.get())) {
        		Platform.exit();
			System.exit(0);
        }     
	}
	
	public void fermerFichier() {
		dm = null;
		lblInfoMatch.setText("");
		lblNomMatch.setText("");
		lblSecondes.setText("00");
		lblMinutes.setText("00");
		lblInfoJoueur.setText("");
	}
	
	private void chargerOptionComboBox() {
		comboVitLecture.setItems(FXCollections.observableArrayList(
				new VitesseLecture(1),
				new VitesseLecture(2),
				new VitesseLecture(6),
				new VitesseLecture(12)
				));
	}
	
	class VitesseLecture {
		int vitesse;
		
		public VitesseLecture(int vitesse) {
			this.vitesse = vitesse;
		}
		
		@Override
		public String toString() {
			return "x" + vitesse;
		}
		
	}
}


