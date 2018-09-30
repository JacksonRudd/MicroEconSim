
public class SinglePriceMarketForTesting implements IMarketForConsumer {
	int price;
	public SinglePriceMarketForTesting(int price){
		this.price = price;
	}
	
	@Override
	public void sellProductToConsumer() {
		return;
	}

	@Override
	public boolean outOfStock() {
		return false;
	}

	@Override
	public Integer getLowestPrice() {
		return price;
	}

}
