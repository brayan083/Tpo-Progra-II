package implementacion;

import tdas.ColaPrioridadTDA;

public class ColaPrioridad implements ColaPrioridadTDA {

	class Nodo { 
		int prioridad;
		int valor;
		Nodo siguiente;
	}

	private Nodo primero;

	@Override
	public void inicializarCola() {
		primero = null;
	}

	@Override
	public void acolarPrioridad(int valor, int prioridad) {
		Nodo nuevo = new Nodo();
		nuevo.valor = valor;
		nuevo.prioridad = prioridad;

		// Caso 1: La cola está vacía o el nuevo elemento tiene mayor prioridad (un
		// valor de prioridad menor)
		// que el primer elemento actual. En ambos casos, el nuevo nodo va al principio.
		if (primero == null || prioridad < primero.prioridad) {
			nuevo.siguiente = primero;
			primero = nuevo;
		}
		// Caso 2: El nuevo elemento va en el medio o al final de la cola.
		else {
			Nodo actual = primero;
			// Buscamos el lugar correcto para insertar.
			// Avanzamos mientras no lleguemos al final y la prioridad del siguiente
			// sea menor que la prioridad del nuevo elemento.
			while (actual.siguiente != null && actual.siguiente.prioridad < prioridad) {
				actual = actual.siguiente;
			}

			// Insertamos el nuevo nodo después de 'actual'.
			nuevo.siguiente = actual.siguiente;
			actual.siguiente = nuevo;
		}
	}

	@Override
	public void desacolar() {
		// Como la cola está ordenada, el de mayor prioridad siempre es el primero.
		// El TDA garantiza que la cola no está vacía.
		primero = primero.siguiente;
	}

	@Override
	public int primero() {
		// El TDA garantiza que la cola no está vacía.
		return primero.valor;
	}

	@Override
	public int prioridad() {
		// El TDA garantiza que la cola no está vacía.
		return primero.prioridad;
	}

	@Override
	public boolean colaVacia() {
		return (primero == null);
	}
}