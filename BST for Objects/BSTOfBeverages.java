package COMP182_Project5;

/*
Jack O'Neil
12 August, 2015
This program is a BST of Beveerages with basic methods (insert/find/delete.
*/

public class BSTOfBeverages implements Proj5Interface
{
   private BSTNode root;
   
   public BSTOfBeverages()
   {
      root = null;
   }
   
   public void insert(Beverage b)
   {
      BSTNode nn = new BSTNode(b), nd = root;
      boolean done = false;
      if (root == null)
         root = nn;
      else
      {
         while (!done) 
         {
            if (b.getRating() < nd.getBeverage().getRating() && nd.getLeft() == null) 
            {
               nd.setLeft(nn);
               done = true;
            }
            else if (b.getRating() < nd.getBeverage().getRating()) 
               nd = nd.getLeft(); 
            else if (nd.getRight() == null) 
            {
               nd.setRight(nn);
               done = true;
            }
            else
               nd = nd.getRight();
         }
      }
   }
   
   public Beverage find(int key)
   {
      BSTNode nd = root;
      Beverage b = null;
      boolean done = false;
      while (!done)
      {
         if (nd.getBeverage().getRating() > key)
         {
            nd = nd.getLeft();
         }
         else if (nd.getBeverage().getRating() < key)
         {
            nd = nd.getRight();
         }
         else
         {
            b = nd.getBeverage();
            done = true;
         }
      }
      return b;
   }
   
   public Beverage delete(int key)
   {
      return delete(root, key).getBeverage();
   }
   
   public BSTNode delete(BSTNode nd, int key)
   {
      if (nd != null)
      {
         if (compare(key, nd.getBeverage().getRating()) < 0)
            nd.setLeft(delete(nd.getLeft(), key));
         else if (compare(key, nd.getBeverage().getRating()) > 0)
            nd.setRight(delete(nd.getRight(), key));
         else
         {
            if (nd.getLeft() == null) 
               return nd.getRight();
            else if (nd.getRight() == null)
               return nd.getLeft();
            else
            {
               BSTNode nn = nd.getLeft();
               while (nn.getRight() != null)
               {
                  nn = nn.getRight();
               }
               nd = nn;
               nd.setLeft(delete(nd.getLeft(), nd.getBeverage().getRating()));
            }
         }   
      }
      return nd;
   }
   
   //helper method that finds difference between two node values
   public int compare(int x, int y)
   {
        return y - x;
   }
   
   public void containingString(String str)
   {
      containingString(root, str);
   }
   
   public void containingString(BSTNode nd,String str)
   {
      if (nd != null)
      {
         containingString(nd.getLeft(), str);
         if (nd.getBeverage().getName().toLowerCase().contains(str.toLowerCase()))
         {  
            System.out.print(nd.getBeverage().getName() + "   ");
            System.out.print(nd.getBeverage().getRating() + "   ");
            System.out.print(nd.getBeverage().getCalories() + "   ");
            System.out.println();
         }
         containingString(nd.getRight(), str);
      }
   }
   
   public void printTree()
   {
      printTree(root);
   }
   
   public void printTree(BSTNode nd) 
   {
      if (nd != null) 
      {
         printTree(nd.getLeft());
         printTree(nd.getRight());
         System.out.print(nd.getBeverage().getName() + "   ");
         System.out.print(nd.getBeverage().getRating() + "   ");
         System.out.print(nd.getBeverage().getCalories() + "   ");
         System.out.println();
      }
   }  
}