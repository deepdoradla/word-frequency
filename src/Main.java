import java.io.*;
import java.util.*;

/**
 * Created by deepdoradla on 07/03/2016.
 */
public class Main {

    public static void main(String[] args)
    {
        ArrayList<WordCount> wordList = new ArrayList<WordCount>();
        ArrayList wordValue = new ArrayList();

        long startTimte =  System.currentTimeMillis();
        //Read file
        FileReader reader = null;
        String filePath = "/Users/deepdoradla/Documents/Cloudwick/WordFrequency/plainText.txt/";
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

                    String[] strArr = line.split("[^a-zA-Z]");
                    int strLength = strArr.length;
                    for(int i=0; i<strLength; i++)
                    {
                        int frequencyVal = 0;
                        int newFrequencyVal = 0;

                        int val = 0;
                        int newVal = 0;
                        //Check if word already exists
                        //Boolean wordExists = checkWordExists(strArr[i], wordList);


                        for (String str : strArr) {

                            int frequency = 0;

                            for(WordCount word : wordList){
                                if(word.str.equals(str)){
                                    word.wordCount++;
                                    frequency = 1;
                                    break;
                                }

                            }

                            if(frequency == 0)
                                wordList.add(new WordCount(str, 1));

                        }//End of inner for


                    }



                }// End of while loop


                Collections.sort(wordList);



                for(int k = 0; k < 10; k++){
                    WordCount wordCount = wordList.get(k);

                    System.out.println(wordCount.str + " : " +wordCount.wordCount);

                }


                long endTime = System.currentTimeMillis();

                long finalTime = endTime - startTimte;

                System.out.print("time is" + finalTime );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Boolean checkWordExists(String string, ArrayList wordList)
    {
        for(int i=0; i<wordList.size(); i++)
        {
            if(wordList.get(i).equals(string))
                return true;
        }

        return false;
    }

    private static void writeMap(Map<String,Integer> map, String filePath)
    {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filePath,"UTF-8");
            writer.println("Word, Frequency");

            for(String string : map.keySet())
            {
                writer.printf("%s, %d\n",string, map.get(string));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null)
            {
                writer.flush();
                writer.close();
            }

        }


    }

    private static class WordCount implements Comparable<WordCount>{
        String str;
        int wordCount;
        public WordCount() {
        }

        public WordCount(String word, int count){
            this.str = word;
            this.wordCount = count;
        }
        @Override
        public int hashCode(){
            return str.hashCode();
        }

        @Override
        public boolean equals(Object obj){
            return str.equals(((WordCount)obj).str);
        }

        @Override
        public int compareTo(WordCount word){
            return word.wordCount - wordCount;
        }
    }
}
