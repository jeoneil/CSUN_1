package COMP182_Project4;

public class Driver
{
   public static void main(String[] args)
   {
      VectorOfBeverages vector = new VectorOfBeverages();
      
      Beverage a = new Beverage("Soda1", 8, 26);
      Beverage b = new Beverage("soda2", 5, 83);
      Beverage c = new Beverage("afraid", 3, 52);
      Beverage d = new Beverage("Soda4", 6, 11);
      Beverage e = new Beverage("sad", 10, 60);
      Beverage f = new Beverage("Soda6", 2, 72);
      Beverage g = new Beverage("robert", 4, 11);
      Beverage h = new Beverage("happy", 3, 25);
      Beverage i = new Beverage("Soda9", 7, 95);
      Beverage j = new Beverage("derpy", 9, 23);
      Beverage k = new Beverage("harold", 1, 12);
      Beverage l = new Beverage("ronald", 2, 65);
      vector.insert(a);
      vector.insert(b);
      vector.insert(c);
      vector.insert(d);
      vector.insert(e);
      vector.insert(f);
      vector.insert(g);
      vector.insert(h);
      vector.insert(i);
      vector.insert(j);
      vector.insert(k);
      vector.insert(l);
      
      vector.printVector();
      
      //vector.insertionSort();
      vector.mergeSort();
      //vector.bucketSort();
      
      vector.printVector();
   }
}