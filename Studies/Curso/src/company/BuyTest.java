package company;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuyTest {

	@Test
	void buyInCash() {
		Buy car = new Buy(500);
		assertEquals(1, car.getNumInstallments());
		assertEquals(500, car.getTotalValue());
		assertEquals(500, car.getValueInstallments());
	
		
	}
	@Test
	void buyInInstallment() {
		Buy car = new Buy(4, 500);
		assertEquals(4, car.getNumInstallments());
		assertEquals(2000, car.getTotalValue());
		assertEquals(500, car.getValueInstallments());
	
		
	}
}
