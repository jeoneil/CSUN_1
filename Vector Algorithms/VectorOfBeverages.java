package COMP182_Project4;

/*
Jack O'Neil
30 July, 2015
This program is a vector of Beveerages that, in addition to basic methods (insert/find/delete), can sort objects with three different sorts.
*/

public class VectorOfBeverages implements Proj4Interface
{
   private Beverage[] theBeverages;
   private int capacity, size;
   
   public VectorOfBeverages()
   {
      size = 0;
      capacity = 10;
      theBeverages = new Beverage[capacity];
   }
   
   public void insert(Beverage b)
   {
      Beverage[] tmp;
      if (size == capacity)
      {
         tmp = new Beverage[capacity * 2];
         capacity *= 2;
         for (int i = 0; i < size; i++)
         {
            tmp[i] = theBeverages[i];
         }
         theBeverages = tmp;
      }
      theBeverages[size] = b;
      size++;
   }
     
   public Beverage delete(int key)
   {
      int index = -1;
      Beverage bev = null;
      for (int i = 0; i < size; i++)
      {
         if (theBeverages[i].getRating() == key)
         {
            index = i;
            bev = theBeverages[i];
         }
      }
      if (index != -1)
      {
         for (int i = 0; i < size - 1; i++)
         {
            theBeverages[i] = theBeverages[i + 1];
            size--;
         }
      } 
      return bev;
   }
   
   public Beverage find(int key)
   {
      boolean hasData = false;
      for (int i = 0; i < size; i++)
      {
         if (theBeverages[i].getRating() == key)
         {
            return theBeverages[i];
         }
         hasData = true;
      }
      if (!hasData)
      {
         return null;
      }
      else
      {
         return null;
      }
   }  
      
   public void insertionSort()
   {
        for (int i = 1; i < size; i++) 
        {
            for (int j = i; j > 0; j--) 
            {
                if (theBeverages[j - 1].getName().compareTo(theBeverages[j].getName()) > 0) 
                {
                    Beverage k = theBeverages[j - 1];
                    theBeverages[j - 1] = theBeverages[j];
                    theBeverages[j] = k;
                }
                else 
                  break;
            }
        }
         
   }  
   
   //no arg method for merge sort   
   public void mergeSort()
   {
      mergeSort(0, size);
   }
   
   public void mergeSort(int begin, int end)
   {
        //size1 is the size of the smaller, temporary array to be merged
        int size1 = end - begin;         
        if (size1 > 1)
        {
           int mid = begin + size1 / 2; 
           //runs the same method recursively until vector is split into sizes of 1
           mergeSort(begin, mid); 
           mergeSort(mid, end); 
           Beverage[] temp = new Beverage[size1];
           int i = begin, j = mid;
           for (int k = 0; k < size1; k++) 
           {
               if (i == mid)  
                   temp[k] = theBeverages[j++];
               else if (j == end) 
                   temp[k] = theBeverages[i++];
               else if (theBeverages[j].getRating() < theBeverages[i].getRating()) 
                   temp[k] = theBeverages[j++];
               else 
                   temp[k] = theBeverages[i++];
           }    
           //changes the items in the vector into the items from the temp array, now sorted
           for (int k = 0; k < size1; k++) 
               theBeverages[begin + k] = temp[k];
         }      
   }
      
   //no arg method for bucket sort
   public void bucketSort()
   {
      bucketSort(maxValue());
   }
   
   public void bucketSort(int maxValue) 
   {
       int[] bucket = new int[maxValue + 1];
       int[] array = new int[size];
       //array stores the sorted ints, then temp stores the sorted Beverages in same order
       Beverage[] temp = new Beverage[size];
 
       for (int i = 0; i < size; i++)
           bucket[theBeverages[i].getCalories()]++;
 
       int outPos = 0;
       for (int i = 0; i < bucket.length; i++)
           for (int j = 0; j < bucket[i]; j++)
               array[outPos++] = i;
       //fills temp with sorted Beverages
       for (int i = 0; i < size; i++)
           for (int j = 0; j < size; j++)
               if (theBeverages[j].getCalories() == array[i])
               {
                  temp[i] = theBeverages[j];
                  array[i] = -1;
               }
       //copies sorted array of Beverages  from temp to the vector   
       for (int k = 0; k < size; k++) 
         theBeverages[k] = temp[k];
 
   }
   
   //helper method to find the max value in the vector
   public int maxValue() 
   {
       int maxValue = 0;
       for (int i = 0; i < size; i++)
           if (theBeverages[i].getCalories() > maxValue)
               maxValue = theBeverages[i].getCalories();
       return maxValue;
   }

   
      
   public void printVector()
   {
      if (size == 0)
         System.out.println("The vector is empty.");
      else
      {
         for (int i = 0; i < size; i++)
         {
            System.out.print(theBeverages[i].getName() + "   ");
            System.out.print(theBeverages[i].getRating() + "   ");
            System.out.print(theBeverages[i].getCalories() + "   ");
            System.out.println();
         }
         System.out.println();
      }
   }
}