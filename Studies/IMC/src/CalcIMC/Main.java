package CalcIMC;

public class Main {

	public static void main(String[] args) {

		// Criando três instâncias da classe Paciente com valores diferentes

		Patient paciente1 = new Patient(40, 1.85);
		Patient paciente2 = new Patient(20, 1.95);
		Patient paciente3 = new Patient(88, 1.65);

		// Imprimindo os resultados dos métodos calcularIMC() e diagnostico()
		System.out.println("Paciente 1:");
		System.out.printf("IMC: %.2f%n", paciente1.calcularIMC());
		System.out.println("Diagnóstico: " + paciente1.diagnostico());
		System.out.println();

		System.out.println("Paciente 2:");
		System.out.printf("IMC: %.2f%n", paciente2.calcularIMC());
		System.out.println("Diagnóstico: " + paciente2.diagnostico());
		System.out.println();

		System.out.println("Paciente 3:");
		System.out.printf("IMC: %.2f%n", paciente3.calcularIMC());
		System.out.println("Diagnóstico: " + paciente3.diagnostico());
		System.out.println();
	}

}
