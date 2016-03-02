package helloworld;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                System.out.println(subHash);
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
    public String getStr(String time) {
        String str = "", mainStr = "";
        try {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        
//        System.out.println(time);
        StringTokenizer st = new StringTokenizer(time,"/");
        String timeStr= "";
        while(st.hasMoreTokens()){
            timeStr += st.nextToken();
            break;
        }
        System.out.println("--->"+timeStr);
        java.util.Date date_timeCrnt =(java.util.Date)format.parse(timeStr);

        java.sql.Time timeCrnt = new java.sql.Time(date_timeCrnt.getTime());
        System.out.println("its time parse"+timeCrnt);
        Set<String> keys = subHash.keySet();
        Iterator<String> itr = keys.iterator();
        
        
        while (itr.hasNext()) { 
           str = itr.next();
//            System.out.println("before parse"+str);
            java.sql.Time time1 = null;
            java.sql.Time time2 = null;
            StringTokenizer st1 = new StringTokenizer(str,", ");
            while(st1.hasMoreTokens()){
               String timeStr1 = st1.nextToken();
//                System.out.println(timeStr1);
               java.util.Date date_time1 =(java.util.Date)format.parse(timeStr1);
               time1 = new java.sql.Time(date_time1.getTime());
               
               String timeStr2 = st1.nextToken();
//               System.out.println(timeStr2);
               java.util.Date date_time2 =(java.util.Date)format.parse(timeStr2);
               time2 = new java.sql.Time(date_time2.getTime());
//                       System.out.println(t);
           }
//            System.out.println(time1+ "---"+time2);
               if (time1.compareTo(timeCrnt) <= 0 && timeCrnt.compareTo(time2) < 0) {
//                   System.out.println(time1+ "---"+time2);
                   mainStr += subHash.get(str);
                   break;
               }
           
        }
         System.out.println(mainStr);
        
        } catch(Exception e) {
            
        }
        return mainStr;
     }
            
    
}
