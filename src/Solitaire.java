import java.util.*;
public class Solitaire {
    private static final String club = "Clubs", diamond = "Diamonds", spade = "Spades", heart = "Hearts",
            red = "Red", black = "Black";
    private static final int seed = 0;
    private static LinkedList<Card> one = new LinkedList<>(), two = new LinkedList<>(), three = new LinkedList<>(),
            four = new LinkedList<>(), five = new LinkedList<>(), six = new LinkedList<>(), seven = new LinkedList<>(),
            clubs = new LinkedList<>(), diamonds = new LinkedList<>(), spades = new LinkedList<>(),
            hearts = new LinkedList<>();
    private static ArrayList<Integer> opened = new ArrayList<>(Arrays.asList(0,2,5,9,14,20,27));
    public static void main(String[] args){
        boolean run = true; String play;
        ArrayList<Card> deck = generate();
        Scanner scnr = new Scanner(System.in);
        while(run){
            deck = shuffle(deck);
            deck = deal(deck);
            System.out.println("Play again?");
            play = scnr.nextLine();
            if(exit(play)){ run = false; }
        }
    }
    private static ArrayList<Card> deal(ArrayList<Card> deck) {
        Card temp;
        for(int i = 0; i < 28; i++) {
            temp = deck.get(i); deck.remove(0);
            if(opened.contains(i)){ temp.reveal(); temp.onTop(); }
            
        }
        return deck;
    }
    private static boolean exit(String key){
        boolean leave = false;
        if(key.equalsIgnoreCase("no") || key.equals("0")){ leave = true; }
        return leave;
    }
    public static ArrayList<Card> generate(){
        ArrayList<Card> list = new ArrayList<>();
        Card temp; String suit, color;
        for(int i = 1; i <= 13; i++){
            for(int j = 1; j <= 4; j++){
                if(j == 1){ suit = club; color = black; }
                else if(j == 2){ suit = diamond; color = red; }
                else if(j == 3){ suit = spade; color = black; }
                else{ suit = heart; color = red; }
                temp = new Card(i, suit, color);
                list.add(temp);
            }
        }
        return list;
    }
    public static ArrayList<Card> shuffle(ArrayList<Card> list){
        Random rand = new Random(seed); int num;
        ArrayList<Boolean> chosen = new ArrayList<>();
        ArrayList<Card> hold = new ArrayList<>();
        for(int i = 0; i < 52; i++){ chosen.add(false); }
        while(chosen.contains(false)){
            num = rand.nextInt(52);
            if(!chosen.get(num)){ hold.add(list.get(num)); }
        }
        return hold;
    }
    public static class Card{
        private int val;
        private String soot, col;
        private boolean top, revealed;
        public Card(int value, String suit, String color){
            val = value; soot = suit; col = color;
            top = false; revealed = false;
        }
        public int getValue(){ return val; }
        public String getColor(){ return col; }
        public String getSuit(){ return soot; }
        public boolean isTop(){ return top; }
        public boolean isRevealed(){ return revealed; }
        public void reveal(){ revealed = true; }
        public void onTop(){ top = true; }
        public void inMiddle(){ top = false; }

        @Override
        public String toString(){
            String name, s;
            if(val == 1){ name = "A"; }
            else if(val == 11){ name = "J"; }
            else if(val == 12){ name = "Q"; }
            else if(val == 13){ name = "K"; }
            else{ name = "" + val; }
            if(soot.equals(club)){ s = "C";}
            else if(soot.equals(diamond)){ s = "D"; }
            else if(soot.equals(spade)){ s = "S"; }
            else { s = "H"; }
            return name + s;
        }
    }
}