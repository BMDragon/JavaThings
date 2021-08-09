import java.util.*;
public class CalcPi{
    public static void main(String[] args){
        long a, b, co = 0;
        Random rand = new Random();
        double top = 100000000, euler, pi;
        for(long i = 0; i < top; i++){
            a = rand.nextInt(Integer.MAX_VALUE);
            b = rand.nextInt(Integer.MAX_VALUE);
            if(gcd(a, b) == 1){ co++; }
            euler = ((double) (i + 1))/co;
            pi = Math.sqrt(6*euler);
            System.out.println(pi);
        }
    }
    public static long gcd(long a, long b) {
        if(a == 0){ return b; }
        return gcd(b % a, a);
    }
}