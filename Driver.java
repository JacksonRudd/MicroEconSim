import java.util.ArrayList;
import java.util.List;

public class Driver {

		public static void main(String args[]){

			Consumer cust = new Consumer();
			List<Consumer> oneConsumer = new ArrayList<Consumer>();
			oneConsumer.add(cust);
			
			Market m = new Market(oneConsumer);

			for(int i = 0; i< 3; i++ ){
				Firm firm = new Firm(m);
				m.addFirm(firm);

			}
			
			
			
			while(true){
				m.nextTimeStep();
				cust.nextTimeStep();
			}
		}
}
