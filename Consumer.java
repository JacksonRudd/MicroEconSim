
public class Consumer implements Incrementable{
	IMarketForConsumer market;
	public Consumer(){
	}
	
	public void setMarket(IMarketForConsumer m){
		this.market = m;
	}
	
	@Override
	public void nextTimeStep() {
		if(this.chooseToBuy()){
			market.sellProductToConsumer();
		}
		
	}
	public boolean chooseToBuy(){
		double probOfPurchase;
		if( market.outOfStock()){
			probOfPurchase = 0;
		}else{
			 probOfPurchase = probabilityOfPurchase(market.getLowestPrice());
		}
		return Math.random() <= probOfPurchase;
	}
	
	double probabilityOfPurchase(int price){
		return Math.max( (double) (300.0 - price)/300, 0)/15.0;
	}
	

}
