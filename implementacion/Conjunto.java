package implementacion;

import java.util.Random;
import tdas.ConjuntoTDA;

public class Conjunto implements ConjuntoTDA {
	
	class Nodo { // El enunciado pide respetar la forma del nodo 
		int valor;
		Nodo siguiente;
	}
	
	private Nodo primero;
	private int cantidad;
	private Random r;
	
	@Override
	public void inicializar() {
		primero = null;
		cantidad = 0;
		r = new Random();
	}

	@Override
	public void agregar(int valor) {
		// Para ser un conjunto, no debe permitir valores duplicados.
		// Primero verificamos que el valor no exista. 
		if (!this.pertenece(valor)) {
			Nodo nuevo = new Nodo();
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
			cantidad++;
		}
	}

	@Override
	public boolean pertenece(int valor) {
		Nodo actual = primero;
		while (actual != null && actual.valor != valor) {
			actual = actual.siguiente;
		}
		return actual != null;
	}

	@Override
	public void sacar(int valor) {
		if (primero != null) {
			// Caso 1: El elemento a sacar es el primero de la lista.
			if (primero.valor == valor) {
				primero = primero.siguiente;
				cantidad--;
			} 
			// Caso 2: El elemento está en otro lugar de la lista.
			else {
				Nodo actual = primero;
				while (actual.siguiente != null && actual.siguiente.valor != valor) {
					actual = actual.siguiente;
				}
				
				// Si encontramos el nodo, lo eliminamos.
				if (actual.siguiente != null) {
					actual.siguiente = actual.siguiente.siguiente;
					cantidad--;
				}
			}
		}
	}

	@Override
	public int elegir() {
		// La forma más simple de "elegir" es devolver el primer elemento.
		// El TDA solo requiere que el conjunto no esté vacío.
		return primero.valor;
	}

	@Override
	public boolean estaVacio() {
		return (primero == null);
	}
}