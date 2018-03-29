package COMP182_Project4;

public interface Proj4Interface
{   
   // The purpose of these 3 methods should be obvious
   public void insert(Beverage b); 
     
   public Beverage delete(int key);
   
   public Beverage find(int key);
   
   // Sorts the vector by name   
   public void insertionSort();
   
   // Sorts the vector by rating 
   public void mergeSort();
   
   // Sorts the vector by calories  
   public void bucketSort();
   
   // Prints out all items in the vector  
   public void printVector(); 
}