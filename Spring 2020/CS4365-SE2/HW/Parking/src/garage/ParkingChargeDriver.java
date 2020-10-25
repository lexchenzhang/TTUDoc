/* 
 *  Author ......: Greg, Beam, KI7MT, <ki7mt@yahoo.com>
 *  Copyright ...: Copyright (C) 2017 GPLv3
 *  Level .......: Intermediate
 *  Target ......: Java Console Application
 *  Description..: Driver for Parking Garage
 *  
 */
package garage;

import java.util.Scanner;

public class ParkingChargeDriver
{

	public static double d_charges;
	public static double d_run_total;
	
	public static void main(String[] args)
	{
		// Initialize the Scanner
		Scanner input = new Scanner(System.in);

		// Initialize loop control and counter variables.
		double hours = 0.0;
		// Set the input objects to 0 initially.
		ParkingCharge costs = new ParkingCharge(0.0);

		// Start while loop for customer hour entry.
		while (hours != -1)
		{
            System.out.printf("Enter Hours (-1 to quit)..: ");
            hours = input.nextDouble();

            // Only allow entry if hours are greater than zero
			if(hours > 0)
			{
                new ParkingChargeDriver().calculate(hours);
            }
            System.out.print("\n");
		} // END - Customer Hours Entry.

		// Close the input scanner ( prevent resource leaks )
		input.close();

        // END - Customer Hour Entry.
        System.out.printf("Total Daily Reciepts = $%.2f%n", costs.getRunTotal());
	
	} // END - Main Method.
	
	public void calculate(double hours)
	{
		if(hours > 0)
		{
			ParkingCharge costs = new ParkingCharge(0.0);
			double charges = ParkingCharge.calculateCharges(hours);
			costs.setRunTotal(charges);
			System.out.printf("Customer charge is........: " + "$%.2f ", charges);
			System.out.print("\n");
			System.out.printf("Running Daily Total.......: " + "$%.2f%n", costs.getRunTotal());

			d_charges = charges;
			d_run_total = costs.getRunTotal();
		}
	}
	
} /* END - ParkingChangeDriver class */