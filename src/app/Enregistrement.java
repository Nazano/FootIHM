package app;

import java.util.ArrayList;

public class Enregistrement extends ArrayList<Joueur> {

	public Joueur getInfoJoueur(int numero) {
		for(Joueur j : this)
			if (j.getId() == numero) return j;
		return null;
	}
}
