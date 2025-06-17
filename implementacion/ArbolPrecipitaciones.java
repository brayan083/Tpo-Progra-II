package implementacion;

import tdas.ABBPrecipitacionesTDA;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class ArbolPrecipitaciones implements ABBPrecipitacionesTDA {

	public class NodoArbol {
		String campo;
		public DiccionarioSimpleStringTDA mensualPrecipitaciones;
		ABBPrecipitacionesTDA hijoIzquierdo;
		ABBPrecipitacionesTDA hijoDerecho;
	}
	
	private NodoArbol raiz;
	
	@Override
	public void inicializar() {
		raiz = null;
	}

	@Override
	public void agregar(String campo) {
		// Si el árbol está vacío, este nuevo campo será la raíz.
		if (this.arbolVacio()) {
			// 1. Creamos el nodo raíz.
			raiz = new NodoArbol();
			raiz.campo = campo;
			
			// 2. Creamos y inicializamos su diccionario de mediciones.
			raiz.mensualPrecipitaciones = new DiccionarioSimpleString();
			raiz.mensualPrecipitaciones.inicializarDiccionario();
			
			// 3. Creamos sus hijos, que serán árboles vacíos.
			raiz.hijoIzquierdo = new ArbolPrecipitaciones();
			raiz.hijoIzquierdo.inicializar();
			raiz.hijoDerecho = new ArbolPrecipitaciones();
			raiz.hijoDerecho.inicializar();
		}
		// Si el campo que queremos agregar es menor (alfabéticamente) que la raíz,
		// lo mandamos al subárbol izquierdo.
		else if (esMenor(campo, raiz.campo)) {
			raiz.hijoIzquierdo.agregar(campo);
		}
		// Si es mayor, lo mandamos al subárbol derecho.
		else if (esMayor(campo, raiz.campo)) {
			raiz.hijoDerecho.agregar(campo);
		}
		// Si es igual, no hacemos nada, porque los campos no se pueden repetir.
	}
	
	@Override
	public void eliminar(String campo) {
		if (!this.arbolVacio()) {
			// Verificamos si el nodo a eliminar es la raíz de este árbol.
			if (raiz.campo.equalsIgnoreCase(campo)) {
				// --- CASO 1: El nodo a eliminar es una hoja (no tiene hijos). ---
				// O tiene solo un hijo.
				if (raiz.hijoIzquierdo.arbolVacio()) {
					// Si no tiene hijo izquierdo, lo reemplazamos por su hijo derecho (que puede ser vacío o no).
					ArbolPrecipitaciones arbolDerecho = (ArbolPrecipitaciones) raiz.hijoDerecho;
					this.raiz = arbolDerecho.raiz;
				} else if (raiz.hijoDerecho.arbolVacio()) {
					// Si no tiene hijo derecho, lo reemplazamos por su hijo izquierdo.
					ArbolPrecipitaciones arbolIzquierdo = (ArbolPrecipitaciones) raiz.hijoIzquierdo;
					this.raiz = arbolIzquierdo.raiz;
				} else {
					// --- CASO 2: El nodo a eliminar tiene DOS hijos (el más difícil). ---
					// 1. Buscamos el mayor de los nodos del subárbol izquierdo.
					String mayorDeLosMenores = this.buscarMayorEnSubarbol(raiz.hijoIzquierdo);
					
					// 2. Reemplazamos el valor de la raíz con el que encontramos.
					raiz.campo = mayorDeLosMenores;
					
					// 3. Ahora eliminamos ese nodo que copiamos, del subárbol izquierdo.
					raiz.hijoIzquierdo.eliminar(mayorDeLosMenores);
				}
			}
			// Si el campo a eliminar es menor que la raíz, lo buscamos en el subárbol izquierdo.
			else if (esMenor(campo, raiz.campo)) {
				raiz.hijoIzquierdo.eliminar(campo);
			}
			// Si no, lo buscamos en el subárbol derecho.
			else {
				raiz.hijoDerecho.eliminar(campo);
			}
		}
	}
	
	// Esta es una función de ayuda (privada) para encontrar el campo más grande en un subárbol.
	// Se usa en el caso 2 de la eliminación.
	private String buscarMayorEnSubarbol(ABBPrecipitacionesTDA arbol) {
		if (arbol.hijoDer().arbolVacio()) {
			return arbol.raiz();
		}
		return buscarMayorEnSubarbol(arbol.hijoDer());
	}


	@Override
	public void agregarMedicion(String campo, String anio, String mes, int dia, int precipitacion) {
		// Buscamos el nodo del campo en el árbol
		NodoArbol nodoCampo = this.buscarNodo(campo);
		
		// Si el nodo no existe, primero creamos el campo
		if (nodoCampo == null) {
			this.agregar(campo);
			// Y ahora que existe, lo volvemos a buscar
			nodoCampo = this.buscarNodo(campo);
		}
		
		// Ahora que estamos seguros de que el nodo existe, agregamos la medición
		String periodo = anio + mes;
		nodoCampo.mensualPrecipitaciones.agregar(periodo, dia, precipitacion);
	}
	
	// Función de ayuda para buscar un nodo de forma iterativa (con un while)
	public NodoArbol buscarNodo(String campo) {
		ABBPrecipitacionesTDA arbolActual = this;
		while (!arbolActual.arbolVacio()) {
			if (arbolActual.raiz().equalsIgnoreCase(campo)) {
				return ((ArbolPrecipitaciones) arbolActual).raiz; // Lo encontramos
			} else if (esMenor(campo, arbolActual.raiz())) {
				arbolActual = arbolActual.hijoIzq(); // Buscamos a la izquierda
			} else {
				arbolActual = arbolActual.hijoDer(); // Buscamos a la derecha
			}
		}
		return null; // No lo encontramos
	}

	@Override
	public void eliminarMedicion(String campo, String anio, String mes, int dia) {
		// 1. Buscamos el nodo del campo.
		NodoArbol nodoCampo = this.buscarNodo(campo);
		
		// 2. Si existe...
		if (nodoCampo != null) {
			String periodo = anio + mes;
			// 3. Recuperamos su diccionario de mediciones.
			DiccionarioSimpleStringTDA mediciones = nodoCampo.mensualPrecipitaciones;
			// 4. De ese diccionario, recuperamos el diccionario del mes/año específico.
			DiccionarioSimpleTDA medicionesDelMes = mediciones.recuperar(periodo);
			
			// 5. Finalmente, eliminamos la medición de ese día.
			if (medicionesDelMes != null) {
				medicionesDelMes.eliminar(dia);
			}
		}
	}

	@Override
	public String raiz() {
		return raiz.campo;
	}

	@Override
	public ABBPrecipitacionesTDA hijoIzq() {
		return raiz.hijoIzquierdo;
	}

	@Override
	public ABBPrecipitacionesTDA hijoDer() {
		return raiz.hijoDerecho;
	}

	@Override
	public boolean arbolVacio() {
		return raiz == null;
	}
	
	private boolean esMenor(String v1, String v2) {
		return v1.compareToIgnoreCase(v2) < 0;
	}
	
	private boolean esMayor(String v1, String v2) {
		return v1.compareToIgnoreCase(v2) > 0;
	}
	
	@Override
	public ColaStringTDA periodos() { return null; }

	@Override
	public ColaPrioridadTDA precipitaciones(String periodo) { return null; }
}