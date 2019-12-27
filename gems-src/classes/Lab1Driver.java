import java.util.Scanner;

public class Lab1Driver
{
   public static void main(String[] args)
   {
       int fTemp = getFarenheit();
       double cTemp = calculateCelsius();
      displayInstructions();
      getFarenheit();
      calculateCelsius();
      displayResults();
   }
   public static void displayInstructions()
   {
       System.out.println("This program will convert farenheit to celsius");
       System.out.println("after you enter a farenheit temperature");
   }
   public static int getFarenheit()
   {
       System.out.println("Please enter your temperature below");
       Scanner keyboard = new  Scanner(System.in);
       return keyboard.nextInt();
   }
   public static double calculateCelsius(int farenheit)
   {
       return (5 * (farenheit - 32) / 9.0);
   }
   public static void displayResults(int fTemp, double cTemp, long l)
   {
       System.out.println("Your farenheit temperature is " + fTemp);
       System.out.printf("Your celsius temperature is ", + cTemp);
       System.out.println("Thank you for using the program!");
       System.out.println("The rest of the world needs to conform to imperial measurments, cuz 'merica");
       return;

   }
}
