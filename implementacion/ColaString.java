package implementacion;

import tdas.ColaStringTDA;

public class ColaString implements ColaStringTDA {

	class nodo{
		String valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private nodo ultimo;
	
	@Override
	public void inicializarCola() {

	}

	@Override
	public void acolar(String valor) {

	}

	@Override
	public void desacolar() {

	}

	@Override
	public String primero() {
		return null;
	}

	@Override
	public boolean colaVacia() {
		return false;
	}

}
