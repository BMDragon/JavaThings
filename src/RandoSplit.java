public class RandoSplit{
    public static void main(String[] args){
        int donation, count = 0, temp, hold;
        for(int i = 1000; i < 16600; i++){
            donation = 50000 - (3*i);
            hold = donation * (int)Math.pow(10, 5) + i;
            temp = sum(hold);
            if(temp == 45){ count++; System.out.println(i + "" + confirm(hold));}
        }
        System.out.println(count);
    }
    public static int sum(int m){
        int n, sum = 0;
        while(m > 0){
            n = m % 10;
            sum = sum + n;
            m = m / 10;
        }
        return sum;
    }
    public static boolean confirm(int k){
        String str = "" + k;
        int index = 0, i = 1; boolean has = false;
        while(index >= 0){
            index = str.indexOf(i);
            i++;
        }
        if(i > 8){ has = true;}
        return has;
    }
}