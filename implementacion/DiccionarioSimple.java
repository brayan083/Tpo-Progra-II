package implementacion;

import tdas.ConjuntoTDA;

public class DiccionarioSimple implements tdas.DiccionarioSimpleTDA {

	class nodo{
		int clave;
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	
	@Override
	public void inicializar() {
		
	}

	@Override
	public void agregar(int clave, int valor) {

	}

	@Override
	public void eliminar(int clave) {

	}

	@Override
	public int recuperar(int clave) {
		return clave;

	}

	@Override
	public ConjuntoTDA obtenerClaves() {
		return null;

	}

}
