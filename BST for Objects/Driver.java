package COMP182_Project5;

public class Driver
{
   public static void main(String[] args)
   {
      BSTOfBeverages BST = new BSTOfBeverages();
      
      
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
      BST.insert(a);
      BST.insert(b);
      BST.insert(c);
      BST.insert(d);
      BST.insert(e);
      BST.insert(f);
      BST.insert(g);
      BST.insert(h);
      BST.insert(i);
      BST.insert(j);
      BST.insert(k);
      BST.insert(l);
      
      BST.printTree();
      
      System.out.println(BST.find(4).getName());
      
      System.out.println(BST.delete(1).getName());
      
      BST.printTree();
      
      BST.containingString("soda");
   }
}