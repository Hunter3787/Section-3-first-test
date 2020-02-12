package CECS328Lab6;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
*  Main driver.
*  CECS 328 Lab 6
*  @author Nickolaus Marshall-Eminger
*  Date: Nov 17, 2019
*/
public class Lab6Runner {
    
    public static void main(String [] args){
        //*********[ Begin declarations, user input, and initilization. ]******
        Scanner input = new Scanner(System.in);
        //int n = 0, k = 0, mpss;
        Random random = new Random();
        
        //User input.
        System.out.print("Enter a positive Integer of 1 or greater: ");
        while(n < 1){
            n = input.nextInt();
            if(n < 1)
                System.out.println("Please enter a number greater than 1.");
            
        }
        
        //User defined array 'a' size initilization.
        int a[] = new int[n];
        
        //Randomize the array from -20 to 20 with 'n' elements.
        for(int i = 0; i < n; i++)
            a[i] = random.nextInt(41)-20;
        
        //Print array.
        System.out.println("Array Output:");
        for(int i = 0; i < a.length ; i++){
            System.out.printf("%5d",a[i]);
            
            if((i + 1) % 10 == 0)
                System.out.println();
        }
        //*******[ End user input and setup. ]*********************************
        int out = mpss(a);
        if(out != Integer.MAX_VALUE)    
            System.out.println("\nMPSS: " + out);
        else
            System.out.println("\nNo positve numbers were present.\n" +
                    "The positive sum does not exist.");
        
    }
    
    //MPSS helper.
    public static int mpss(int [] array){
        return mpss(array, 0, array.length - 1, Integer.MAX_VALUE);
    }
            
    public static int mpss(int [] array, int start, int end, int min){
        //Default max value.
        int sMin = min;
        int mid = (start + end)/2;
        int sumL[] = new int[mid+1], sumR[] = new int[end-mid];
        
        //Fill left sum array, starting with an initial value.
        //Since a subsequence can be a single element, we test to see if
        //the initial value is samller than the min.
        sumL[mid] = array[mid];
        if(sumL[mid] < sMin && sumL[mid] > 0)
                sMin = sumL[mid];
        
        //For loop to build sumL subarray.
        for(int i = mid-1 ; i >= start; i--)
            sumL[i] = array[i] + sumL[i+1];
        
        //Fill right sum array, starting with an initial value.
        //Since a subsequence can be a single element, we test to see if
        //the initial value is samller than the min.
        if(mid+1 < end)
            sumR[0] = array[mid+1];
        if(sumR[mid] < sMin && sumR[mid] > 0)
            sMin = sumR[mid];
        
        //For loop to build sumR subarray.
        for(int j = 1; j <= mid; j++)
            //Catch for in case there are only 3 elements.
            if(j + mid < end)
                sumR[j] = array[mid + j + 1] + sumR[j-1]; 
        
        quickSort(sumL);
        quickSort(sumR);
        
        //While loop to iterate through the two sub arrays.
        int i = 0, j = sumR.length-1;
        while(i < sumL.length && j >= 0 ){
            int sum = sumL[i] + sumR[j];
            if( sum <= 0)
                i++;
            else if(sum < sMin && sum > 0){
                sMin = sum;
                j--;
            }
            else
                j--;     
        }
  
        int sMinL = sMin;
        int sMinR = sMin;
        //As long as there is more than one element, continue to use recursion
        //This prevents overflows.
        if(start != end){
             sMinL = mpss(array, 0, mid, sMinL);
             sMinR = mpss(array, mid + 1, end, sMinR);
        }
        
        if(sMinL + sMinR < sMin && sMinL + sMinR > 0)
            sMin = sMinL + sMinR;
        if(sMinL < sMin && sMinL > 0)
            sMin = sMinL;
        if(sMinR < sMin && sMinR > 0)
            sMin = sMinR;

        return sMin; 
    }
    
    //Helper quicksort for ease.
    public static void quickSort(int [] array){
        quickSort(array, 0, array.length - 1);
    }
    
    //Quicksort Method.
    public static void quickSort(int [] array, int low, int high){
        if(low >= high) 
            return;
        int pivot = partition(array, low, high);
        quickSort(array, low, pivot-1);
        quickSort(array,pivot+1, high);
    }
    
    private static int partition(int array[], int low, int high){
        int mid = (high + low)/2, pivot, lowIndex, highIndex;
        
        if(array[mid] < array[low])
            swap(array, mid, low);
        if(array[high] < array[low])
            swap(array, high, low);
        if(array[high] < array[mid])
            swap(array, high, mid);
        
        pivot = array[mid];
        swap(array, mid, high - 1);
        
        for(lowIndex = low, highIndex = high - 1; ;){
            while(lowIndex < highIndex && array[++lowIndex] < pivot)
                ;
            while(highIndex > lowIndex && array[--highIndex] > pivot )
                ;
            if(lowIndex >= highIndex)
                break;
            swap(array, lowIndex, highIndex);
        }//End for
        
        if((high - low) > 3)
            swap(array, lowIndex, high - 1);
        
        return lowIndex;
    }
    
    public static void swap(int array[], int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
