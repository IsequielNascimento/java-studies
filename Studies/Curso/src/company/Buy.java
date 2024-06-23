package company;

public class Buy {
	
	int totalValue;
	int  numberInstallments;
	
	//In cash constructor
	public Buy(int value) {
		
		totalValue = value;
		numberInstallments = 1;
		
	
	}
	
	//Installments
	
	public Buy(int numInstallments, int valueInstallments) {
		numberInstallments = numInstallments;
		totalValue = valueInstallments * numInstallments;
		
	}
	
	public int getTotalValue() {
		return totalValue;
	}
	
	public int getNumInstallments() {
		
		return numberInstallments;
		
	}
	
	public int getValueInstallments() {
		return totalValue / numberInstallments;
	}

}
