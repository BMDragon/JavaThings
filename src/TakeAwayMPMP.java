//Created by Brandon Weiss on 6/24/2020
import java.util.HashSet;
import java.util.Objects;
public class TakeAwayMPMP {
    public static void main(String[] args){
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                for(int k = 0; k < 11; k++){
                    TakeAway(i, j, k);
                }
            }
        }
        counted();
    }
    public static void TakeAway(int i, int j, int k){
        int a, b, c;
        System.out.print("" + i + " " + j + " " + k + ": ");
        HashSet<Tuple> set = new HashSet<>();
        Tuple temp = new Tuple(i, j, k);
        while(!set.contains(temp)){
            set.add(temp);
            a = Math.abs(i-j); b = Math.abs(j-k); c = Math.abs(k-i);
            temp = new Tuple(a, b, c);
            i = a; j = b; k = c;
        }
        System.out.println(temp.toString());
        count(temp);
    }
    public static int tally = 0, other = 0;
    public static void count(Tuple t){
        if((t.a == 0 && t.b == t.c)|| (t.b == 0 && t.a == t.c) || (t.c == 0 && t.a == t.b)){ tally++; }
        else{ other++; }
    }
    public static void counted(){ System.out.println("(0, n, n): " + tally + "  others: " + other); }
    public static class Tuple{
        private int a, b, c;
        public Tuple(int i, int j, int k){ a = i; b = j; c = k; }
        public int sum(){ return a+b+c; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return this.sum() == tuple.sum() &&
                    a == tuple.a && b == tuple.b;
        }
        @Override
        public int hashCode(){
            if(a <= b && b <= c){ return Objects.hash(a, b, c); }
            else if(a <= b && a <= c){ return Objects.hash(a, c, b); }
            else if(b <= a && a <= c){ return Objects.hash(b, a, c); }
            else if(b <= a && b <= c){ return Objects.hash(b, c, a); }
            else if(b <= a){ return Objects.hash(c, b, a); }
            else{ return Objects.hash(c, a, b); }
        }
        @Override
        public String toString() {
            return "Tuple{sum=" + this.sum() +
                    ", a=" + a + ", b=" + b + ", c=" + c + '}';
        }
    }
}