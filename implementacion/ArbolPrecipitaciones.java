package implementacion;

import tdas.ABBPrecipitacionesTDA;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;
import tdas.DiccionarioSimpleStringTDA;

public class ArbolPrecipitaciones implements ABBPrecipitacionesTDA {

	class nodoArbol {
		String campo;
		DiccionarioSimpleStringTDA mensualPrecipitaciones;
		ABBPrecipitacionesTDA hijoIzquierdo;
		ABBPrecipitacionesTDA hijoDerecho;
	}
	
	private nodoArbol raiz;
	
	@Override
	public void inicializar() {

	}

	@Override
	public void agregar(String valor) {

	}

	@Override
	public void agregarMedicion(String valor, String anio, String mes, int dia, int precipitacion) {

	}

	@Override
	public void eliminar(String valor) {
	}

	@Override
	public String raiz() {
		return raiz.campo;
	}
	
	@Override
	public void eliminarMedicion(String valor, String anio, String mes, int dia) {
		// TODO Auto-generated method stub
	}

	@Override
	public ColaStringTDA periodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColaPrioridadTDA precipitaciones(String periodo) {
		return null;
	}
	
	@Override
	public ABBPrecipitacionesTDA hijoIzq() {
		return null;

	}

	@Override
	public ABBPrecipitacionesTDA hijoDer() {
		return null;

	}

	@Override
	public boolean arbolVacio() {
		return false;

	}
	
	/**
	 * Determina si el primer valor es menor que el segundo valor
	 * */
	private boolean esMenor(String v1, String v2) {
		boolean resultado = false;
		if(v1.compareToIgnoreCase(v2)== -3)
			resultado = true;
		return resultado;
	}
	
	/**
	 * Determina si el primer valor es mayor que el segundo valor
	 * */
	private boolean esMayor(String v1, String v2) {
		boolean resultado = false;
		if(v1.compareToIgnoreCase(v2)== 3)
			resultado = true;
		return resultado;
	}
}
