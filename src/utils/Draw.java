package utils;

import java.net.URL;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import app.Joueur;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Draw {
	
	

	
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
	
	public  Fx3DGroup createField()
	{
		 ObjModelImporter objImporter = new ObjModelImporter();
	        try {
				URL modelUrl = this.getClass().getResource("/assets/soccer.obj");
				objImporter.read(modelUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

	        	
	        MeshView[] meshViews = objImporter.getImport();
	        Fx3DGroup stade = new Fx3DGroup(meshViews);
			return stade;
	}
	
	public Fx3DGroup createPlayer(Joueur j)
	{
		ObjModelImporter objImporter = new ObjModelImporter();
		try {
			URL modelUrl = this.getClass().getResource("/assets/player.obj");
			objImporter.read(modelUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
        MeshView[] meshViews = objImporter.getImport();
        
        //Create Fx3DGroup
        Fx3DGroup player= new Fx3DGroup(meshViews); 
        
        //Initilaze the player position
        player.set3DRotate(0, j.getDirection()*58, 0);
    	player.set3DTranslate(j.getX_pos()-52, 0, j.getY_pos()-34);
     
       // At the creation of the player set billboard
        Box boxBB = new Box(1, 1, 0.001);
        Fx3DGroup billboard = new Fx3DGroup(boxBB);
        billboard.useAffine();

        player.getChildren().add(billboard);
        
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/soccer/09.png")));
        boxBB.setMaterial(material);
      
       
    	
        return player;
	}

	

}
