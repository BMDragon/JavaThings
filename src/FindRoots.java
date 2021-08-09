import java.util.ArrayList;

public class FindRoots {
    public static void main(String[] args){
        String f = "x^2 -69";
        char var = 'x';
        double xn = 1000;
        int numIterate = 20;
        ArrayList<String> terms = splitTerms(f);
        for(int i = 0; i < numIterate; i++){
            xn = xn - getValue(terms, var, xn)/getDerivative(terms, var, xn);
            System.out.println(xn);
        }
        System.out.println("Found Root: " + xn);
    }

    public static ArrayList<String> splitTerms(String f){
        ArrayList<String> list = new ArrayList<>();
        String temp;
        while(f.contains(" ")){
            list.add(f.substring(0,f.indexOf(" ")));
            f = f.substring(f.indexOf(" ")+1);
        }
        list.add(f);
        return list;
    }

    public static double getValue(ArrayList<String> list, char x, double val){
        double ret = 0, coefficient, exp;
        int xDex;
        for(String s: list){
            if(!s.contains(x+"")){
                ret += Double.parseDouble(s);
            }
            else if(s.contains("^")){
                xDex = s.indexOf(x);
                if(xDex == 0){ coefficient = 1; }
                else{ coefficient = Double.parseDouble(s.substring(0,xDex)); }
                exp = Double.parseDouble(s.substring(xDex+2));
                ret += coefficient * Math.pow(val, exp);
            }
            else{
                xDex = s.indexOf(x);
                if(xDex == 0){ coefficient = 1; }
                else{ coefficient = Double.parseDouble(s.substring(0,xDex)); }
                ret += coefficient*val;
            }
        }
        return ret;
    }

    public static double getDerivative(ArrayList<String> list, char x, double val){
        double ret = 0, coefficient, exp;
        int xDex;
        for(String s: list){
            if(!s.contains(x+"")){
                ret += 0;
            }
            else if(s.contains("^")){
                xDex = s.indexOf(x);
                if(xDex == 0){ coefficient = 1; }
                else{ coefficient = Double.parseDouble(s.substring(0,xDex)); }
                exp = Double.parseDouble(s.substring(xDex+2));
                ret += coefficient* exp * Math.pow(val, exp-1);
            }
            else{
                xDex = s.indexOf(x);
                if(xDex == 0){ coefficient = 1; }
                else{ coefficient = Double.parseDouble(s.substring(0,xDex)); }
                ret += coefficient;
            }
        }
        return ret;
    }
}
