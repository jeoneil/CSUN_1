package COMP182_Project5;

public class BSTNode
{
   private Beverage b;
   private BSTNode left, right;
   
   public BSTNode()
   {
   }
   
   public BSTNode(Beverage bev)
   {
      b = bev;
      left = right = null;
   }
   
   public Beverage getBeverage()
   {
      return b;
   }
   
   public BSTNode getLeft()
   {
      return left;
   }
   
   public BSTNode getRight()
   {
      return right;
   }
   
   public void setBeverage(Beverage nBev)
   {
      b = nBev;
   }
   
   public void setLeft(BSTNode l)
   {
      left = l;
   }
   
   public void setRight(BSTNode r)
   {
      right = r;
   }
}