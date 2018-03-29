package COMP182_Project5;

public interface Proj5Interface 
{
   // The purpose of these 3 methods should be obvious
   public void insert(Beverage b);
   
   public Beverage delete(int key);
   
   public Beverage find(int key);
   
   // Prints out all beverages whose name contains the string str
   public void containingString(String str);
}