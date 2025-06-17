package implementacion;

import tdas.ColaPrioridadTDA;

public class ColaPrioridad implements ColaPrioridadTDA {
	
	class nodo {
		int prioridad;
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	
	@Override
	public void inicializarCola() {
		
	}


	@Override
	public void acolarPrioridad(int valor, int prioridad) {

	}


	@Override
	public void desacolar() {
		
	}


	@Override
	public int primero() {
		return 0;
		
	}


	@Override
	public int prioridad() {
		return 0;
		
	}


	@Override
	public boolean colaVacia() {
		return false;
		
	}

}