package COMP282_Project1;

public class CircularDLL {
	   private Thing[] array;
	   private DNode head, tail;
	   
	   public CircularDLL() {
	      head = tail = null;
	   }
	   
	   public void showAll() {
	      if (head != null) {
	         if (head == tail)
	            System.out.println(head.toString());
	         else {
	            DNode nd;
	            for (nd = head; nd.getNext() != head; nd = nd.getNext())
	               System.out.println(nd.toString());
	            System.out.println(nd.toString());   
	         }
	         System.out.println();
	      }   
	   }
	   
	   public Thing find(int i) {
	      Thing result = null;
	      if (head != null) {
	         DNode nd;
	         for (nd = head; nd.getNext() != head; nd = nd.getNext())
	            if (nd.getIndex() == i)
	               result = nd.getThing();
	         if (nd.getIndex() == i)
	            result = nd.getThing();
	      }
	      return result;
	   }
	   public Thing findRecursive(int i, DNode nd) {
	      Thing result = null;
	      if (head != null) {
	         //for (nd = head; nd.getNext() != head; nd = nd.getNext())
	         if (nd.getIndex() == i)
	            result = nd.getThing();
	         else {
	            if (nd != head)
	               result = findRecursive(i, nd.getNext());
	            else
	               if (nd.getIndex() == i)
	                  result = nd.getThing();
	         }      
	      }
	      return result;
	   }
	   
	   public void insert(int i, Thing t) {
	      DNode nd = new DNode(t, i);
	      if (head == null) {
	         head = tail = nd;
	         head.setNext(nd);
	         head.setPrev(nd);
	         tail.setNext(nd);
	         tail.setPrev(nd);
	      } else {
	         nd.setNext(head);
	         head.setPrev(nd);
	         head = nd;
	         head.setPrev(tail);
	         tail.setNext(head);
	      }
	   }
	   public boolean insertRecursive(int i, Thing t, DNode nn) {
	      DNode nd = new DNode(t, i);
	      boolean result = false;
	      if (head == null) {
	         head = tail = nd;
	         head.setNext(nd);
	         head.setPrev(nd);
	         tail.setNext(nd);
	         tail.setPrev(nd);
	         }
	      else {
	         if (nn.getIndex() < i - 1)
	            result = insertRecursive(i, t, nn.getNext());
	         nn = nn.getNext();
	         nd.setNext(head);
	         head.setPrev(nd);
	         head = nd;
	         head.setPrev(tail);
	         tail.setNext(head);
	         result = true;  
	      }   
	      return result;
	   }
	   
	   public Thing delete(int i) {
	      if (head != null) {
	         DNode nd;
	         for (nd = head; nd.getNext() != head; nd = nd.getNext())
	            if (nd.getIndex() == i) {
	               if (nd == head) {
	                  tail.setNext(head.getNext());
	                  head.getNext().setPrev(tail);
	                  head = head.getNext();
	               } else if (nd == tail) {
	                  head.setPrev(tail.getPrev());
	                  tail.getPrev().setNext(head);
	                  tail = tail.getPrev();
	               } else {
	                  nd.getPrev().setNext(nd.getNext());
	                  nd.getNext().setPrev(nd.getPrev());
	               }
	               return nd.getThing();
	            }
	         if (nd.getIndex() == i) {
	            if (nd == head) {
	               tail.setNext(head.getNext());
	               head.getNext().setPrev(tail);
	               head = head.getNext();
	            } else if (nd == tail) {
	               head.setPrev(tail.getPrev());
	               tail.getPrev().setNext(head);
	               tail = tail.getPrev();
	            } else {
	               nd.getPrev().setNext(nd.getNext());
	               nd.getNext().setPrev(nd.getPrev());
	            }
	            return nd.getThing();
	         }
	      }
	      return null;
	   }   
	   public Thing deleteResursive(int i, DNode nd) {
	      Thing result = null;
	      if (head != null) {
	         if (nd.getIndex() == i) {
	            if (nd == head) {
	               tail.setNext(head.getNext());
	               head.getNext().setPrev(tail);
	               head = head.getNext();
	            } else if (nd == tail) {
	               head.setPrev(tail.getPrev());
	               tail.getPrev().setNext(head);
	               tail = tail.getPrev();
	            } else {
	               nd.getPrev().setNext(nd.getNext());
	               nd.getNext().setPrev(nd.getPrev());
	            } 
	            return nd.getThing();
	         } else {
	            if (nd != head)
	               result = deleteResursive(i, nd.getNext());
	            else
	               if (nd.getIndex() == i) {
	                  if (nd == head) {
	                     tail.setNext(head.getNext());
	                     head.getNext().setPrev(tail);
	                     head = head.getNext();
	                  } else if (nd == tail) {
	                     head.setPrev(tail.getPrev());
	                     tail.getPrev().setNext(head);
	                     tail = tail.getPrev();
	                  } else {
	                     nd.getPrev().setNext(nd.getNext());
	                     nd.getNext().setPrev(nd.getPrev());
	                  }
	                  return nd.getThing();
	               } 
	         }          
	      }
	      return result;          
	   }
	   
	   public DNode getHead(){
	      return head;
	   }
	}