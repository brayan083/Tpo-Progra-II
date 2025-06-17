package algoritmos;

import implementacion.ArbolPrecipitaciones; // 
import implementacion.ColaPrioridad;
import implementacion.ColaString; // 
import tdas.ABBPrecipitacionesTDA; // 
import tdas.ColaPrioridadTDA; // 
import tdas.ColaStringTDA;
// import tdas.ColaStringTDA;
import tdas.ConjuntoStringTDA;
import tdas.ConjuntoTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class Algoritmos {

	private String campoGanador;
	private int maxLluviaRegistrada;

	private ABBPrecipitacionesTDA arbolPrecipitaciones;

	// Método para inicializar la estructura principal.
	public void inicializar() {
		arbolPrecipitaciones = new ArbolPrecipitaciones();
		arbolPrecipitaciones.inicializar();
	}

	/**
	 * Valida si una fecha es correcta, incluyendo años bisiestos.
	 * Requerido por el enunciado del TPO.
	 */
	private boolean esFechaValida(int anio, int mes, int dia) {
		if (anio <= 0 || mes < 1 || mes > 12 || dia < 1 || dia > 31) {
			return false;
		}

		// Comprobación de días según el mes
		switch (mes) {
			case 4:
			case 6:
			case 9:
			case 11: // Meses con 30 días
				return dia <= 30;
			case 2: // Febrero
				// Comprobación de año bisiesto
				boolean esBisiesto = (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
				return esBisiesto ? dia <= 29 : dia <= 28;
			default: // Meses con 31 días (1, 3, 5, 7, 8, 10, 12)
				return true;
		}
	}

	/**
	 * Agrega una medicion a un campo determinado, en una fecha determinada
	 */
	public void agregarMedicion(String campo, int anio, int mes, int dia, int precipitacion) { //
		if (esFechaValida(anio, mes, dia)) {
			// Convertimos los enteros a String como sugiere el enunciado
			String anioStr = String.valueOf(anio);
			String mesStr = String.valueOf(mes);
			arbolPrecipitaciones.agregarMedicion(campo, anioStr, mesStr, dia, precipitacion);
		} else {
			System.out.println("Error: La fecha " + dia + "/" + mes + "/" + anio + " no es válida.");
		}
	}

	/**
	 * Elimina una medicions a un campo determinado, en una fecha determinada
	 */
	public void eliminarMedicion(String campo, int anio, int mes, int dia, int precipitacion) { //
		// La TDA para eliminar no necesita el valor de la precipitación, solo la fecha.
		String anioStr = String.valueOf(anio);
		String mesStr = String.valueOf(mes);
		arbolPrecipitaciones.eliminarMedicion(campo, anioStr, mesStr, dia);
	}

	/**
	 * Elimina un campo determinado recibido como parametro
	 */
	public void eliminarCampo(String campo) { //
		arbolPrecipitaciones.eliminar(campo);
	}

	public ColaPrioridadTDA medicionesMes(int anio, int mes) { //
		return null;
	}

	/**
	 * Devuelve una cola con prioridad con las precipitaciones de cada dia de un mes
	 * y año
	 * determinado en un campo determinado
	 */
	public ColaPrioridadTDA medicionesCampoMes(String campo, int anio, int mes) {
		// 1. Creamos la cola de prioridad que vamos a devolver.
		ColaPrioridadTDA resultado = new ColaPrioridad();
		resultado.inicializarCola();

		// 2. Usamos nuestro método de ayuda para buscar el nodo del campo.
		ArbolPrecipitaciones arbolImpl = (ArbolPrecipitaciones) this.arbolPrecipitaciones;
		ArbolPrecipitaciones.NodoArbol nodoCampo = arbolImpl.buscarNodo(campo);

		// 3. Si encontramos el campo, procedemos a buscar los datos.
		if (nodoCampo != null) {
			// Formamos la clave del periodo, ej: "20256"
			String periodo = String.valueOf(anio) + String.valueOf(mes);

			// Accedemos al diccionario de mediciones del campo.
			DiccionarioSimpleStringTDA medicionesCampo = nodoCampo.mensualPrecipitaciones;

			// Verificamos si hay mediciones para el periodo que nos interesa.
			if (medicionesCampo.claves().pertenece(periodo)) {
				// Si las hay, obtenemos el diccionario de ese mes (dias -> lluvias)
				DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodo);

				// Obtenemos el conjunto de todos los días que tienen medición en ese mes.
				ConjuntoTDA dias = medicionesMes.obtenerClaves();

				// Recorremos todos los días para agregarlos a la cola de prioridad.
				while (!dias.estaVacio()) {
					int diaActual = dias.elegir();
					int precipitacion = medicionesMes.recuperar(diaActual);

					// Agregamos a la cola: el valor es la lluvia, la prioridad es el día.
					resultado.acolarPrioridad(precipitacion, diaActual);

					dias.sacar(diaActual); // Sacamos para no repetir.
				}
			}
		}

		// 4. Devolvemos la cola. Estará llena si encontramos datos, o vacía si no.
		return resultado;
	}

	/**
	 * Devuelve el numero de mes donde mas llovio entre todos los meses de todos los
	 * años de cualquier campo
	 * El resultado es una Cola con Prioridad donde el valor es el N° de mes y la
	 * prioridad es el total de lluvia (negativo).
	 */
	public ColaPrioridadTDA mesMasLluvioso() {
		// 1. Creamos un array que funcionará como acumulador para los 12 meses.
		// Usamos tamaño 13 para poder usar los índices 1 al 12 directamente.
		int[] lluviaTotalPorMes = new int[13]; // Por defecto, se inicializa con ceros.

		// 2. Llamamos al método recursivo para que recorra el árbol y llene el array.
		if (!this.arbolPrecipitaciones.arbolVacio()) {
			acumularLluviaPorMes(this.arbolPrecipitaciones, lluviaTotalPorMes);
		}

		// 3. Ahora que el array tiene los totales, creamos la cola de prioridad.
		ColaPrioridadTDA resultado = new ColaPrioridad();
		resultado.inicializarCola();

		for (int mes = 1; mes <= 12; mes++) {
			if (lluviaTotalPorMes[mes] > 0) {
				int totalLluvia = lluviaTotalPorMes[mes];
				// Agregamos el mes a la cola. La prioridad es el total de lluvia en negativo,
				// para que los valores más altos (más lluvia) salgan primero.
				resultado.acolarPrioridad(mes, -totalLluvia);
			}
		}

		return resultado;
	}

	/**
	 * MÉTODO DE AYUDA (privado y recursivo)
	 * Recorre el árbol y va sumando las lluvias en el array acumulador.
	 */
	private void acumularLluviaPorMes(ABBPrecipitacionesTDA arbol, int[] acumulador) {
		// Caso Base: Si el árbol está vacío, no hay nada que hacer.
		if (arbol.arbolVacio()) {
			return;
		}

		// --- Paso Recursivo ---

		// 1. Procesamos el nodo actual (el campo actual).
		DiccionarioSimpleStringTDA medicionesCampo = ((ArbolPrecipitaciones) arbol)
				.buscarNodo(arbol.raiz()).mensualPrecipitaciones;
		ConjuntoStringTDA periodos = medicionesCampo.claves();

		// Recorremos todos los periodos ("AñoMes") de este campo.
		while (!periodos.estaVacio()) {
			String periodoActual = periodos.elegir();

			// ATENCIÓN: Asumimos que el año tiene 4 dígitos para poder extraer el mes.
			// Ej: de "20256", extraemos "6". De "202511", extraemos "11".
			int mes = Integer.parseInt(periodoActual.substring(4));

			// Obtenemos el diccionario con los días de lluvia de ese periodo.
			DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodoActual);
			ConjuntoTDA dias = medicionesMes.obtenerClaves();

			// Recorremos cada día y sumamos su lluvia.
			while (!dias.estaVacio()) {
				int diaActual = dias.elegir();
				int precipitacion = medicionesMes.recuperar(diaActual);

				// Sumamos la lluvia en la posición del mes correspondiente en nuestro array.
				acumulador[mes] += precipitacion;

				dias.sacar(diaActual);
			}

			periodos.sacar(periodoActual);
		}

		// 2. Continuamos el recorrido por el resto del árbol.
		acumularLluviaPorMes(arbol.hijoIzq(), acumulador);
		acumularLluviaPorMes(arbol.hijoDer(), acumulador);
	}

	/**
	 * Devuelve el promedio de precipitaciones caidas en un dia, mes y anio
	 * determinado en todos los campos
	 */
	public float promedioLluviaEnUnDia(int anio, int mes, int dia) {
		String periodo = String.valueOf(anio) + String.valueOf(mes);

		// 1. Llamamos a nuestros métodos de ayuda para recorrer el árbol y obtener los
		// totales.
		int sumaTotal = sumarLluviaEnUnDia(this.arbolPrecipitaciones, periodo, dia);
		int cantidadCampos = contarCamposConLluviaEnUnDia(this.arbolPrecipitaciones, periodo, dia);

		// 2. Calculamos el promedio, evitando la división por cero.
		if (cantidadCampos > 0) {
			return (float) sumaTotal / cantidadCampos;
		} else {
			return 0;
		}
	}

	/**
	 * MÉTODO DE AYUDA (privado y recursivo)
	 * Recorre el árbol y suma las precipitaciones de todos los campos para un
	 * periodo y día específicos.
	 */
	private int sumarLluviaEnUnDia(ABBPrecipitacionesTDA arbol, String periodo, int dia) {
		// Caso Base: Si el árbol está vacío, no hay nada que sumar.
		if (arbol.arbolVacio()) {
			return 0;
		}

		// --- Paso Recursivo ---
		// 1. Calculamos la suma de los subárboles izquierdo y derecho.
		int sumaIzquierda = sumarLluviaEnUnDia(arbol.hijoIzq(), periodo, dia);
		int sumaDerecha = sumarLluviaEnUnDia(arbol.hijoDer(), periodo, dia);

		// 2. Procesamos el nodo actual (la raíz del árbol/subárbol).
		int lluviaNodoActual = 0;
		// Buscamos el diccionario de mediciones del campo actual.
		// ArbolPrecipitaciones arbolImpl = (ArbolPrecipitaciones) arbol; // Necesitamos
		// acceso a los métodos de nuestra
		DiccionarioSimpleStringTDA medicionesCampo = ((ArbolPrecipitaciones) arbol)
				.buscarNodo(arbol.raiz()).mensualPrecipitaciones;

		// Verificamos si existe el periodo en este campo.
		if (medicionesCampo.claves().pertenece(periodo)) {
			// Si existe, obtenemos el diccionario de ese mes.
			DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodo);
			// Verificamos si existe una medición para el día que buscamos.
			if (medicionesMes.obtenerClaves().pertenece(dia)) {
				// Si existe, recuperamos el valor de la lluvia.
				lluviaNodoActual = medicionesMes.recuperar(dia);
			}
		}

		// 3. El total es la suma de la izquierda, la derecha y el nodo actual.
		return sumaIzquierda + sumaDerecha + lluviaNodoActual;
	}

	/**
	 * MÉTODO DE AYUDA (privado y recursivo)
	 * Recorre el árbol y cuenta cuántos campos tienen una medición para un periodo
	 * y día específicos.
	 */
	private int contarCamposConLluviaEnUnDia(ABBPrecipitacionesTDA arbol, String periodo, int dia) {
		// Caso Base: Si el árbol está vacío, no hay nada que contar.
		if (arbol.arbolVacio()) {
			return 0;
		}

		// --- Paso Recursivo ---
		// 1. Contamos los campos en los subárboles izquierdo y derecho.
		int conteoIzquierdo = contarCamposConLluviaEnUnDia(arbol.hijoIzq(), periodo, dia);
		int conteoDerecho = contarCamposConLluviaEnUnDia(arbol.hijoDer(), periodo, dia);

		// 2. Procesamos el nodo actual.
		int contadorNodoActual = 0;
		// Buscamos el diccionario de mediciones del campo actual.
		DiccionarioSimpleStringTDA medicionesCampo = ((ArbolPrecipitaciones) arbol)
				.buscarNodo(arbol.raiz()).mensualPrecipitaciones;

		// Verificamos si existe el periodo en este campo.
		if (medicionesCampo.claves().pertenece(periodo)) {
			// Si existe, obtenemos el diccionario de ese mes.
			DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodo);
			// Verificamos si existe una medición para el día.
			if (medicionesMes.obtenerClaves().pertenece(dia)) {
				// Si se cumplen todas las condiciones, contamos este campo.
				contadorNodoActual = 1;
			}
		}

		// 3. El total es la suma de los conteos de la izquierda, la derecha y el nodo
		// actual.
		return conteoIzquierdo + conteoDerecho + contadorNodoActual;
	}

	/**
	 * Devuelve el campo que recibio mas lluvia
	 */
	public String campoMasLLuvisoHistoria() {
		// 1. Preparamos las variables para encontrar al ganador.
		// Empezamos con el máximo en -1 para que cualquier campo con lluvia le gane.
		this.campoGanador = "Ninguno (no hay datos)";
		this.maxLluviaRegistrada = -1;

		// 2. Llamamos al método recursivo que hará el trabajo pesado.
		// Le pasamos el árbol completo para que lo recorra.
		if (!this.arbolPrecipitaciones.arbolVacio()) {
			buscarGanadorRecursivo(this.arbolPrecipitaciones);
		}

		// 3. Devolvemos el nombre del campo que quedó como ganador al final.
		return this.campoGanador;
	}

	/**
	 * MÉTODO DE AYUDA (privado y recursivo)
	 * Recorre el árbol para encontrar el campo con más lluvia.
	 */
	private void buscarGanadorRecursivo(ABBPrecipitacionesTDA arbol) {
		// Caso Base: Si el árbol está vacío, no hacemos nada.
		if (arbol.arbolVacio()) {
			return;
		}

		// --- Paso Recursivo ---

		// 1. Calculamos la lluvia total del campo en el nodo actual.
		int lluviaTotalDelCampoActual = calcularLluviaTotalDeUnCampo(arbol);

		// 2. Comparamos con el máximo que teníamos registrado.
		if (lluviaTotalDelCampoActual > this.maxLluviaRegistrada) {
			// ¡Encontramos un nuevo ganador!
			// Actualizamos el máximo de lluvia y el nombre del campo ganador.
			this.maxLluviaRegistrada = lluviaTotalDelCampoActual;
			this.campoGanador = arbol.raiz();
		}

		// 3. Continuamos la búsqueda en los hijos izquierdo y derecho.
		buscarGanadorRecursivo(arbol.hijoIzq());
		buscarGanadorRecursivo(arbol.hijoDer());
	}

	/**
	 * MÉTODO DE AYUDA (privado)
	 * Calcula la suma de TODAS las precipitaciones de un campo (un nodo del árbol).
	 */
	private int calcularLluviaTotalDeUnCampo(ABBPrecipitacionesTDA arbol) {
		int sumaTotal = 0;

		// Obtenemos el diccionario de mediciones del campo.
		// (Hacemos el casting para poder usar nuestros métodos de implementación)
		DiccionarioSimpleStringTDA medicionesCampo = ((ArbolPrecipitaciones) arbol)
				.buscarNodo(arbol.raiz()).mensualPrecipitaciones;

		// Obtenemos el conjunto de todas las claves (periodos "AñoMes") de este campo.
		ConjuntoStringTDA periodos = medicionesCampo.claves();

		// Recorremos cada periodo (cada mes de cada año)
		while (!periodos.estaVacio()) {
			String periodoActual = periodos.elegir();

			// Obtenemos el diccionario de ese periodo, que contiene los días y las lluvias.
			DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodoActual);
			ConjuntoTDA dias = medicionesMes.obtenerClaves();

			// Recorremos cada día con medición dentro de ese mes.
			while (!dias.estaVacio()) {
				int diaActual = dias.elegir();
				sumaTotal += medicionesMes.recuperar(diaActual); // Sumamos la lluvia de ese día.
				dias.sacar(diaActual); // Quitamos el día para no repetirlo.
			}

			periodos.sacar(periodoActual); // Quitamos el periodo para no repetirlo.
		}

		return sumaTotal;
	}

	/**
	 * Devuelve los campos con una cantidad de lluvia en un periodo determinado que
	 * es mayor al
	 * promedio de lluvia en un periodo determinado
	 */
	public ColaString camposConLLuviaMayorPromedio(int anio, int mes) {
		// ----- FASE 1: CALCULAR EL PROMEDIO -----

		String periodo = String.valueOf(anio) + String.valueOf(mes);

		// Usamos un array para obtener dos resultados del método de ayuda: la suma y el
		// contador.
		// posicion [0] = Suma total de lluvia en el periodo
		// posicion [1] = Cantidad de campos que tuvieron lluvia
		int[] totales = new int[2];

		// Llamamos al primer ayudante para que llene nuestro array 'totales'.
		calcularTotalesPeriodo(this.arbolPrecipitaciones, periodo, totales);

		float promedio = 0;
		if (totales[1] > 0) { // Si el contador es mayor a 0, calculamos el promedio.
			promedio = (float) totales[0] / totales[1];
		}

		// ----- FASE 2: ENCONTRAR LOS CAMPOS QUE SUPERAN EL PROMEDIO -----

		// Creamos la cola de strings que vamos a devolver.
		ColaString colaResultado = new ColaString();
		colaResultado.inicializarCola();

		// Llamamos al segundo ayudante para que compare cada campo con el promedio y
		// llene la cola.
		encontrarCamposSobrePromedio(this.arbolPrecipitaciones, periodo, promedio, colaResultado);

		return colaResultado;
	}

	/**
	 * AYUDANTE 1 (recursivo)
	 * Recorre el árbol para calcular la suma total y el conteo de campos para un
	 * periodo.
	 */
	private void calcularTotalesPeriodo(ABBPrecipitacionesTDA arbol, String periodo, int[] acumulador) {
		if (arbol.arbolVacio()) {
			return;
		}

		// Calculamos la lluvia del campo actual en el periodo dado.
		int lluviaCampo = calcularLluviaDeCampoEnPeriodo(arbol, periodo);

		if (lluviaCampo > 0) {
			acumulador[0] += lluviaCampo; // Sumamos al total general.
			acumulador[1]++; // Contamos este campo.
		}

		// Seguimos buscando en el resto del árbol.
		calcularTotalesPeriodo(arbol.hijoIzq(), periodo, acumulador);
		calcularTotalesPeriodo(arbol.hijoDer(), periodo, acumulador);
	}

	/**
	 * AYUDANTE 2 (recursivo)
	 * Recorre el árbol, y si la lluvia de un campo en un periodo supera el
	 * promedio, lo acola.
	 */
	private void encontrarCamposSobrePromedio(ABBPrecipitacionesTDA arbol, String periodo, float promedio,
			ColaStringTDA cola) {
		if (arbol.arbolVacio()) {
			return;
		}

		// Calculamos la lluvia del campo actual en el periodo (reutilizamos el método
		// de ayuda).
		int lluviaCampo = calcularLluviaDeCampoEnPeriodo(arbol, periodo);

		if (lluviaCampo > promedio) {
			cola.acolar(arbol.raiz());
		}

		// Seguimos buscando en el resto del árbol.
		encontrarCamposSobrePromedio(arbol.hijoIzq(), periodo, promedio, cola);
		encontrarCamposSobrePromedio(arbol.hijoDer(), periodo, promedio, cola);
	}

	/**
	 * AYUDANTE 3 (NO recursivo)
	 * Calcula el total de lluvia para un campo específico (el nodo raíz del árbol
	 * que recibe)
	 * en un periodo específico.
	 */
	private int calcularLluviaDeCampoEnPeriodo(ABBPrecipitacionesTDA arbol, String periodo) {
		int sumaCampo = 0;
		DiccionarioSimpleStringTDA medicionesCampo = ((ArbolPrecipitaciones) arbol)
				.buscarNodo(arbol.raiz()).mensualPrecipitaciones;

		// Verificamos que el campo tenga mediciones en ese periodo.
		if (medicionesCampo.claves().pertenece(periodo)) {
			DiccionarioSimpleTDA medicionesMes = medicionesCampo.recuperar(periodo);
			ConjuntoTDA dias = medicionesMes.obtenerClaves();

			while (!dias.estaVacio()) {
				int dia = dias.elegir();
				sumaCampo += medicionesMes.recuperar(dia);
				dias.sacar(dia);
			}
		}
		return sumaCampo;
	}
}