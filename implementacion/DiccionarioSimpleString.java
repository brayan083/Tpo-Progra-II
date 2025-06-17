package implementacion;

import tdas.ConjuntoStringTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class DiccionarioSimpleString implements DiccionarioSimpleStringTDA {

	class nodo{
		String periodo;
		DiccionarioSimpleTDA precipitacionesMes;
		nodo siguiente;
	}
	
	private nodo primero;
	
	@Override
	public void inicializarDiccionario() {
	
	}

	@Override
	public void agregar(String periodo, int dia, int cantidad) {

	}

	@Override
	public void eliminar(String periodo) {

	}

	@Override
	public DiccionarioSimpleTDA recuperar(String periodo) {
		return null;

	}

	@Override
	public ConjuntoStringTDA claves() {
		return null;

	}
}
