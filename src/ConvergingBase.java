//Created by Brandon Weiss on 8/1/2020
import java.util.*;
public class ConvergingBase {
    public static void main(String[] args){
        double target = Math.PI;
        generate(target);
    }
    private static void generate(double target){
        double[] vals = new double[31];
        Arrays.fill(vals,9); vals[0] = 1;
        double sum = 0;
        for(int i = 0; i < vals.length; i++){
            sum += vals[i]/Math.pow(target,i);
        }
        for(int j = 1; j< vals.length; j++){
            sum -= vals[j]/Math.pow(target,j);
            if(sum > target){ vals[j] = 0; }
            for(int k = 1; sum < target; k++){
                sum += 1/Math.pow(target,j);
                vals[j] = k;
            }
            System.out.println(sum);
        }
        System.out.print("" + (int)vals[0] + ".");
        for(int a = 1; a < vals.length; a++){
            System.out.print((int)vals[a]);
        }
    }
}