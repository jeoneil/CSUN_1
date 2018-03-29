package COMP182_Project5;

public class Beverage
{
   private String name;
   private int rating, calories;
   
   public Beverage()
   {
   }
   
   public Beverage(String n, int r, int c)
   {
      name = n;
      rating = r;
      calories = c;
   }
   
   public String getName()
   {
      return name;
   }
   
   public int getRating()
   {
      return rating;
   }
   
   public int getCalories()
   {
      return calories;
   }
   
   public void setName(String n)
   {
      name = n;
   }
   
   public void setRating(int r)
   {
      rating = r;
   }
   
   public void setCalories(int c)
   {
      calories = c;
   }
} 