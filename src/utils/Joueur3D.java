package utils;

import java.net.URL;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import app.Joueur;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Affine;

public class Joueur3D extends Joueur{

	Fx3DGroup mesh, billboard;
	PerspectiveCamera camera;
	
	public Joueur3D(Joueur j, PerspectiveCamera camera, Group root3D) {
		super(j);
		this.camera = camera;
		
		importerModele3D();
		initialiserPosition();
		creerBillboard();
		
		root3D.getChildren().add(mesh);
    	root3D.getChildren().add(billboard);
	}
	
	private void importerModele3D() {
		ObjModelImporter objImporter = new ObjModelImporter();
		try {
			URL modelUrl = this.getClass().getResource("/assets/player.obj");
			objImporter.read(modelUrl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		mesh = new Fx3DGroup(objImporter.getImport());	
	}
	
	private void initialiserPosition() {
		mesh.set3DRotate(0, direction * 58, 0);
		mesh.set3DTranslate(x_pos - 52, 0, y_pos - 34);
	}
	
	private void creerBillboard() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(new Image(
				getClass().getResourceAsStream(
						chargerImageBillboard()
				)));
		
		Box boxBB = new Box(1, 1, 0.001);
		boxBB.setCullFace(CullFace.FRONT);
		boxBB.setMaterial(material);

		billboard = new Fx3DGroup(boxBB);		
        billboard.useAffine();
        billboard.set3DTranslate(x_pos-52, 0, y_pos-34); 
	}
	
	private String chargerImageBillboard() {
		String fileName = "/assets/soccer/" ;
		if(id < 10)
			fileName = fileName + "0" + id + ".png";
		else
			fileName = fileName + id + ".png";
		
		return fileName;
	}
	
	public void updateBillboard() {
		Point3D yDir = new Point3D(0, 1, 0);
		Point3D billboardOffset = new Point3D(0,-1,0);
		Point3D to = camera.localToScene(Point3D.ZERO);           
		Point3D from = mesh.localToScene(billboardOffset);

		Affine affine = new Affine();
		affine.append(Fx3DGroup.lookAt(from,to,yDir));
		billboard.getTransforms().setAll(affine);
	}
	
	public void updateJoueur3D(Joueur j) {
		this.updateInfos(j);
		mesh.set3DTranslate(x_pos - 52, 0, y_pos - 34);
		mesh.set3DRotate(0, direction * 58, 0);
	}

	public Fx3DGroup getMesh() {
		return mesh;
	}

	public Fx3DGroup getBillboard() {
		return billboard;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
