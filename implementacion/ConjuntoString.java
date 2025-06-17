package implementacion;

import java.util.Random;
import tdas.ConjuntoStringTDA;

public class ConjuntoString implements ConjuntoStringTDA {

	class Nodo {
		String valor;
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
	public void agregar(String valor) {
		// Verificamos que el valor no exista para no tener duplicados.
		if (!this.pertenece(valor)) {
			Nodo nuevo = new Nodo();
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
			cantidad++;
		}
	}

	@Override
	public void sacar(String valor) {
		if (primero != null) {
			// Usamos .equals() para comparar Strings.
			// Caso 1: El elemento a sacar es el primero.
			if (primero.valor.equals(valor)) {
				primero = primero.siguiente;
				cantidad--;
			} 
			// Caso 2: El elemento está en otra parte de la lista.
			else {
				Nodo actual = primero;
				// Buscamos el nodo anterior al que queremos eliminar.
				while (actual.siguiente != null && !actual.siguiente.valor.equals(valor)) {
					actual = actual.siguiente;
				}
				
				// Si lo encontramos (no es el final de la lista), lo sacamos.
				if (actual.siguiente != null) {
					actual.siguiente = actual.siguiente.siguiente;
					cantidad--;
				}
			}
		}
	}

	@Override
	public String elegir() {
		r = new Random();
		int pos = r.nextInt(cantidad);
		Nodo actual = primero;
		while(pos > 0) {
			actual = actual.siguiente;
			pos--;
		}
		return actual.valor;
	}

	@Override
	public boolean pertenece(String valor) {
		Nodo actual = primero;
		// Recorremos la lista comparando con .equals()
		while (actual != null && !actual.valor.equals(valor)) {
			actual = actual.siguiente;
		}
		// Si actual no es null, es porque encontramos el valor.
		return actual != null;
	}

	@Override
	public boolean estaVacio() {
		return (primero == null);
	}
}