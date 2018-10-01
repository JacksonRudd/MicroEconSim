import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Market implements Incrementable, IMarketForConsumer{
	DemandCurve demandCurve;
	List<Consumer> consumers;
	FairPriorityQueue<Firm> firmQueue = new FairPriorityQueue<Firm>();
	
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
		List<Firm> allFirms = firmQueue.getAllMembers();
		Collections.shuffle(allFirms);
		for(Firm firm : allFirms){
			firm.nextTimeStep();
		}
	}

	public void addFirm(Firm firm) {
		firmQueue.offer(firm);
		
	}
	
	//should be used with 
	public Integer getLowestPrice() {
		if(this.outOfStock()){
			return null;
		}
		return getCheapestFirm().getPrice();
	}
	
	private Firm getCheapestFirm() {		
		firmQueue.setNextRandomMember();
		return firmQueue.peek();
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
		return getCheapestFirm().outOfStock();
	}

	public void updatePriceForFirm(Firm firm) {
		firmQueue.updatePriority(firm);
	}

	public Integer getLowestPriceOfOtherFirms(Firm firm) {
		if(firmQueue.sizeOfSmallestMembers() > 1){
			return firmQueue.peek().price;
		}else{
			if(firmQueue.pQueue.isEmpty()){
				return null;
			}			
			if(firmQueue.IamInSmallestSet(firm)){
				return firmQueue.secondLargestMember().price;
			}else{
				return firmQueue.peek().price;
			}
		}
	}

	public double numberOfOtherFirmsOfferingLowestPrice(Firm firm) {
		if(firmQueue.IamInSmallestSet(firm)){
			return firmQueue.sizeOfSmallestMembers() - 1;
		}else{
			return firmQueue.sizeOfSmallestMembers();
		}
	}

	public DemandCurve getDemandCurve() {
		return demandCurve;
	}

}
