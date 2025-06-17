package implementacion;

import tdas.ConjuntoStringTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class DiccionarioSimpleString implements DiccionarioSimpleStringTDA {

	class Nodo { // El enunciado pide respetar la forma del nodo
		String periodo;
		DiccionarioSimpleTDA precipitacionesMes;
		Nodo siguiente;
	}
	
	private Nodo primero;
	
	/**
	 * Método de ayuda privado para encontrar un nodo por su clave (periodo).
	 * Devuelve null si no lo encuentra.
	 */
	private Nodo nodoConClave(String clave) {
		Nodo actual = primero;
		while (actual != null && !actual.periodo.equals(clave)) {
			actual = actual.siguiente;
		}
		return actual;
	}
	
	@Override
	public void inicializarDiccionario() {
		primero = null;
	}

	@Override
	public void agregar(String periodo, int dia, int cantidad) {
		Nodo nodo = this.nodoConClave(periodo);
		
		if (nodo == null) {
			// El periodo no existe, hay que crearlo.
			// 1. Creamos el diccionario de precipitaciones para este periodo.
			DiccionarioSimpleTDA nuevoDiccionarioPrecipitaciones = new DiccionarioSimple();
			nuevoDiccionarioPrecipitaciones.inicializar();
			nuevoDiccionarioPrecipitaciones.agregar(dia, cantidad);
			
			// 2. Creamos el nodo principal y lo enlazamos.
			Nodo nuevoNodo = new Nodo();
			nuevoNodo.periodo = periodo;
			nuevoNodo.precipitacionesMes = nuevoDiccionarioPrecipitaciones;
			nuevoNodo.siguiente = primero;
			primero = nuevoNodo;
		} else {
			// El periodo ya existe, solo agregamos la medición a su diccionario.
			nodo.precipitacionesMes.agregar(dia, cantidad);
		}
	}

	@Override
	public void eliminar(String periodo) {
		if (primero != null) {
			if (primero.periodo.equals(periodo)) {
				primero = primero.siguiente;
			} else {
				Nodo actual = primero;
				while (actual.siguiente != null && !actual.siguiente.periodo.equals(periodo)) {
					actual = actual.siguiente;
				}
				if (actual.siguiente != null) {
					actual.siguiente = actual.siguiente.siguiente;
				}
			}
		}
	}

	@Override
	public DiccionarioSimpleTDA recuperar(String periodo) {
		// El TDA garantiza que la clave existe.
		Nodo nodo = this.nodoConClave(periodo);
		return nodo.precipitacionesMes;
	}

	@Override
	public ConjuntoStringTDA claves() {
		ConjuntoStringTDA c = new ConjuntoString();
		c.inicializar();
		
		Nodo actual = primero;
		while(actual != null) {
			c.agregar(actual.periodo);
			actual = actual.siguiente;
		}
		return c;
	}
}