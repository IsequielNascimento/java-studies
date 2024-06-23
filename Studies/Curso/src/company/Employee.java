package company;

public class Employee {

	int ID;
	int age;
	String name;
	int workedDays;
	int workedHours;
	int contractHours;
	int totalExtraHours;
	
	void workedHours() {
		workedHours = workedDays * 4;
	}
	void extraHours() {
		totalExtraHours = workedHours - contractHours;
	}
	
	int getAnnualLeave(){
		return totalExtraHours;
	}
	
	void imprimir() {
		System.out.println("Total hours in employee "+ name + " database: "+getAnnualLeave() + ".");
	}
	
	
	
}
