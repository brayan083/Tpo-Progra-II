package algoritmos;
import implementacion.ColaString;
import tdas.ColaPrioridadTDA;

public class PruebaTPO {

	public static void main(String[] args) {
		// 1. Creamos una instancia de nuestra clase de algoritmos y la inicializamos.
		Algoritmos algoritmos = new Algoritmos();
		algoritmos.inicializar();
		System.out.println("Sistema de algoritmos inicializado.");
		
		// 2. Agregamos datos de prueba.
		System.out.println("\n----- Agregando Mediciones de Prueba -----");
		algoritmos.agregarMedicion("CampoA", 2024, 6, 10, 50);  // Junio 2024
		algoritmos.agregarMedicion("CampoA", 2024, 6, 15, 30);
		algoritmos.agregarMedicion("CampoB", 2024, 6, 10, 25);
		
		algoritmos.agregarMedicion("CampoA", 2025, 2, 5, 100); // Febrero 2025
		algoritmos.agregarMedicion("CampoC", 2025, 2, 20, 80);
		
		// Prueba de fecha inválida (29 de Feb en año no bisiesto)
		algoritmos.agregarMedicion("CampoB", 2025, 2, 29, 99);
		
		System.out.println("Datos cargados.");
		
		// 3. Ejecutamos cada uno de los algoritmos y mostramos los resultados.
		System.out.println("\n\n----- Probando Algoritmos de Consulta -----");

		// Prueba: promedioLluviaEnUnDia
		System.out.println("\n[Prueba 1: Promedio de lluvia en un día]");
		float promedio = algoritmos.promedioLluviaEnUnDia(2024, 6, 10);
		System.out.println("Promedio de lluvia para el 10/6/2024: " + promedio);
		System.out.println("    (Esperado: (50+25)/2 = 37.5)");

		// Prueba: campoMasLLuvisoHistoria
		System.out.println("\n[Prueba 2: Campo más lluvioso de la historia]");
		String campoMasLluvioso = algoritmos.campoMasLLuvisoHistoria();
		System.out.println("El campo más lluvioso es: " + campoMasLluvioso);
		System.out.println("    (Esperado: CampoA que tiene 50+30+100=180)");

		// Prueba: medicionesCampoMes
		System.out.println("\n[Prueba 3: Mediciones de un campo en un mes]");
		System.out.println("Mediciones para 'CampoA' en Junio de 2024:");
		ColaPrioridadTDA mediciones = algoritmos.medicionesCampoMes("CampoA", 2024, 6);
		// Vaciamos la cola para ver su contenido
		while(!mediciones.colaVacia()) {
			System.out.println("  - Día " + mediciones.prioridad() + ": " + mediciones.primero() + "mm");
			mediciones.desacolar();
		}
		System.out.println("    (Esperado: Día 10: 50mm, Día 15: 30mm)");

		// Prueba: mesMasLluvioso
		System.out.println("\n[Prueba 4: Mes más lluvioso de la historia]");
		System.out.println("Ranking de meses por lluvia total:");
		ColaPrioridadTDA rankingMeses = algoritmos.mesMasLluvioso();
		while(!rankingMeses.colaVacia()) {
			int mes = rankingMeses.primero();
			int totalLluvia = -rankingMeses.prioridad(); // Convertimos de nuevo a positivo
			System.out.println("  - Mes " + mes + " con un total de " + totalLluvia + "mm");
			rankingMeses.desacolar();
		}
		System.out.println("    (Esperado: Mes 2 con 180mm, luego Mes 6 con 105mm)");
		
		// Prueba: camposConLLuviaMayorPromedio
		System.out.println("\n[Prueba 5: Campos con lluvia mayor al promedio en un periodo]");
		System.out.println("Campos con lluvia > promedio en Febrero de 2025:");
		ColaString camposSobrePromedio = algoritmos.camposConLLuviaMayorPromedio(2025, 2);
		// Vaciamos la cola para ver su contenido
		while(!camposSobrePromedio.colaVacia()) {
			System.out.println("  - " + camposSobrePromedio.primero());
			camposSobrePromedio.desacolar();
		}
		System.out.println("    (Promedio en Feb 2025 es (100+80)/2=90. Esperado: CampoA con 100)");

		// 4. Probamos los algoritmos de eliminación.
		System.out.println("\n\n----- Probando Algoritmos de Eliminación -----");
		System.out.println("\nEliminando la medición del día 10/6/2024 para CampoA...");
		algoritmos.eliminarMedicion("CampoA", 2024, 6, 10, 50);
		float nuevoPromedio = algoritmos.promedioLluviaEnUnDia(2024, 6, 10);
		System.out.println("Nuevo promedio para el 10/6/2024: " + nuevoPromedio);
		System.out.println("    (Esperado: 25.0, ya que solo queda la medición de CampoB)");
		
		System.out.println("\nEliminando el 'CampoC' completo...");
		algoritmos.eliminarCampo("CampoC");
		String nuevoGanador = algoritmos.campoMasLLuvisoHistoria();
		System.out.println("Ahora el campo más lluvioso es: " + nuevoGanador);
		System.out.println("    (Esperado: CampoA, que ahora tiene 30+100=130)");
	}
}