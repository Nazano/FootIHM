package interfaceGraphique;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import app.gestion.DataManager;
import app.utils.Enregistrement;
import app.utils.Joueur;
import interfaceGraphique.utils.CameraManager;
import interfaceGraphique.utils.Draw;
import interfaceGraphique.utils.Fx3DGroup;
import interfaceGraphique.utils.Joueur3D;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class MatchView implements Initializable {
	
	private DataManager dm;
	private ArrayList<Joueur3D> players = new ArrayList<Joueur3D>();
	private AnimationTimer anim;
	private Group root3D; 
	private int speed;
	
	
	@FXML
	private Label lblMinutes, lblNomMatch, lblInfoMatch, lblJoueurSelec, lblInfoJoueur;
	
	@FXML
	private Button btnArret, btnPause, btnPlay, btnVue;
	
	@FXML
	private Pane pane3D;
	
	@FXML
	private AnchorPane anchor3D;
	
	@FXML
	private VBox vBox;
	
	@FXML
	private MenuItem menuItemOpen, menuItemClose, menuItemQuit, menuItemHeatMap;
	
	@FXML
	private ComboBox<VitesseLecture> comboVitLecture;
	
	@FXML
	private Slider sliderLecture;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		chargerOptionComboBox();
		
		//Create a Pane et graph scene root for the 3D content
        root3D = new Group();
        
        //Create Draw 
        Draw draw = new Draw(); 
       
        // Load geometry
        Fx3DGroup field = draw.createField();
        root3D.getChildren().add(field);

        
        // Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane3D, root3D);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);

        // Create scene
        
        SubScene subscene = new SubScene(root3D, 600, 600, true,SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.gray(0.2));
        pane3D.getChildren().add(subscene);
        subscene.heightProperty().bind(pane3D.heightProperty());
        subscene.widthProperty().bind(pane3D.widthProperty());
        

        
		menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				openFile();	
				animation(camera, root3D);
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
		
		
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!Objects.isNull(dm))
				{ 
					if(Objects.isNull(anim))
						animation(camera, root3D);
					if (!comboVitLecture.getSelectionModel().isEmpty())
					{
						speed = comboVitLecture.getSelectionModel().getSelectedItem().getVitesse();
						System.out.println(speed);
					}else{
						System.out.println("comboxvide");
						speed = 1;
					}
						
					anim.start();
				}		
				System.out.println("Demarrer");
			}

			
		});
		
		btnPause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Pause");
				if(!Objects.isNull(dm))
					anim.stop();
			}
		});
		
		btnArret.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Arret Simulation");
				if(!Objects.isNull(dm))
					arret();
			}
		});
		
		sliderLecture.valueProperty().addListener(n -> {
			if(sliderLecture.getValue() == sliderLecture.getMax())
				arret();
		});
	

	comboVitLecture.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						speed = comboVitLecture.getSelectionModel().getSelectedItem().getVitesse();
						System.out.println("X" + speed);
					}
				});
	
/*	sliderLecture.valueProperty().addListener(new ChangeListener<Number>() {
            
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				
			}
        });
		
	*/	
		
	}
	
	public DataManager getDataManager() {
		if(dm == null)
			throw(new NullPointerException("DataManager pas initialis√©"));
		return dm;
	}
	
	private void openFile() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(FileSystems.getDefault().getPath(".").toAbsolutePath().toFile());
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Choissisez un fichier de donn√©e CSV");
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
		sb.append("Dur√©e du match = " + milisecToFormatTime(dm.getEnregisrements().size() * 50) + "\n");
		lblInfoMatch.setText(sb.toString());
	}

	
	public String milisecToFormatTime(long millisecondsSinceEpoch) {
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
	
	public void animation(PerspectiveCamera camera, Group root3D) {
		final long startNanoTime = System.nanoTime();

		ArrayList<Enregistrement> E = dm.getEnregisrements();
		double tMax = E.size()*50;
		sliderLecture.setMajorTickUnit(500);
		sliderLecture.setMax(tMax);
		anim = new AnimationTimer() {

			@Override
			public void handle(long currentNanoTime) {
				double raw_time = (currentNanoTime - startNanoTime) / 1000000.0;
				double t = (currentNanoTime - startNanoTime)/ 50.0 / 1000000.0;
				int index = (int) Math.round(t); // dÈsigne l'enregistrment ‡ sÈlectionner
				
				lblMinutes.setText(new MatchView().milisecToFormatTime(Double.valueOf(raw_time).longValue()));
				if(sliderLecture.isValueChanging())
					index = (int) sliderLecture.getValue()/50;
				else 
					sliderLecture.setValue(raw_time);
				
				StringBuilder sb = new StringBuilder();
				for(Joueur3D j : players)
					sb.append(j.toString());
				lblInfoJoueur.setText(sb.toString());
				
				
				int indice = index*speed ;
				for (Joueur j : E.get(indice))// parcours les joueurs dans l'enregistrements
				{
					if (players.contains(j)) { // le joueur existe dÈj‡ : on le dÈplace
						Joueur3D j3D = players.stream().filter(j3 -> j3.equals(j)).findFirst().get();
						j3D.updateBillboard();
						j3D.updateJoueur3D(j);
					} else // le joueur n'existe pas : on le crÈe
						players.add(new Joueur3D(j, camera, root3D));

				}
			}
		};
	}
	
	public void fermerFichier() {
		dm = null;
		lblInfoMatch.setText("");
		lblNomMatch.setText("");
		lblMinutes.setText("00:00");
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
	
	public void arret()
	{

		anim.stop();
	
		for(Joueur3D j : players)		
		{
			j.getMesh().getChildren().clear();
			j.getBillboard().getChildren().clear();
		}
		
		lblInfoJoueur.setText("");
		players.clear();
		sliderLecture.setValue(0);
		anim =null;
		
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

		public int getVitesse() {
			return vitesse;
		}
		
		
	}
}


