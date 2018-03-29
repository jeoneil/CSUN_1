package COMP282_Project1;

public class Node {
	   private Thing thing;
	   private Node next;
	   private int index;
	   
	   public Node(Thing t, int i) {
	      thing = t;
	      next = null;
	      index = i;
	   }
	   
	   @Override
	   public String toString() {
	      return thing.getName() + ": " + thing.getData();
	   }
	   
	   public Thing getThing() {
	      return thing;
	   }
	   public Node getNext() {
	      return next;
	   }
	   public int getIndex() {
	      return index;
	   }   
	   public void setThing(Thing t) {
	      thing = t;
	   }
	   public void setNext(Node nd) {
	      next = nd;
	   }
	   public void setIndex(int i) {
	      index = i;
	   }   
	}