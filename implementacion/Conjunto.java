package implementacion;

import java.util.Random;
import tdas.ConjuntoTDA;

public class Conjunto implements ConjuntoTDA {
	
	class nodo {
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private int cantidad;
	private Random r;
	
	@Override
	public void inicializar() {

	}

	@Override
	public void agregar(int valor) {

	}

	@Override
	public boolean pertenece(int valor) {
		return false;

	}

	@Override
	public void sacar(int valor) {

	}

	@Override
	public int elegir() {
		return cantidad;

	}

	@Override
	public boolean estaVacio() {
		return false;

	}

}