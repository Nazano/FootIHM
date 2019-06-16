package Interface;


import java.util.ArrayList;


import app.Enregistrement;
import app.Joueur;
import gestion.DataManager;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.transform.Affine;
import utils.Draw;
import utils.Fx3DGroup;

public class Soccer  {

    
    public static void Animation(DataManager dataManager,Group root3D,PerspectiveCamera camera)
    {
    	
    	ArrayList<Enregistrement> E = dataManager.getEnregisrements();
        //Create Draw 
        Draw draw = new Draw();
    	 //Add an animation timer 
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
        	
        	Fx3DGroup[] players = new Fx3DGroup[16]; // stocker les joueurs 
        	Fx3DGroup[] billboards = new Fx3DGroup[16]; // stocker les numéros 
        
        	
			@Override
			public void handle(long currentNanoTime) {
				double t = (currentNanoTime - startNanoTime )/ 50.0 / 1000000.0;
				
				// Add a player
				int index = (int)Math.round(t); // désigne l'enregistrment à sélectionner
		        
		        
				for(Joueur j : E.get(index))// parcours les joueurs dans l'enregistrements 
		        {
					
					if (players[j.getId()]!=null)
					{
						// le joueur existe déjà  : on le déplace
						
						for(int i=0 ; i<root3D.getChildren().size();i++)
						{
							Fx3DGroup player = players[j.getId()];
							Fx3DGroup billboard = billboards[j.getId()];
							
							// on parcours la liste de joueurs et on selectionne l'objet s'il exite
							if (root3D.getChildren().get(i)==player) 
							{	
								// refresh billboard 
								
								Point3D to = camera.localToScene(Point3D.ZERO);
								Point3D yDir = new Point3D(0, 1, 0);
								Point3D billboardOffset = new Point3D(0,-1,0);
								                    
								Point3D from = player.localToScene(billboardOffset);

								Affine affine = new Affine();
								affine.append(Fx3DGroup.lookAt(from,to,yDir));
								billboard.getTransforms().setAll(affine);
								
								// refresh player position
								player .set3DTranslate(j.getX_pos()-52, 0, j.getY_pos()-34);
								player .set3DRotate(0,j.getDirection()*58,0);

								
							}
						}	
						
					}else{
						// le joueur n'existe pas : on le crée
						Fx3DGroup player = draw.createPlayer(j);
						Fx3DGroup billboard = draw.createBillboard(j);
			        	players[j.getId()]= player ; 
			        	billboards[j.getId()]=billboard;
			        	root3D.getChildren().add(player);
			        	root3D.getChildren().add(billboard);
					}
						
						
		        }
				
				
			}
		}.start();
    }


    
}
