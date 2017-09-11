package grandsentiers;

import java.util.*;

/**
 * Mise en oeuvre des grands entiers à l'aide d'une liste de chiffres significatifs.
 * 
 * <p>
 * 
 * Les chiffres en base 10 du grand entier sont stockés dans une liste d'entiers.
 * Afin de faciliter certaines opérations, les premiers chiffres stockés sont les
 * chiffres de <i>poids faible</i>, et les chiffres non significatifs ne sont pas stockés. <br>
 * 
 * Exemples : <br>
 * 
 * <table>
 *   <tr>
 *     <td>Nombre</td><td>Décomposition en base 10</td><td>Représentation en liste</td>
 *   </tr>
 *   <tr>
 *     <td>0</td><td>0&times;10<sup>0</sup></td><td>( 0 )</td>
 *   </tr>
 *   <tr>
 *     <td>5067</td><td>7&times;10<sup>0</sup> + 6&times;10<sup>1</sup> + 0&times;10<sup>2</sup> + 5&times;10<sup>3</sup></td><td>( 7 6 0 5 )</td>
 *   </tr>
 *   <tr>
 *     <td>123000</td><td>0&times;10<sup>0</sup> + 0&times;10<sup>1</sup> + 0&times;10<sup>2</sup> + 3&times;10<sup>3</sup> + 2&times;10<sup>4</sup> + 1&times;10<sup>5</sup></td><td>( 0 0 0 3 2 1 )</td>
 *   </tr>
 *   <tr>
 *     <td>000123</td><td>3&times;10<sup>0</sup> + 2&times;10<sup>1</sup> + 1&times;10<sup>2</sup> + 0&times;10<sup>3</sup> + 0&times;10<sup>4</sup> + 0&times;10<sup>5</sup></td><td>( 3 2 1 )</td>
 *   </tr>
 * </table>
 * 
 * <p>
 * 
 * Grâce à cette représentation inversée, l’indice d’un chiffre dans la liste correspond toujours exactement à son poids dans la décomposition.
 *
 */
public class ListeGrandEntier implements GrandEntier {

	/**
	 * Liste de chiffres significatifs en base 10 dans l'ordre croissant des poids.
	 */
	private List<Integer> chiffres;
		
	/**
	 * Crée un grand entier à partir d'une valeur de type <tt>int</tt>. <br>
	 * <b>Attention</b> : on ne tient compte que de la valeur absolue du paramètre.
	 * @param i valeur du grand entier à créer
	 */
	public ListeGrandEntier(int i) {
		if (i<0) i = -i;   // on code la valeur absolue
		this.chiffres = new ArrayList<>();
		do {
			this.chiffres.add(i%10); // chiffre de poids faible
			i = i/10; // suppression du chiffee de poids faible
		}
		while (i>0); // tant qu'il y a un chiffre significatif
	}
	
	/**
	 * Crée un grand entier de valeur 0.
	 */
	public ListeGrandEntier() {
		this(0);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res = "";
		for (Integer i : this.chiffres)
			res = i+res;
		return res;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof GrandEntier)) return false;
		return this.compareTo((GrandEntier)o)==0;
	}

	/**
	 * @see grandsentiers.GrandEntier#size()
	 */
	@Override
	public int size() {
		return this.chiffres.size();
	}

	/**
	 * @see grandsentiers.GrandEntier#digit(int)
	 */
	@Override
	public int digit(int i) {
		if (i >= this.chiffres.size()) return 0;
		return this.chiffres.get(i);
	}

	/**
	 * @see grandsentiers.GrandEntier#compareTo(grandsentiers.GrandEntier)
	 */
	@Override
	public int compareTo(GrandEntier g) {
		// test la taille de l'entier
		if (this.size() > g.size()) {
			return 1;
		}
		if (this.size() < g.size()) {
			return -1;
		}
		
		// si taille egale on teste 1 par 1
		for(int i = this.size()-1; i>=0; i--) {
			if(this.digit(i) != g.digit(i)) {
				//chiffre different
				if (this.digit(i) > g.digit(i)) {
					return 1;
				}
				else {
					return -1;
				}
			}
		}
		// fin de la boucle tout les chiffres sont identiques
		return 0;
	}

	/**
	 * @see grandsentiers.GrandEntier#add(grandsentiers.GrandEntier)
	 */
	@Override
	public GrandEntier add(GrandEntier g) {
		
		ListeGrandEntier res = new ListeGrandEntier();
		int retenu = 0;
		res.chiffres.clear();
		// boucle pour this plus grand que g
		if (this.size() >= g.size() ) {
			for (int i=0; i<g.size();i++){
				res.chiffres.add((this.digit(i) + g.digit(i) + retenu) % 10); 
				
				// on regarde s'il y a une retenu
				if (this.digit(i) + g.digit(i) + retenu > 9) {
					retenu = 1;
				}
				else {
					retenu = 0;
				}
			}
			
			// on ajoute les chiffres restants
			for(int i= g.size(); i<this.size(); i++) {
				res.chiffres.add((this.digit(i) + retenu) % 10);
				if (this.digit(i) + retenu > 9) {
					retenu = 1;
				}
				else {
					retenu = 0;
				}
			}
		}
		
		//boucle pour g plus grand que this
		else {
			for (int i=0; i<this.size();i++){
				res.chiffres.add((this.digit(i) + g.digit(i) + retenu) % 10);

				// on regarde s'il y a une retenu
				if (this.digit(i) + g.digit(i) + retenu > 9) {
					retenu = 1;
				}
				else {
					retenu = 0;
				}
			}
			
			// on ajoute les chiffres restants
			for(int i= this.size(); i<g.size(); i++) {
				res.chiffres.add((g.digit(i) + retenu) % 10);
				if (g.digit(i) + retenu > 9) {
					retenu = 1;
				}
				else {
					retenu = 0;
				}
			}
		}
		// ajout de la derniere retenu
		if (retenu == 1) {
			res.chiffres.add(1);
		}
		
		return res; // à modifier...
	}

	/**
	 * @see grandsentiers.GrandEntier#mult(grandsentiers.GrandEntier)
	 */
	@Override
	public GrandEntier mult(GrandEntier g) {
		
		ListeGrandEntier tempo = new ListeGrandEntier();
		GrandEntier res = new ListeGrandEntier();
		int retenu = 0;
		for(int i=0; i<g.size(); i++ ) {
			tempo = new ListeGrandEntier();
			tempo.chiffres.clear();
			retenu = 0;
			for(int j=0; j<i; j++) {
				tempo.chiffres.add(0);
			}
			for(int j=0; j<this.size(); j++) {
				tempo.chiffres.add((g.digit(i)*this.digit(j)+retenu)%10);
				retenu = (g.digit(i)*this.digit(j)+retenu)/10;
			}
			if (retenu != 0) {
				tempo.chiffres.add(retenu);
			}
			res = res.add(tempo);
		}
		
		
		return res;
	}
	
	/**
	 * Crée un entier correspondant à la factorielle du paramètre fourni. 
	 * @param g grand entier dont on souhaite calculer la factorielle
	 * @return grand entier correspondant à la factorielle de <tt>g</tt>
	 */
	public static GrandEntier factorielle (GrandEntier g) {
		GrandEntier res = new ListeGrandEntier(1);
		GrandEntier tempo = new ListeGrandEntier(1);
		GrandEntier fixe = new ListeGrandEntier(1);
		while (g.compareTo(tempo) != -1) {
			res = res.mult(tempo);
			tempo = tempo.add(fixe);
		}
		return res; // à modifier...
	}

}
