import java.util.Collection;
import java.util.Iterator;

public class CachedDemandCurve implements DemandCurve{

	public Double[] demandAtPrice;
	@Override
	public double quantityDemandedInTimeStep(int price) {
		return demandAtPrice[price];
	}
	
	public CachedDemandCurve(Collection<Consumer> consumers){
		
		demandAtPrice =  new Double[10000];
		Iterator<Consumer> consumerList;
		double totalPurchase;
		
		for(int price = 0; price< 10000; price++){
			totalPurchase = 0;
			consumerList = consumers.iterator();
			while(consumerList.hasNext()){
				totalPurchase += consumerList.next().probabilityOfPurchase(price);
			}
			demandAtPrice[price] = totalPurchase;
		}

		
	}

}
