import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class PriorityQueueRandomizesPoll<E extends Comparable<E>> extends AbstractQueue<E> {

	PriorityQueue<E> pQueue = new PriorityQueue<E>();
	Set<E> smallestMembers = new HashSet<E>();
	List<E> allMembers = new ArrayList<E>();
	E nextRandomMember;

	@Override
	public boolean offer(E e) {
		pQueue.offer(e);
		updateSmallestMembers();
		return true;
	}
	//if pQueue is ahead of smallest
	public void updateSmallestMembers(){
		if(pQueue.isEmpty() || smallestMembers.isEmpty()){
			return;
		}
		E elementOfSmallestMembers = smallestMembers.iterator().next(); 
		boolean pQueueContainsElementSmallerThanSmallest = pQueue.peek().compareTo(elementOfSmallestMembers) < 0;
		if(pQueueContainsElementSmallerThanSmallest){
			flushSetIntoQueue();
			smallestMembers.add(pQueue.poll());
		}
		addQueueItemsToSmallestMembers();
		setNextRandomMember();
	}
	
	private void flushSetIntoQueue(){
		for(E obj : smallestMembers)
		{
		    smallestMembers.remove(obj);
			pQueue.offer(obj);
		}
	}
	
	private void addQueueItemsToSmallestMembers(){
		E smallestElement = smallestMembers.iterator().next(); 
		while(!pQueue.isEmpty()){
			if(pQueue.peek().compareTo(smallestElement) == 0){
				smallestMembers.add(pQueue.poll());
			}else{
				return;
			}
		}
	}

	@Override
	public E poll() {
		E randomSmallestElement = peek();
		remove(nextRandomMember);
		updateSmallestMembers();
		return randomSmallestElement;
	}
	
	public void setNextRandomMember(){
		if(allMembers.isEmpty()){
			nextRandomMember = null;
			return;
		}
		int nextRandomIndex = (int) (Math.random() * smallestMembers.size());
		int i = 0;
		for(E obj : this.smallestMembers)
		{
		    if (i == nextRandomIndex)
		        nextRandomMember = obj;
		    i++;
		}

	}
	
	@Override
	public E peek() {
		return nextRandomMember;
	}

	@Override
	public Iterator<E> iterator() {
		return allMembers.iterator();
	}

	@Override
	public int size() {
		return allMembers.size();
	}
	
	public void updatePriority(E member){
		 if(smallestMembers.contains(member)){
			 smallestMembers.remove(member);
		 }else{
			 pQueue.remove(member);
		 }
		 pQueue.offer(member);
		 updateSmallestMembers();
	}
	// linear time
	public void removeElement(E member){
		pQueue.remove(member);
		allMembers.remove(member);
		smallestMembers.remove(member);
		updateSmallestMembers();
	}

}
