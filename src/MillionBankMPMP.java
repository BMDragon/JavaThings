public class MillionBankMPMP{
    private static int steps;
    public static void main(String[] args){
        int a = 0, b = 0, length = 0;
        for(int i = 0; i < 500000; i++){
            for(int j = 1; j < 500000; j++){
                if(million(i,j)){
                    if(steps > length){
                        length = steps; a = i; b = j;
                    }
                    System.out.println(i + " " + j + " " + steps);
                }
            }
        }
        System.out.println(a + " " + b + " " + length);
    }
    public static boolean million(int a, int b){
        boolean bank = false; steps = 0;
        int sum = a + b, temp;
        while(sum < 1000000){
            temp = sum;
            sum += b;
            b = temp;
            steps++;
        }
        if(sum == 1000000){ bank = true; }
        return bank;
    }
}