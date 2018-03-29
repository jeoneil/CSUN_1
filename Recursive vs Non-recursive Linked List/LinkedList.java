package COMP282_Project1;

public class LinkedList {
	   private Thing[] array;
	   private Node head;
	   
	   public LinkedList() {
	      head = null;
	   }
	   
	   public void showAll() {
	      for (Node nd = head; nd != null; nd = nd.getNext())
	         System.out.println(nd.toString());
	      System.out.println();
	   }
	   
	   public Thing find(int i) {
	      Thing result = null;
	      for (Node nd = head; nd != null; nd = nd.getNext())
	         if (nd.getIndex() == i) 
	            result = nd.getThing();
	      return result;
	   }
	   public Thing findRecursive(int i, Node nd) {
	      Thing result = null;
	      if (nd != null) {
	         if (nd.getIndex() == i)
	            return nd.getThing();
	         else
	            result = findRecursive(i, nd.getNext());
	      }   
	      return result;
	   }
	   
	   public boolean insert(int i, Thing t) {
	      Node nd = new Node(t, i);
	      nd.setNext(head);
	      head = nd;
	      return true;
	   }
	   public boolean insertRecursive(int i, Thing t, Node nn) {
	      Node nd = new Node(t, i);
	      boolean result = false;
	      if (head == null)
	         head = nd;
	      else {
	         if (nn.getNext() != null)
	            result = insertRecursive(i, t, nn.getNext());
	         nn.setNext(nd);
	         result = true;  
	      }   
	      return result;
	   }
	   
	   public Thing delete(int i) {
	      Node previous = null; 
	      Thing result = null;
	      for (Node nd = head; nd != null; previous = nd, nd = nd.getNext())
	         if (nd.getIndex() == i) {
	            if (previous == null)
	               head = head.getNext();
	            else
	               previous.setNext(nd.getNext());
	            result = nd.getThing();
	         }
	      return result;         
	   }
	   public Thing deleteRecursive(int i, Node nd) {
	      Thing result = null;
	      if (nd != null) {
	         Node previous = nd;
	         if (nd.getIndex() == i) {
	            if (previous == null)
	               head = head.getNext();
	            else
	               previous.setNext(nd.getNext());
	            return nd.getThing();     
	         } 
	         else
	            result = deleteRecursive(i, nd.getNext());
	      }
	      return result;       
	   }
	   
	   public Node getHead(){
	      return head;
	   }
	}