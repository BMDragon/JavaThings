// Created by Brandon Weiss on 5/13/2022
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AirportMapper {
    public static TreeMap<String, Set<String>> child = new TreeMap<>();
    public static TreeMap<String, String> parent = new TreeMap<>();
    public static TreeMap<String, String> location = new TreeMap<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("AirportChain.csv"));
        BufferedReader data = new BufferedReader(new FileReader("GlobalAirportDatabase.txt"));
        FileWriter csvWriter = new FileWriter("AirportMapping.csv", false);

        String line = "";
        while ((line=br.readLine())!=null){
            String[] port = line.split(",");
            if (port[0].equals("Code")){ continue; }
            parent.put(port[0], port[2]);
            String[] kids = port[3].substring(1, port[3].length()-1).split("; ");
            Set<String> kiddo = new TreeSet<>(Arrays.asList(kids));
            child.put(port[0], kiddo);
        }

        String line2 = "";
        while ((line2=data.readLine())!=null){
            String[] port2 = line2.split(",");
            port2[4] = port2[4].substring(1, port2[4].length()-1);
            if (!parent.keySet().contains(port2[4])) { continue; }
            String loc = "" + port2[7] + " " + port2[6];
            location.put(port2[4], loc);
        }

        TreeSet<String> remove = new TreeSet<>();
        for (String iata : parent.keySet()) {
            if (!location.keySet().contains(iata)){
                remove.add(iata);
            }
        }
        for (String air : remove) {
            parent.remove(air);
            child.remove(air);
        }
        for (String rem : child.keySet()) {
            Set<String> ugh = new TreeSet<>(child.get(rem));
            for (String kep : child.get(rem)) {
                if (remove.contains(kep)){
                    ugh.remove(kep);
                }
            }
            child.put(rem, ugh);
        }
        
        String par, label;
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (String s : parent.keySet()) {
            par = parent.get(s);
            if (par.compareTo(s)<=0 || !child.get(s).contains(par)){ continue; }
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(semiDFS(s));
            temp.addAll(semiDFS(par));
            label = "" + s + " <-> " + par;
            names.add(label);
            lists.add(temp);
        }

        csvWriter.append("WKT");
        csvWriter.append(",");
        csvWriter.append("name");
        csvWriter.append(",");
        csvWriter.append("description");
        csvWriter.append("\n");
        for (int i = 0; i < lists.size(); i++){
            csvWriter.append("\"LINESTRING (");
            ArrayList<String> ports = lists.get(i);
            csvWriter.append(location.get(ports.get(0)));
            for (int j = 1; j < ports.size(); j++){
                csvWriter.append(", ");
                csvWriter.append(location.get(ports.get(j)));
            }
            csvWriter.append(")\"");
            csvWriter.append(",");
            csvWriter.append(names.get(i));
            csvWriter.append(",");
            csvWriter.append("\n");
        }

        System.out.println("Done");
        br.close();
        data.close();
        csvWriter.flush();
        csvWriter.close();
    }

    public static ArrayList<String> semiDFS(String code){
        ArrayList<String> ret = new ArrayList<>();
        ret.add(code);
        if (child.get(code).contains("/")){ return ret; }
        for (String s : child.get(code)) {
            if (s.equals(parent.get(code))){ continue; }
            ArrayList<String> hold = semiDFS(s);
            ret.addAll(hold);
            ret.add(code);
        }
        return ret;
    }
}