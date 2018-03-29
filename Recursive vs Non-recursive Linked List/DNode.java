package COMP282_Project1;

public class DNode {
	   private Thing thing;
	   private DNode next, previous;
	   private int index;
	   
	   public DNode(Thing t, int i){
	      thing = t;
	      next = null;
	      previous = null;
	      index = i;
	   }
	   
	   @Override
	   public String toString(){
	      return thing.getName() + ": " + thing.getData();
	   }
	   
	   public Thing getThing(){
	      return thing;
	   }   
	   public DNode getNext() {
	      return next;
	   }
	   public DNode getPrev() {
	      return previous;
	   }
	   public int getIndex() {
	      return index;
	   }
	   public void setThing(Thing t) {
	      thing = t;
	   }
	   public void setNext(DNode nd) {
	      next = nd;
	   }
	   public void setPrev(DNode nd) {
	      previous = nd;
	   }
	   public void setIndex(int i) {
	      index = i;
	   }
	}