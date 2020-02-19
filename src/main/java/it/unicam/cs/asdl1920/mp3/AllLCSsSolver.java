/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Un oggetto di questa classe è un risolutore del problema di trovare
 * <b>tutte</b> le più lunghe sottosequenze comuni tra due stringhe date.
 * 
 * @author Flavio Pocari flavio.pocari@studenti.unicam.it
 *
 */
public class AllLCSsSolver {

	private final String x;

	private final String y;

	private LCSSolver lcsSolver;

	
	/**
	 * Costruisce un risolutore All LCS fra due stringhe date.
	 * 
	 * @param x la prima stringa
	 * @param y la seconda stringa
	 * @throws NullPointerException se almeno una delle due stringhe passate è nulla
	 */
	public AllLCSsSolver(String x, String y) {

		if (isNull(x) || isNull(y)) {
			throw new NullPointerException();
		}

		this.x = x;
		this.y = y;

		/*
		 * Creo un LCSSolver da utilizzare nell' AllLCSsSolver.
		 */
		lcsSolver = new LCSSolver(x, y);
	}

	
	/**
	 * @return the string x
	 */
	public String getX() {
		return x;
	}

	
	/**
	 * @return the string y
	 */
	public String getY() {
		return y;
	}

	
	/**
	 * Risolve il problema di trovare tutte le LCSs delle due stringhe di questo
	 * solver, se non è stato già risolto precedentemente. Dopo l'esecuzione di
	 * questo metodo il problema verrà considerato risolto.
	 */
	public void solve() {
		lcsSolver.solve();
	}

	
	/**
	 * Determina se questo solver ha già risolto il problema.
	 * 
	 * @return true se il problema LCS di questo solver è già stato risolto
	 *         precedentemente, false altrimenti
	 */
	public boolean isSolved() {
		return lcsSolver.isSolved();
	}

	
	/**
	 * Determina la lunghezza massima delle sottosequenze comuni.
	 * 
	 * @return la massima lunghezza delle sottosequenze comuni di this.x e this.y.
	 * @throws IllegalStateException se il solver non ha ancora risolto il problema
	 *                               LCS
	 */
	public int getLengthOfSolutions() {

		if (!lcsSolver.isSolved()) {
			throw new IllegalArgumentException();
		}

		return lcsSolver.getLengthOfSolution();
	}

	
	/**
	 * Restituisce la soluzione del problema di tutte le LCSs.
	 * 
	 * @return un insieme che contiene tutte le sottosequenze di this.x e this.y di
	 *         lunghezza massima
	 * @throws IllegalStateException se il solver non ha ancora risolto il problema
	 *                               All LCSs
	 */
	public Set<String> getSolutions() {

		if (!lcsSolver.isSolved()) {
			throw new IllegalArgumentException();
		}
		
		Set<String> allLCS = allLCS(x, y, x.length(), y.length());
		
		/*
		 * Rendo iterabile il Set che contiene tutte le possibili sottosequenze e prendo il primo
		 * elemento ipotizzandolo come quello di lunghezza massima.
		 */
		Iterator<String> elements = allLCS.iterator();
		String maxLength = elements.next();
		
		while (elements.hasNext()) {
			
			String tmpMax = elements.next();
			
			/*
			 * Prendo un nuovo elemento e verifico la sua lunghezza, cosi da avere un nuovo max.
			 */
			if (maxLength.length() < tmpMax.length()) {
				maxLength = tmpMax;
			}
			
		}

		
		Set<String> maxLengthElements = new HashSet<String>();
		
		for (String tmp : allLCS) {
			
			/*
			 * Costruisco il nuovo Set e verifico la lunghezza massima trovata in precedenza
			 * con tutti gli elementi, cosi da aggiungere solo quelli che verificano la
			 * condizione.
			 */
			if (tmp.length() == maxLength.length()) {
				maxLengthElements.add(tmp);
			}
		}
		
		return maxLengthElements;
	}
	
	
	/*
	 * Metodo privato che ritorna un Set con tutte le possibili combinazioni, eccetto duplicati.
	 */
	private Set<String> allLCS(String x, String y, int indiceR, int indiceC) {

		char[] xChar = x.toCharArray();
		char[] yChar = y.toCharArray();

		/*
		 * Creo la matrice.
		 */
		int[][] matrice = new int[indiceR + 1][indiceC + 1];

		/*
		 * Costruisco un Set che conterra tutte le possibili sottosequenze.
		 */
		Set<String> allLCS = new HashSet<>();

		/*
		 * Se xLength oppure yLength arrivano/hanno a lunghezza 0, si inserisce la
		 * sottosequenza comune vuota: "" e si ritora la Set.
		 */
		if (indiceR == 0 || indiceC == 0) {
			allLCS.add("");
			return allLCS;
		}

		if (xChar[indiceR - 1] == yChar[indiceC - 1]) {

			/*
			 * Se gli ultimi caratteri di 'x' e 'y' combaciano li ignoro e procedo a trovare
			 * tutte le LCS della sottosequenza x[0.. xlength - 2] e y[0.. yLength - 2] dove vengono
			 * aggiunti al Set.
			 */
			Set<String> tmp = allLCS(x, y, indiceR - 1, indiceC - 1);
			
			/* 
			 * Aggiungo i caratteri seguenti come possibili sottosequenze di una
			 * sottostringa x[0.. xlength - 2] e y[0.. yLength - 2]
			 */
			for (String str : tmp) {
				allLCS.add(str + xChar[indiceR - 1]);
			}

		} else {
			
			/*
			 * Se gli utlimi caratteri di 'x' e 'y' non coincidono si procede a percorrere
			 * la matrice verso destra ricorrendo x[0.. xlength - 2] e y[0.. yLength - 1].
			 */
			if (matrice[indiceR - 1][indiceC] >= matrice[indiceR][indiceC - 1]) {
				allLCS = allLCS(x, y, indiceR - 1, indiceC);
			}
			
			/*
			 * Si percorre la matrice verso sinistra ricorrendo x[0.. xlength - 1] e y[0..
			 * yLength - 2].
			 */
			if (matrice[indiceR][indiceC - 1] >= matrice[indiceR - 1][indiceC]) {
				Set<String> tmp = allLCS(x, y, indiceR, indiceC - 1);

				/*
				 * Aggiungo al Set principale il Set temporaneo nel caso in cui si ha:
				 * matrice[xlength - 1][yLength] uguale a matrice[xlength][yLength - 1].
				 */
				allLCS.addAll(tmp);
			}
		}

		/*
		 * Ritorno il Set che contiene le sottosequenze di 'x' e 'y'.
		 */
		return allLCS;
	}

	
	/**
	 * Determina se una certa stringa è una sottosequenza comune delle due stringhe
	 * di questo solver.
	 * 
	 * @param z la string da controllare
	 * @return true se z è sottosequenza di this.x e di this.y, false altrimenti
	 * @throws NullPointerException se z è null
	 */
	public boolean isCommonSubsequence(String z) {

		if (isNull(z)) {
			throw new NullPointerException();
		}

		return lcsSolver.isCommonSubsequence(z);
	}

	
	/**
	 * Determina se tutte le stringhe di un certo insieme hanno tutte la stessa
	 * lunghezza e sono sottosequenze di entrambe le stringhe di questo solver.
	 * 
	 * @param sequences l'insieme di stringhe da testare
	 * @return true se tutte le stringe in sequences hanno la stessa lunghezza e
	 *         sono sottosequenze di this.x e this.y, false altrimenti
	 * @throws NullPointerException se l'insieme passato è nullo.
	 */
	public boolean checkLCSs(Set<String> sequences) {

		if (isNull(sequences)) {
			throw new NullPointerException();
		}

		if (sequences.isEmpty()) {

			/*
			 * Se la sequences passata è vuota, ritorno true dato che è sicuramente una
			 * sottosequenza.
			 */
			return true;
		}

		/*
		 * Variabili che determinano il risultato del metodo 'CheckLCSs'.
		 */
		boolean firstCon = false;
		boolean secondCon = false;

		if (sequences.size() == 1) {
			firstCon = true;
		}

		Iterator<String> elements = sequences.iterator();
		String firstEl = elements.next();

		int count = 1;

		while (elements.hasNext()) {

			String element = elements.next();

			/*
			 * Verifico che la lunghezza di 'element' sia uguale a quella del primo
			 * elemento, se è cosi aumento un contatore.
			 */
			if (element.length() == firstEl.length()) {

				count++;

				/*
				 * Se il contatore è uguale alla lunghezza della sequenza passata, la prima
				 * condizione diventa 'true' dato che tutti gli elementi hanno la stessa
				 * lunghezza.
				 */
				if (count == sequences.size()) {
					firstCon = true;
				}
			}

		}

		/*
		 * Creo un Set che contiene tutte le sottosequenze di lunghezza massima.
		 */
		Set<String> maxLCSLength = this.getSolutions();

		/*
		 * Se 'sequences' contiene la sottosequenza "" questa viene aggiunta anche al
		 * Set maxLCSLength.
		 */
		if (sequences.contains("")) {
			maxLCSLength.add("");
		}

		/*
		 * Se maxLCSLength contiene tutte le sequenze di 'sequences' la seconda
		 * condizione diventa vera.
		 */
		if (maxLCSLength.containsAll(sequences)) {
			secondCon = true;
		}

		return firstCon && secondCon;
	}

	
	/*
	 * Metodo privato che determina se un parametro formale passato è 'null',
	 * anzichè scrivere sempre (... == null) nella condizione dell' if.
	 */
	private boolean isNull(Object obj) {
		return obj == null;
	}

}
