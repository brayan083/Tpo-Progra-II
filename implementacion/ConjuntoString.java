package implementacion;

import java.util.Random;
import tdas.ConjuntoStringTDA;

public class ConjuntoString implements ConjuntoStringTDA {

	class nodo {
		String valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private int cantidad;
	private Random r;
	
	@Override
	public void inicializar() {

	}

	@Override
	public void agregar(String valor) {

	}

	@Override
	public void sacar(String valor) {

	}

	@Override
	public String elegir() {
		return null;

	}

	@Override
	public boolean pertenece(String valor) {
		return false;

	}

	@Override
	public boolean estaVacio() {
		return false;

	}

}
