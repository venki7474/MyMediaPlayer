package helloworld;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chotu
 */
public class ParseSrt {
    Hashtable<String, ArrayList<String>> subHash = new Hashtable<String, ArrayList<String>>();
    public void extractSrt() {
        BufferedReader newLine = null;
            try {
                String readerLine;
                newLine = new BufferedReader(new FileReader("subtitles.srt"));
                while ((readerLine = newLine.readLine()) != null) {
    //          System.out.println(readerLine);
                    StringTokenizer st = new StringTokenizer(readerLine, "-->");
                    String timeStr = "";
                    while(st.hasMoreTokens()) {
                        String token = st.nextToken();
                        StringTokenizer st2 = new StringTokenizer(token, ",");
                        while (st2.hasMoreTokens()) {
                            String token2 = st2.nextToken();
                            timeStr = timeStr + token2;
                            break;
                        }
                        timeStr = timeStr +",";
                    }
    //              System.out.println(token.trim());
                    if (readerLine.contains("-->")){
                        readerLine = newLine.readLine();
                        ArrayList<String> subs = new ArrayList<String>();
                        while ( readerLine != null && !readerLine.contains("-->") && !readerLine.matches("[0-9]+")){
    //                  System.out.println(readerLine);
                            subs.add(readerLine);
                            readerLine = newLine.readLine();
                        }
                        subHash.put(timeStr, subs);
                    }
                }
                System.out.println(subHash.keySet());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (newLine != null)newLine.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }
     public void getStr(String time){
         String str = "";
//         System.out.println(subHash);
        Set<String> keys = subHash.keySet();
//         System.out.println(subHash.keySet());
        Iterator<String> itr = keys.iterator();
        while (itr.hasNext()) { 
           str = itr.next();
            System.out.println("keys--> :" +str);
        }
     }
            
    
}
