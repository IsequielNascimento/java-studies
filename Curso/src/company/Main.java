package company;

public class Main {

	public static void main(String[] args) {

		Employee Confia = new Employee();
		Confia.ID = 1;
		Confia.name = "Teste";
		Confia.contractHours = 88;
		Confia.workedDays = 25;
	
		Confia.workedHours();
		Confia.extraHours();
		Confia.imprimir();
	}

}
