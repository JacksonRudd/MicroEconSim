import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Market implements Incrementable, IMarketForConsumer{
	DemandCurve demandCurve;
	PriorityQueue<Firm> pQueue = new PriorityQueue<Firm>(); 
	List<Consumer> consumers;
	List<Firm> firmsList = new ArrayList<Firm>();
	List<Firm> cheapestFirms = new ArrayList<Firm>();
	
	public static Market quickCreate(){
		Consumer cust = new Consumer();
		List<Consumer> oneConsumer = new ArrayList<Consumer>();
		oneConsumer.add(cust);
		return new Market(oneConsumer);
	}
	
	public Market( List<Consumer> consumers){
		this.consumers = consumers;
		for(Consumer c : consumers){
			c.setMarket(this);
		}
		this.demandCurve = new CachedDemandCurve(consumers);
	}
	
	public void nextTimeStep() {
		for(Firm f: firmsList){
			f.nextTimeStep();
		}
	}

	public void addFirm(Firm firm) {
		pQueue.add(firm);	
		firmsList.add(firm);
	}
	
	//should be used with 
	public Integer getLowestPrice() {
		if(this.outOfStock()){
			return null;
		}
		return getCheapestFirm().getPrice();
	}
	
	private Firm getCheapestFirm() {		
		while(pQueue.peek() != null && pQueue.peek().outOfStock()){
			pQueue.poll();
		}
		return pQueue.peek();
	}

	public void sellProductToConsumer() {

		if(!this.outOfStock()){
			try {
				getCheapestFirm().sellProductToCustomer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return;
		}
	}

	public boolean outOfStock() {
		return getCheapestFirm() == null;
	}

	public void updatePriceForFirm(Firm firm) {
		pQueue.remove(firm);
		pQueue.offer(firm);
	}

	public Integer getLowestPriceOfOtherFirms(Firm firm) {
		Integer lowestPrice;
		if(this.outOfStock()){
			return null;
		}
		if(this.getCheapestFirm() == firm){
			pQueue.poll();
			if(this.outOfStock()){
				lowestPrice = null;
			}else{
				lowestPrice = this.getLowestPrice();
			}
			pQueue.offer(firm);
		}else{
			lowestPrice = this.getLowestPrice(); 
		}
		return lowestPrice;
	}

	public double numberOfOtherFirmsOfferingLowestPrice(Firm firm) {
		
		return 3;
	}

	public DemandCurve getDemandCurve() {
		return demandCurve;
	}

}
