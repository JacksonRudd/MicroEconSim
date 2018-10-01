import java.util.ArrayList;
import java.util.List;

public class Driver {

		public static void main(String args[]){

			List<Consumer> consumers = new ArrayList<Consumer>();
			for(int i = 0; i< 10; i++ ){
				Consumer cons = new Consumer();
				consumers.add(cons);
			}
			
			Market m = new Market(consumers);

			for(int i = 0; i< 8; i++ ){
				Firm firm = new Firm(m);
				m.addFirm(firm);
			}
			while(true){
				m.nextTimeStep();
				for(Consumer c  : consumers){
					c.nextTimeStep();
				}
			}
		}
}
