import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Tests {
	Market m;
	@Before
	public void setUp() throws Exception {

	}
	
	
	@Test
	//the demand curve of a firm is the normal demand curve if the market is filled with empty buyers 
	public void FirmsWithoutInventoryDoNotEffectDemandCurve() {
		Market m = Market.quickCreate();	
		//low price firm without inventory does not effect demand curve
		Firm firm = new Firm(m);
		firm.price = 0;
		firm.inventory = 0;
		m.addFirm(firm);
		m.updatePriceForFirm(firm);
		
		Firm firm2 = new Firm(m);
		firm.price = 10;
		firm.inventory = 0;
		m.addFirm(firm2);
		assert(firm2.demandGivenOtherFirms(5) > 0);
		
		firm.price = 3;
		firm.inventory = 1;
		m.updatePriceForFirm(firm);
		assert(firm2.demandGivenOtherFirms(5) == 0);
	}

	@Test
	public void UpdatePriceNotEffectedByEmptyFirms() {
		Market m = Market.quickCreate();	
		//low price firm without inventory does not effect demand curve
		Firm firm = new Firm(m);
		firm.price = 0;
		firm.inventory = 0;
		m.addFirm(firm);
		m.updatePriceForFirm(firm);
		
		Firm firm2 = new Firm(m);
		firm2.price = 0;
		firm2.inventory = 15;
		m.addFirm(firm);
		firm2.updatePrice();
		//System.out.print(firm2.price);
		assert(firm2.price > 0);
	}
	
	@Test
	public void FirmsWillUndercutOtherFirms() {
		Market m = Market.quickCreate();	
		//low price firm without inventory does not effect demand curve
		Firm firm = new Firm(m);
		firm.price = 150;
		firm.inventory = 300;
		m.addFirm(firm);
		m.updatePriceForFirm(firm);
		
		Firm firm2 = new Firm(m);
		for(int i = 0; i< 200; i++){
			//System.out.println("demand given other firms at price " + i  + ": " + firm2.demandGivenOtherFirms(i));
		}

		firm2.price = 0;
		firm2.inventory = 15;
		m.addFirm(firm);
		firm2.updatePrice();
		//System.out.print(firm2.price);
		assert(firm2.price < 150);
	}
	
	@Test
	public void demandCurvePredicitons() {
		Consumer cust = new Consumer();
		cust.setMarket(new SinglePriceMarketForTesting(256));
		List<Consumer> oneConsumer = new ArrayList<Consumer>();
		oneConsumer.add(cust);
		DemandCurve demandCurve = new CachedDemandCurve(oneConsumer);
		System.out.println(demandCurve.quantityDemandedInTimeStep(256)*6000);
		int total = 0;
		for(int i = 0; i< 6000; i++){
			if(cust.chooseToBuy()){
				total = total + 1;
			}
		}
		System.out.println(total);
		
	}
}
