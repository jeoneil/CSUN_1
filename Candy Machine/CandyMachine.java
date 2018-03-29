package COMP110_Project3;

/*
*Name: Jack O'Neil
*Date Created: 12 February, 2015
*Project Info: Project2 - "Candy Machine"
*Description: Console program in which user may interact with virtual candy machine. 
*/

/*
*Name: Jack O'Neil
*Date Created: 12 February, 2015
*Project Info: Project2 - "Candy Machine"
*Description: Console program in which user may interact with virtual candy machine. 
*/

import java.util.Scanner;

public class CandyMachine
{
   static int credit = 0;
   static int nickel = 0, dime = 0, quarter = 0;
   static int selection = 1;
   
   public static void main(String[] args)
   {
      CandyMachine machine = new CandyMachine();
      
      int snickers = 5, hersheys = 5, kitkat = 5, reeses = 5; 
      int snickerValue = 100, kitkatValue = 75, hersheyValue = 125, reesesValue = 60; 
      Scanner input = new Scanner(System.in);
      
      boolean exit = false;
      
      while (!exit)
      {
         //System.out.printf("Nickels: %d\nDimes: %d\nQuarters: %d\n", nickel, dime, quarter);
         System.out.printf("Menu\n\nCredit: $%.2f\n\n0. Insert Money\n\n1. Purchase Snickers  - $1.00\n2. Purchase Hershey’s - $1.25\n3. Purchase Kit-Kat   - $0.75\n4. Purchase Reese’s   - $0.60\n\n5. Change Return\n", (double)credit / 100);
         
         selection = input.nextInt();
         
         switch (selection)
         {
            case 0: insertMoney();
                    break;
            case 1: if (snickers > 0)
                    {
                       if (credit >= snickerValue)
                       { 
                          vend(snickerValue);
                          snickers--;
                          System.out.printf("The candy machine dispenses a Snickers bar.\n\n");
                       }
                       else
                       {
                          System.out.println("\nInsufficient funds\n\n");
                       }
                    }
                    else
                    {
                       vend("Sold out.\n\n");
                    }
                    break;
            case 2: if (hersheys > 0)
                    {
                       if (credit >= hersheyValue)
                       { 
                          vend(hersheyValue);
                          hersheys--;
                          System.out.printf("The candy machine dispenses a Hershey's bar.\n\n");
                       }
                       else
                       {
                          System.out.println("\nInsufficient funds\n\n");
                       }
                    }
                    else
                    {
                       vend("Sold out.\n\n");
                    }
                    break;
            case 3: if (kitkat > 0)
                    {
                       if (credit >= kitkatValue)
                       { 
                          vend(kitkatValue);
                          kitkat--;
                          System.out.printf("The candy machine dispenses a Kit-Kat.\n\n");
                       }
                       else
                       {
                          System.out.println("\nInsufficient funds\n\n");
                       }
                    }
                    else
                    {
                       vend("Sold out.\n\n");
                    }
                    break;
            case 4: if (reeses > 0)
                    {
                       if (credit >= reesesValue)
                       { 
                          vend(reesesValue);
                          reeses--;
                          System.out.printf("The candy machine dispenses a Reese's bar.\n\n");
                       }
                       else
                       {
                          System.out.println("\nInsufficient funds\n");
                       }
                    }
                    else
                    {
                       vend("Sold out.\n\n");
                    }
                    break;                
            case 5: System.out.println(machine.changeReturn());
                    break;        
            default: System.out.println("ERROR: Out of range");
                     break;
         }
      }
   }
   
   public static void insertMoney()
   {
      Scanner input = new Scanner(System.in);
      boolean exitInsert = false;
      int selectCoin = 0, dollar = 0;
      
      while (!exitInsert)
      {
         System.out.printf("Insert Money\n\nCredit: $%.2f\n\n1. Insert nickel(s)\n2. Insert dime(s)\n3. Insert quarter(s)\n4. Insert dollar(s)\n\n5. Back\n", (double)credit / 100);
         
         selectCoin = input.nextInt();
         
         switch (selectCoin)
         {
            case 1: System.out.println("How many nickels?");
                    nickel += input.nextInt();
                    break;
            case 2: System.out.println("How many dimes?");
                    dime += input.nextInt();
                    break;
            case 3: System.out.println("How many quarters?");
                    quarter += input.nextInt();
                    break;
            case 4: System.out.println("How many dollars?");
                    dollar += input.nextInt();
                    break;
            case 5: exitInsert = true;
                    break;        
            default: System.out.println("Error: Out of range");
                     break;
         }
         credit = (nickel * 5) + (dime * 10) + (quarter * 25) + (dollar * 100);
      }
   }
   
   public static void vend(int itemValue)
   {
      credit -= itemValue;
   }
   
   public static void vend(String message)
   {
      System.out.println(message);
   }
   
   public String changeReturn()
   {
      String message = "";
      
      int initialCredit = credit;
      int nickelReturn = 0, dimeReturn = 0, quarterReturn = 0;
      boolean hasChange = true;
      
      while (credit > 0 && hasChange)
      {
         if (credit - 25 >= 0 && quarter > 0)
         {
            quarter--;
            credit -= 25;
            quarterReturn++;
            //System.out.println(quarter);
         }
         else if (credit - 10 >= 0 && dime > 0)
         {
            dime--;
            credit -= 10;
            dimeReturn++;
            //System.out.println(dime);
         }
         else if (credit - 5 >= 0 && nickel > 0)
         {
            nickel--;
            credit -= 5;
            nickelReturn++;
            //System.out.println(nickel);
         }
         else
         {
            hasChange = false;
         }
      }
      
      nickel = 0;
      dime = 0;
      quarter = 0;
      
      System.out.printf("Amount owed to you: $%.2f\nCoins returned to you:\nNickels: %d\nDimes: %d\nQuarters: %d\n", ((double)initialCredit / 100), nickelReturn, dimeReturn, quarterReturn);

      if (!hasChange)
      {
         credit = 0;
         message = "Not enough coins in machine; not all of your change was returned.\n";
      }
      return message;
   }

	public static void checkCredit(int item, int itemValue)
	{
	   String itemString = "";
	   switch (selection)
	   {
	      case 1:
	      case 2:
	      case 3:
	      case 4:
	   }
	   
	   if (item > 0)
	   {
	        if (credit >= itemValue)
	        { 
	         vend(itemValue);
	         item--;
	         System.out.printf("The candy machine dispenses a Kit-Kat.\n\n");
	      }
	      else
	      {
	         System.out.println("\nInsufficient funds\n\n");
	      }
	   }
	   else
	   {
	      vend("Sold out.\n\n");
	   }
	}
}