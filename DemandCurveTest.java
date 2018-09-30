
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DemandCurveTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Consumer consumer1 = new Consumer();
		Consumer consumer2 = new Consumer();
		
		Set<Consumer> oneConsumer = new HashSet<Consumer>();
		oneConsumer.add(consumer1);
		
		Set<Consumer> twoConsumers = new HashSet<Consumer>();
		twoConsumers.add(consumer1); twoConsumers.add(consumer2);
		
		DemandCurve dcOneConsumer  = new CachedDemandCurve(oneConsumer);
		DemandCurve dcTwoConsumers = new CachedDemandCurve(twoConsumers);
		
		for(int i = 0; i< 100; i++){
			assert(2*dcOneConsumer.quantityDemandedInTimeStep(i) == dcTwoConsumers.quantityDemandedInTimeStep(i) ); 
		}
	}

}
