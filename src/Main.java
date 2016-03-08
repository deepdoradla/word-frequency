import java.io.*;
import java.util.*;

/**
 * Created by deepdoradla on 07/03/2016.
 */
public class Main {

    public static void main(String[] args)
    {
        //Read file
        FileReader reader = null;
        String filePath = "/Users/deepdoradla/Documents/Cloudwick/SearchText/plainText.txt/";
        String wordFreqFilePath = "/Users/deepdoradla/Documents/Cloudwick/WordFrequency/wordFrequency.csv";
        String wordTopTenFilePath = "/Users/deepdoradla/Documents/Cloudwick/WordFrequency/top10.csv";
        String line;
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        try {
            reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            try {
                while((line = bufferedReader.readLine()) != null)
                {

                    String[] strArr = line.split(" ");
                    int strLength = strArr.length;
                    for(int i=0; i<strLength; i++)
                    {
                        int frequencyVal = 0;
                        int newFrequencyVal = 0;

                        if(hashMap.containsKey(strArr[i]))
                        {
                            frequencyVal = (int) hashMap.get(strArr[i]);
                            newFrequencyVal = frequencyVal+1;
                            hashMap.put(strArr[i], newFrequencyVal);
                        }else
                            hashMap.put(strArr[i],1);
                    }
                }

                //This prints all the words and their frequency numbers. All the words are unique except for case sensitivity
                //System.out.println(hashMap);

                Map<String, Integer> treeMap = new TreeMap<String, Integer>(hashMap);
                writeMap(hashMap, wordFreqFilePath);

                //Now sort the map by value and get top 10 results
                ArrayList<Map.Entry<String, Integer>> sortedList = sortByValye(hashMap);

                Map.Entry<String, Integer> entry;
                HashMap<String, Integer> sortedMap = new HashMap<String, Integer>();
                for (int i = 0; i < Math.min(10,sortedList.size()); i++) {
                    entry = sortedList.get(i);
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
                writeMap(sortedMap, wordTopTenFilePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static <K, V extends Comparable<? super V>> ArrayList<Map.Entry<K, V>> sortByValye(Map<K, V> map) {
        ArrayList<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        return sortedEntries;
    }



    private static void writeMap(Map<String,Integer> map, String filePath)
    {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filePath,"UTF-8");
            writer.println("Word, Frequency");

            for(String string : map.keySet())
            {
                writer.printf("%s, %d\n",string, map.get(string), ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }


    }
}
