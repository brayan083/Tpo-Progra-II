package implementacion;

import tdas.ConjuntoTDA;
import tdas.DiccionarioSimpleTDA;

public class DiccionarioSimple implements DiccionarioSimpleTDA {

	class Nodo { 
		int clave;
		int valor;
		Nodo siguiente;
	}
	
	private Nodo primero;
	
	/**
	 * Devuelve el nodo que contiene una clave. Si no lo encuentra, devuelve null.
	 * Este es un método privado de ayuda (helper method).
	 */
	private Nodo nodoConClave(int clave) {
		Nodo actual = primero;
		while (actual != null && actual.clave != clave) {
			actual = actual.siguiente;
		}
		return actual;
	}
	
	@Override
	public void inicializar() {
		primero = null;
	}

	@Override
	public void agregar(int clave, int valor) {
		// Buscamos si la clave ya existe.
		Nodo nodo = this.nodoConClave(clave);
		
		if (nodo == null) {
			// La clave no existe, creamos un nuevo nodo y lo agregamos al principio.
			Nodo nuevo = new Nodo();
			nuevo.clave = clave;
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
		} else {
			// La clave ya existe, solo actualizamos el valor.
			nodo.valor = valor;
		}
	}

	@Override
	public void eliminar(int clave) {
		if (primero != null) {
			// Caso 1: El elemento a eliminar es el primero.
			if (primero.clave == clave) {
				primero = primero.siguiente;
			}
			// Caso 2: El elemento está en otra parte.
			else {
				Nodo actual = primero;
				while (actual.siguiente != null && actual.siguiente.clave != clave) {
					actual = actual.siguiente;
				}
				
				if (actual.siguiente != null) {
					actual.siguiente = actual.siguiente.siguiente;
				}
			}
		}
	}

	@Override
	public int recuperar(int clave) {
		// El TDA garantiza que la clave existe.
		Nodo nodo = this.nodoConClave(clave);
		return nodo.valor;
	}

	@Override
	public ConjuntoTDA obtenerClaves() {
		ConjuntoTDA claves = new Conjunto();
		claves.inicializar();
		
		Nodo actual = primero;
		while (actual != null) {
			claves.agregar(actual.clave);
			actual = actual.siguiente;
		}
		
		return claves;
	}
}