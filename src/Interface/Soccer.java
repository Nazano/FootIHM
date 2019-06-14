
package Interface;





import java.net.URL;
import java.util.ArrayList;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import app.Enregistrement;
import app.Joueur;
import gestion.DataManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import utils.CameraManager;
import utils.Draw;
import utils.Fx3DGroup;

public class Soccer extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
    	DataManager dm = new DataManager("2013-11-03_tromso_stromsgodset_first.csv");
		dm.findEnregistrements();
		System.out.println("fin chargement données");
		ArrayList<Enregistrement> E = dm.getEnregisrements();

        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        Pane pane3D = new Pane(root3D);
        
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
        Scene scene = new Scene(pane3D, 600, 600, true,SceneAntialiasing.BALANCED);
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.2));
        
        
        //Add an animation timer 
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
        	
        	Fx3DGroup[] players = new Fx3DGroup[16];
	
			@Override
			public void handle(long currentNanoTime) {
				double t = (currentNanoTime - startNanoTime )/ 50.0 / 1000000.0;
				
				// Add a player
				int index = (int)Math.round(t);
		        
		        
				for(Joueur j : E.get(index))
		        {
					
					if (players[j.getId()]!=null)
					{

						//players[j.getId()].set3DTranslate(t*j.getSpeed()*(j.getX_pos()-52), 0, t*50*j.getSpeed()*(j.getY_pos()-34));
						//root3D.getChildren().get(j.getId()).setTranslateX(j.getX_pos());
					}else{
						Fx3DGroup player = draw.createPlayer(j);
			        	players[j.getId()]= player ; 	
			        	root3D.getChildren().add(player);
					}
						
						
		        }
				
			}
		}.start();

        //Add the scene to the stage and show it
        primaryStage.setTitle("Soccer Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }


    // From Rahel LÃ¼thy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    
}