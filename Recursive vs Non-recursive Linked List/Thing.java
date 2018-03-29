package COMP282_Project1;

public class Thing {
	   private int data; 
	   private String name; 
	   public Thing() {
	   }
	   public Thing(int d, String n) {
	      data = d;
	      name = n;
	   }
	   
	   @Override
	   public String toString(){
	      return name + ": " + data;
	   }
	   
	   public int getData() {
	      return data;
	   }
	   public String getName() {
	      return name;
	   }
	   public void setData(int d) {
	      data = d;
	   }
	   public void setName(String n) {
	      name = n;
	   }   
	}