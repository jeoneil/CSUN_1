package COMP282_Project1;

/*
Jack O'Neil
25 August, 2015
Project 1: Linked List & Circular DLL
COMP 282
*/

import java.util.Random;

public class Driver {
   static Random r = new Random();
   
   public static void main(String[] args) {
      LinkedList LL1 = new LinkedList();
      LinkedList LL2 = new LinkedList();
      LinkedList LL3 = new LinkedList();
      CircularDLL CDLL1 = new CircularDLL();
      CircularDLL CDLL2 = new CircularDLL();
      CircularDLL CDLL3 = new CircularDLL();
      LinkedList LL4 = new LinkedList();
      LinkedList LL5 = new LinkedList();
      LinkedList LL6 = new LinkedList();
      CircularDLL CDLL4 = new CircularDLL();
      CircularDLL CDLL5 = new CircularDLL();
      CircularDLL CDLL6 = new CircularDLL();
      
      System.out.println("---WITHOUT RECURSION---");
      System.out.println("Linked List - time per method (in milliseconds)");
      System.out.println("       | 1,000 items | 10,000 items | 100,000 items");
      System.out.printf("Insert | %d ms        | %d ms         | %d ms    \n", 
         timeLLInserts(1000, LL1), timeLLInserts(10000, LL2),
         timeLLInserts(100000, LL3));
      System.out.printf("Find   | %d ms      | %d ms       | %d ms    \n", 
         timeLLFinds(1000, LL1), timeLLFinds(10000, LL2), 
         timeLLFinds(100000, LL3));
      System.out.printf("Delete | %d ms       | %d ms         | %d ms    \n", 
         timeLLDeletes(1000, LL1), timeLLDeletes(10000, LL2), 
         timeLLDeletes(100000, LL3));
      System.out.println();
      System.out.println(
         "Circular Doubly Linked List - time per method (in milliseconds)");
      System.out.println("       | 1,000 items | 10,000 items | 100,000 items");
      System.out.printf("Insert | %d ms        | %d ms         | %d ms    \n", 
         timeCDLLInserts(1000, CDLL1), timeCDLLInserts(10000, CDLL2), 
         timeCDLLInserts(100000, CDLL3));
      System.out.printf("Find   | %d ms       | %d ms       | %d ms    \n", 
         timeCDLLFinds(1000, CDLL1), timeCDLLFinds(10000, CDLL2), 
         timeCDLLFinds(100000, CDLL3));
      System.out.printf("Delete | %d ms       | %d ms         | %d ms    \n", 
         timeCDLLDeletes(1000, CDLL1), timeCDLLDeletes(10000, CDLL2), 
         timeCDLLDeletes(100000, CDLL3));
      
      System.out.println("\n---WITH RECURSION---");
      System.out.println("Linked List - time per method (in milliseconds)");
      System.out.println("       | 1,000 items | 10,000 items | 100,000 items");
      System.out.printf("Insert | %d ms        | %d ms         | %d ms    \n", 
         timeLLInsertsR(1000, LL4), timeLLInsertsR(10000, LL5), 
         timeLLInsertsR(100000, LL6));
      System.out.printf("Find   | %d ms      | %d ms       | %d ms    \n", 
         timeLLFindsR(1000, LL4), timeLLFindsR(10000, LL5), 
         timeLLFindsR(100000, LL6));
      System.out.printf("Delete | %d ms       | %d ms         | %d ms    \n", 
         timeLLDeletesR(1000, LL4), timeLLDeletesR(10000, LL5), 
         timeLLDeletesR(100000, LL6));
      System.out.println();
      System.out.println("Circular Doubly Linked List - time per method (in milliseconds)");
      System.out.println("       | 1,000 items | 10,000 items | 100,000 items");
      System.out.printf("Insert | %d ms        | %d ms         | %d ms    \n", 
         timeCDLLInsertsR(1000, CDLL4), timeCDLLInsertsR(10000, CDLL5), 
         timeCDLLInsertsR(100000, CDLL6));
      System.out.printf("Find   | %d ms       | %d ms       | %d ms    \n", 
         timeCDLLFindsR(1000, CDLL4), timeCDLLFindsR(10000, CDLL5), 
         timeCDLLFindsR(100000, CDLL6));
      System.out.printf("Delete | %d ms       | %d ms         | %d ms    \n", 
         timeCDLLDeletesR(1000, CDLL4), timeCDLLDeletesR(10000, CDLL5), 
         timeCDLLDeletesR(100000, CDLL6));
   }
   
   //Linked List
   public static long timeLLInserts(int n, LinkedList LL) {
      String name = "LLThing";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
         Thing t = new Thing(r.nextInt(100), name + (i + 1));
         LL.insert(i, t);
      }
      return System.currentTimeMillis() - startTime;
   }
   public static long timeLLFinds(int n, LinkedList LL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         LL.find(i);
      return System.currentTimeMillis() - startTime;
   }
   public static long timeLLDeletes(int n, LinkedList LL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         LL.delete(i);
      return System.currentTimeMillis() - startTime;
   }
   
   //Circular doubly LL
   public static long timeCDLLInserts(int n, CircularDLL CDLL) {
      String name = "CDLLThing";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
         Thing t = new Thing(r.nextInt(100), name + (i + 1));
         CDLL.insert(i, t);
      }
      return System.currentTimeMillis() - startTime;
   }
   public static long timeCDLLFinds(int n, CircularDLL CDLL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         CDLL.find(i);
      return System.currentTimeMillis() - startTime;
   }
   public static long timeCDLLDeletes(int n, CircularDLL CDLL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         CDLL.delete(i);
      return System.currentTimeMillis() - startTime;
   }
   
   //Recursive Linked List
   public static long timeLLInsertsR(int n, LinkedList LL) {
      String name = "LLThing";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
         Thing t = new Thing(r.nextInt(100), name + (i + 1));
         LL.insertRecursive(i, t, LL.getHead());
      }
      return System.currentTimeMillis() - startTime;
   }
   public static long timeLLFindsR(int n, LinkedList LL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         LL.findRecursive(i, LL.getHead());
      return System.currentTimeMillis() - startTime;
   }
   public static long timeLLDeletesR(int n, LinkedList LL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         LL.deleteRecursive(i, LL.getHead());
      return System.currentTimeMillis() - startTime;
   }
   
   //Recursive Circular doubly LL
   public static long timeCDLLInsertsR(int n, CircularDLL CDLL) {
      String name = "LLThing";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
         Thing t = new Thing(r.nextInt(100), name + (i + 1));
         CDLL.insertRecursive(i, t, CDLL.getHead());
      }
      return System.currentTimeMillis() - startTime;
   }
   public static long timeCDLLFindsR(int n, CircularDLL CDLL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         CDLL.findRecursive(i, CDLL.getHead());
      return System.currentTimeMillis() - startTime;
   }
   public static long timeCDLLDeletesR(int n, CircularDLL CDLL) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
         CDLL.deleteResursive(i, CDLL.getHead());
      return System.currentTimeMillis() - startTime;
   }
}