
public class Firm implements Incrementable, Comparable<Firm> {
	int price;
	Factory factory;
	Market market;
	int inventory;
	private int profit;
	private int breakEvenPrice;
	
	public void setPrice(int price ){
		this.price = price;
	}
	
	public void print(){
		System.out.println("inventory: " + inventory);
		System.out.println("price: " + price);
		System.out.println("profit: " + profit);
		System.out.println("rate: " + factory.rate);
		System.out.println("breakEvenPrice: " + this.breakEvenPrice);
		System.out.println();
	}
	
	public Firm(Market m) {
		this.price = Integer.MAX_VALUE;
		this.profit = 0;
		this.market = m;	
		this.factory = new Factory(this);
		this.inventory = 10000;
		calculateBreakEvenPrice();
	}


	@Override
    public int compareTo(Firm other){
		//never promote a store that is out of stock
		if(this.outOfStock() && other.outOfStock()){
			return 0;
		}
		if(this.outOfStock()){
			return 1;
		}
		if(other.outOfStock()){
			return -1;
		}
        //if this has a lower price than other, than it is a better deal
		if ( this.getPrice() < other.getPrice() ){
        	return -1;
        }else if (other.getPrice() == this.getPrice()){
        	return 0;
        }else{
        	return 1;
        }
    }
	int getPrice() {
		return price;
	}

	public void nextTimeStep(){
		factory.nextTimeStep();
		updatePrice();
	}
	//shouldn't sell them faster than we make them
	public void updatePrice(){
		double maxRevenue = 0;
		int argMax = 0;
		int priceIndex = 0;
		double demandPerYear = demandGivenOtherFirms(priceIndex) * 600;
		while(demandPerYear != 0.0){
			demandPerYear = demandGivenOtherFirms(priceIndex) * 600;
			double revenueInYear = priceIndex* Math.min(demandPerYear, (double) factory.rate.getValue());
			if(maxRevenue < revenueInYear ){
				maxRevenue = revenueInYear;
				argMax = priceIndex;
			}
			priceIndex += 1;
		}

		setPrice(Math.max(argMax, 0));	
	}

	
	public double demandGivenOtherFirms(int price) {
		DemandCurve demandCurve = market.getDemandCurve();
		if(market.outOfStock() || market.getLowestPriceOfOtherFirms(this) == null){
			return demandCurve.quantityDemandedInTimeStep(price);
		}
		if(price > market.getLowestPriceOfOtherFirms(this)){
			return 0;
		}else if(price == market.getLowestPriceOfOtherFirms(this)){
			return demandCurve.quantityDemandedInTimeStep(price) /(market.numberOfOtherFirmsOfferingLowestPrice(this) + 1);
		}
		else { // if I have the lowest price i get all the demand
			return demandCurve.quantityDemandedInTimeStep(price);
		}
	}
	
//	the cheapest we can make products for
	public void calculateBreakEvenPrice(){	
		int min = Integer.MAX_VALUE;
		for(RateOfProduction r : RateOfProduction.values()){
			if (r != RateOfProduction.ZERO && factory.costPerGoodAtRate(r) < min){
				min = factory.costPerGoodAtRate(r);
			}
		}
		breakEvenPrice = min;
	}

	public RateOfProduction getNextProductionRate(){
		//find the rate of production AND PRICE that maximizes profit
		double maxExpectedProfit = Double.MIN_VALUE; //per time step
		RateOfProduction mostProfitableRateOfProduction = RateOfProduction.ZERO;
		int bestPrice= 0;
		for(int priceIndex = 0; priceIndex < 10000; priceIndex ++){
			for(RateOfProduction rateOfProduction : RateOfProduction.values()){
				int costPerGood = factory.costPerGoodAtRate(rateOfProduction);
				double numberOfGoodsSoldPerYear = Math.min(rateOfProduction.getValue(), this.demandGivenOtherFirms(priceIndex)*600);
				double revenuePerYear = numberOfGoodsSoldPerYear * priceIndex;
				double costPerYear = costPerGood * rateOfProduction.getValue();
				double profitPerYear = revenuePerYear - costPerYear;
				if (profitPerYear > maxExpectedProfit){
					maxExpectedProfit = profitPerYear;
					mostProfitableRateOfProduction = rateOfProduction;
					bestPrice = priceIndex;
				}
			}
		}
		setPrice(bestPrice);
		return mostProfitableRateOfProduction;
	}


	public boolean outOfStock(){
		return inventory == 0;
	}

	public void sellProductToCustomer() throws Exception{
		if(outOfStock()){
			throw new Exception();
		}
		profit += price;
		inventory-=1;
		if(inventory == 0){
			market.updatePriceForFirm(this);
		}
		System.out.println(this + " sold a product");
		print();
	}

	public void buyProductFromFactory(){
		profit -= factory.currentCostOfGood();
		inventory += 1;
		market.updatePriceForFirm(this);
		System.out.println(this + "bought a product");
		print();
	}


}
