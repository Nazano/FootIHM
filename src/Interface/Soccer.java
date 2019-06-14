
package Interface;





import java.net.URL;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

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

public class Soccer extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        Pane pane3D = new Pane(root3D);

        // Load geometry
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
			URL modelUrl = this.getClass().getResource("/assets/soccer.obj");
			objImporter.read(modelUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        MeshView[] meshViews = objImporter.getImport();
        Group stade = new Group(meshViews);
        root3D.getChildren().add(stade);
        
        // Draw a line
        
        Cylinder cyl = createLine(new Point3D(5,0,0),new Point3D(-5,0,0));
        root3D.getChildren().add(cyl);
        
        
        // Draw an helix
        
        Group HelixNode = new Group();
        Point3D oldVect = new Point3D(1, 0, 0);
        for	 (int i = 0; i<100 ; i++)
        {
        	float t = i / 5.0f;
        	Point3D newVect = new Point3D(Math.cos(t),-t/5.0f,Math.sin(t));
        	// Draw a small line between the old and new 3D points
        	Cylinder cyl2 = createLine(oldVect,newVect);
        	oldVect = newVect;
        	root3D.getChildren().add(cyl2);
        }
        
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

        //Add the scene to the stage and show it
        primaryStage.setTitle("Soccer Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

}