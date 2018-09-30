public class Factory{
	RateOfProduction rate; 
	Firm firm;
	int time = 0;
	
	public Factory(Firm firm){
		this.firm = firm;
		rate = RateOfProduction.ONE;
	}
	
	public void nextTimeStep(){
		if(rate.getValue() == 0){
			time = time+1;
			return;
		}
		if(time % 600/rate.getValue() == 0){
			firm.buyProductFromFactory();
		}
		
		if(time == 600){
			rate = firm.getNextProductionRate();
			time = 1;
		}else{
			time = time + 1;
		} 	
	}
	
	public void setRate(RateOfProduction rate){
		this.rate = rate;
	}

	public int costPerGoodAtRate(RateOfProduction r){
		return r.getValue()*30;
	}


	public void planForNextShipment(RateOfProduction rate){
		this.rate = rate;
	}

	public int currentCostOfGood() {
		return costPerGoodAtRate(rate);
	}
}