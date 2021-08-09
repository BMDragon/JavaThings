//Created by Brandon Weiss on 8/27/19
import java.lang.Math;
public class Binarish {
    public static void main(String args[]){

        long max = (long) Math.pow(2,40)-1;//Generate numbers up to a(max)
       /* for(int x = 1; x <= max; x++) {
            System.out.print(counter(x) + "\n");
        }*/
       System.out.print(counter(max));
    }

    public static long counter(long amount){
        long a = amount, count = 0, power = 0;

        while(Math.pow(2,power) < a){
            power++;
        }
        for(int z = 1; z <= power; z++){
            if(a % Math.pow(2,z) > Math.pow(2,z-1)-1){
                count += (a/((int) Math.pow(2,z))*Math.pow(2,z-1)) + (a%Math.pow(2,z)-Math.pow(2,z-1));
            }
        }
        return count;
    }
}