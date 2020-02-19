/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;


/**
 * Un oggetto di questa classe è un risolutore del problema della più lunga
 * sottosequenza comune tra due stringhe date.
 * 
 * @author Flavio Pocari flavio.pocari@studenti.unicam.it
 *
 */
public class LCSSolver {

	private final String x;

	private final String y;

	private String solution;

	private char[] xChar;
	private char[] yChar;

	
	/**
	 * Costruisce un risolutore LCS fra due stringhe date.
	 * 
	 * @param x la prima stringa
	 * @param y la seconda stringa
	 * @throws NullPointerException se almeno una delle due stringhe passate è nulla
	 */
	public LCSSolver(String x, String y) {

		if (isNull(x) || isNull(y)) {
			throw new NullPointerException();
		}

		this.x = x;
		this.y = y;

		/*
		 * Scompongo 'x' e 'y' in un array per confrontare i caratteri attraverso gli
		 * indici della matrice.
		 */
		this.xChar = x.toCharArray();
		this.yChar = y.toCharArray();
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
	 * Risolve il problema LCS delle due stringhe di questo solver, se non è stato
	 * già risolto precedentemente. Dopo l'esecuzione di questo metodo il problema
	 * verrà considerato risolto.
	 */
	public void solve() {
		
		/*
		 * Ritorno la soluzione del metodo privato, se si utilizza 'getOneSolution' viene lanciata
		 * un eccezzione di tipo 'IllegalArgumentException' dato che devo gestire la stessa eccezzione
		 * in quel metodo.
		 */
		this.solution = this.oneSolution(x, y);
	}

	
	/**
	 * Determina se questo solver ha già risolto il problema.
	 * 
	 * @return true se il problema LCS di questo solver è già stato risolto
	 *         precedentemente, false altrimenti
	 */
	public boolean isSolved() {
		
		/*
		 * Se 'isSolved' è true, 'solution' è diverso da 'null', false altrimenti.
		 */
		return !isNull(this.solution);
	}

	
	/**
	 * Determina la lunghezza massima delle sottosequenze comuni.
	 * 
	 * @return la massima lunghezza delle sottosequenze comuni di x e y.
	 * @throws IllegalStateException se il solver non ha ancora risolto il problema
	 *                               LCS
	 */
	public int getLengthOfSolution() {

		if (!isSolved()) {
			throw new IllegalArgumentException();
		}

		int[][] matrix = getMatrix(x, y);
		
		/*
		 * Creo la matrice e ritorno la posizione [lunghezza x][lunghezza y] che coincide con la 
		 * lunghezza massima della sottosequenza.
		 */
		return matrix[x.length()][y.length()];
	}

	
	/*
	 * Metodo privato che ritorna la matrice 'traceBack'.
	 */
	private int[][] getMatrix(String x, String y) {

		/*
		 * Viene creata la matrice, con dimensione incrementata di uno rispetto alla lunghezza
		 * di 'x' e 'y' per gestire il caso base.
		 */
		int[][] matrix = new int[x.length() + 1][y.length() + 1];

		for (int indiceR = 1; indiceR < matrix.length; indiceR++) {
			for (int indiceC = 1; indiceC < y.length() + 1; indiceC++) {

				/*   
				 * Attraverso i cicli che hanno le variabili 'indiceR' e 'indiceC' dove queste stanno
				 * rispettivamente per 'riga' e 'colonna' viene creata la struttura della matrice man 
				 * mano che si entra nelle condizioni.
				 * 
				 *   A B C D A
				 * A 1 1 1 1 1
				 * C 1 1 2 2 2
				 * B 1 2 2 2 2
				 * D 1 2 2 3 3
				 * A 1 2 2 3 4 -> è la lunghezza massima della sottosequenza comune.
				 */
				if (xChar[indiceR - 1] == yChar[indiceC - 1]) {
					matrix[indiceR][indiceC] = matrix[indiceR - 1][indiceC - 1] + 1;

				} else if (matrix[indiceR - 1][indiceC] > matrix[indiceR][indiceC - 1]) {
					matrix[indiceR][indiceC] = matrix[indiceR - 1][indiceC];
					
				} else {
					matrix[indiceR][indiceC] = matrix[indiceR][indiceC - 1];
				}

			}
		}

		return matrix;
	}

	
	@Override
	public int hashCode() {
		return this.x.hashCode() + this.y.hashCode();
	}

	
	@Override
	public boolean equals(Object obj) {

		LCSSolver other = (LCSSolver) obj;

		if (this.x.equals(other.x) && this.y.equals(other.y)) {
			return true;

		} else {
			return this.y.equals(other.x) && this.x.equals(other.y);
		}

	}

	
	/**
	 * Restituisce una soluzione del problema LCS.
	 * 
	 * @return una sottosequenza di this.x e this.y di lunghezza massima
	 * @throws IllegalStateException se il solver non ha ancora risolto il problema
	 *                               LCS
	 */
	public String getOneSolution() {

		if (!isSolved()) {
			throw new IllegalArgumentException();
		}

		/*
		 * Ritorno la soluzione del metodo privato.
		 */
		return oneSolution(x, y);
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
		
		/*
		 * Se 'z' è vuoto ritorno true, dato che è sottosequenza comune.
		 */
		if (z.isEmpty()) {
			return true;
		}
		
		int firstLength = 0;
		int secondLength = 0;
		
		char[] zChar = z.toCharArray();

		/*
		 * Confronto se 'z' è una sottosequenza comune di 'x' e 'y'.
		 */
		for (int match = 0, notMatch = 0; notMatch < this.x.length() && match < z.length();) {
			
			/*
			 * Scorro il ciclo e verifico la condizione, se la condizione si avvera, incremento entrambe
			 * le variabili, altrimenti aumento solo la variabile per l'indice di confronto di 'x'.
			 */
			if (xChar[notMatch] == zChar[match]) {
				match++;
			}

			notMatch++;

			/*
			 * Assegno a 'firstLength' il valore di 'match' nel caso fosse sottosequenza.
			 */
			firstLength = match;
		}
		
		
		for (int match = 0, notMatch = 0; notMatch < this.y.length() && match < z.length();) {
			
			if (yChar[notMatch] == zChar[match]) {
				match++;
			}
			
			notMatch++;
			secondLength = match;
		}

		/*
		 * Sia 'firstLength' che 'secondLength' hanno preso il valore di 'match' utilizzato nei cicli, 
		 * e vengono confrontate con la lunghezza di 'z', se risultano entrambe uguali ritorno true, 
		 * false altrimenti.
		 */
		return (firstLength == z.length() && secondLength == z.length());
	}

	
	private String oneSolution(String x, String y) {

		/*
		 * Se 'x' o 'y' sono vuoti ritorno come soluzione la sottosequenza vuota.
		 */
		if (x.isEmpty() || y.isEmpty()) {
			return "";
		}

		int indiceR = x.length();
		int indiceC = y.length();
		
		int[][] matrix = getMatrix(x, y);

		StringBuilder solution = new StringBuilder();

		/*
		 * Si attraversa in diagonale la matrice finche il valore [indireRiga][indiceColonna] non è 0.
		 */
		while (matrix[indiceR][indiceC] != 0) {

			if (matrix[indiceR - 1][indiceC] < matrix[indiceR][indiceC] && 
					matrix[indiceR][indiceC - 1] < matrix[indiceR][indiceC]) {

				solution.insert(0, xChar[indiceR - 1]);
				
				/*
				 * Si ritorna al valore precedente spostandosi in diagonale.
				 * 
				 *     A   B   D   A
				 * A   1   1   1   1
				 * 
				 * C   1   1   1   1
				 * 
				 * B   1   2  (2)  2
				 *               \
				 * A   1   2   2  (3) -> dove si passa da 3 a 2.
				 */
				indiceR--;
				indiceC--;

			} else if (matrix[indiceR - 1][indiceC] < matrix[indiceR][indiceC]) {
				
				/*
				 * Ci si sposta sulla colonna precedente.
				 * 
				 * 	   B   A   D   A
				 * A   0   1   1   1
				 * 
				 * C   0   1   1   1
				 *             
				 * B   1   1   1   1
				 *
				 * A   1   2  (2)-(2)
				 * 
				 * Per passare i relativi test questa porzione di codice non serve, ma senza viene lanciata
				 * un eccezzione senza realizzare i dati per i grafici.
				 */
				indiceC--;

			} else {
	
				/*
				 * Altrimenti ci si sposta solamente sulla riga precedente.
				 * 
				 * 	   B   A   D   A
				 * A   0   1   1   1
				 * 
				 * C   0   1  (1)  1
				 *             |
				 * B   1   1  (1)  1
				 *
				 * A   1   2   2   2
				 */
				indiceR--;
			}
		}

		return solution.toString();
	}

	
	/*
	 * Metodo privato che determina se un parametro formale passato è 'null',
	 * anzichè scrivere sempre (... == null) nella condizione dell' if.
	 */
	private boolean isNull(Object obj) {
		return obj == null;
	}
	
}
