package implementacion;

import tdas.ColaStringTDA;

public class ColaString implements ColaStringTDA {

	class Nodo {
		String valor;
		Nodo siguiente;
	}
	
	private Nodo primero;
	private Nodo ultimo;
	
	@Override
	public void inicializarCola() {
		primero = null;
		ultimo = null;
	}

	@Override
	public void acolar(String valor) {
		Nodo nuevo = new Nodo();
		nuevo.valor = valor;
		nuevo.siguiente = null; // El nuevo nodo siempre va al final.
		
		// Si la cola está vacía, el nuevo nodo es tanto el primero como el último.
		if (ultimo != null) {
			ultimo.siguiente = nuevo;
		} else {
			primero = nuevo;
		}
		// En cualquier caso, el puntero al último ahora es el nuevo nodo.
		ultimo = nuevo;
	}

	@Override
	public void desacolar() {
		// El TDA garantiza que la cola no está vacía al llamar a este método.
		primero = primero.siguiente;
		
		// Si al desacolar la cola queda vacía, también debemos actualizar el puntero 'ultimo'.
		if (primero == null) {
			ultimo = null;
		}
	}

	@Override
	public String primero() {
		// El TDA garantiza que la cola no está vacía.
		return primero.valor;
	}

	@Override
	public boolean colaVacia() {
		return (primero == null);
	}
}