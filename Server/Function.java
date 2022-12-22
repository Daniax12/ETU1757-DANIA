package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import bd.*;

public class Function{

    // Get data from file into vector of LONG STRING
    public String getDataToVectoFromFile(File file) throws FileNotFoundException, IOException{
        
        Scanner sc = new Scanner(file);
        String getText = sc.nextLine();
        return getText;
        
    }

    // Split the long line of vector into Table
    public Table getTableOneData(String longData){
        
        String[] getSplitted = longData.split("//");
        
        String[] titlesTemp = getSplitted[1].split(";"); // Get all fields namwe of the table

        Vector titles = new Vector<>(); 
        for (int i = 0; i < titlesTemp.length; i++) {
            titles.add(titlesTemp[i]);                      // Fields of the table in vector
        }

        Vector<Vector<Object>> rowData = new Vector<>();

        for(int i = 2; i < getSplitted.length; i++){
            String[] temp = getSplitted[i].split(";");
            Vector<Object> tempVect = new Vector<>();
            for (int j = 0; j < temp.length; j++) {
                tempVect.add(temp[j]);              // Insert all data into a vector of vector<Object>
            }
            rowData.add(tempVect);
        }

        Table table = new Table();

        table.setName(getSplitted[0]);          // index[0] name of the table
        table.setTitles(titles);
        table.setAllData(rowData);

        return table;
    }

    // Deleting all non character of a string
    public String[] deleteAllNoChar(String request){
        String[] splited = request.split("[ ,=''().]");
        int index = 0;
        for (int i = 0; i < splited.length; i++) {
            if(splited[i].equals("") == false) index++;
        }

        String[] result = new String[index];
        int count = 0;
        for (int i = 0; i < splited.length; i++) {
            if(splited[i].equals("") == false) {
                result[count] = splited[i];
                count++;
            }
        }
        return result;
    }

    // Searching the index of a field in a table
    public int getIndexOfField(Table table, String field) throws Exception{

        Vector titles = table.getTitles();

        Integer index = null;
        for (int i = 0; i < table.getColumnNum(); i++) {
            if(titles.get(i).equals(field) == true) index = Integer.valueOf(i);
        }
        if(index == null){
       
            throw new Exception("Field insert invalid");
        } 
        return index;
    }

    // Searching the index (or Array) of a field who have a specified value
    public int[] getIndexOfSpecifiedValue(Table table, String field, Object obj) throws Exception{
        int index = this.getIndexOfField(table, field);

        Vector<Vector<Object>> data = table.getAllData();               // Get rowdata of the table

        int count = 0;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).get(index).equals(obj) == true) count++;     // Count the row which has have same value of object obj
        }

        int iterate = 0;
        int[] result = new int[count];
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).get(index).equals(obj) == true){
                result[iterate] = i;                                    // Get the array index of the specified row which has have the same value as object obj
                iterate++;
            }
        }
        return result;
    }

    // Inserting a whole table in file
    public void insertTableInFile(String nameUser, String nameDb, Table table) throws FileNotFoundException, IOException{
        File file = new File("DATABASE/"+ nameUser+ "/" + nameDb + "/" + table.getName() + ".txt");
        FileWriter fw = new FileWriter(file, false);

        fw.write(table.getName()+"//");                     // Inserting the name table 

        for(int i = 0; i < table.getTitles().size(); i++){
            fw.write(table.getTitles().get(i) + ";");       // Inserting the table fields
        }
        fw.write("//");

        for(int i = 0; i < table.getAllData().size(); i++){
            for(int j = 0; j < table.getAllData().get(i).size(); j++){
                fw.write(table.getAllData().get(i).get(j) + ";");       // Inserting the data in each row
            }
            fw.write("//");
        }

        fw.close();
    }

    // Searching the index of a word in a request
    public int getIndexOfTheWord(String request, String[] word) throws Exception {      // An array because maybe it is a lower/upper case
        Integer index = null;

        String[] split = this.deleteAllNoChar(request);

        for (int i = 0; i < split.length; i++) {
            if(split[i].equals(word[0]) == true || split[i].equals(word[1]) == true ) index = Integer.valueOf(i);
        }
        if(index == null){
            //System.out.println("Syntax invelid at line 1");
            throw new Exception("Syntax invalid at line 1");
        } 

        return index;
    }
    
    // Get all field declared before FROM
    public String[] allFieldRequest(String request) throws Exception{
        String[] split = this.deleteAllNoChar(request);         //Split the request
        String[] from = {"FROM", "from"};
        int indexFrom = this.getIndexOfTheWord(request, from);  // Getting the index of FROM (The fields to show)

        String[] fieldRequest = new String[indexFrom-1];
        int index = 0;
        for (int i = 1; i < indexFrom; i++) {
            fieldRequest[index] = split[i];                         // Get all fields request before FROM (Fields to show)
            index++;
        }

        return fieldRequest;
    }

    // Counting a specific 
    public int countSpecificString(String request, String[] trigg){
        String[] split = this.deleteAllNoChar(request);
        int count = 0;
        for(int j = 0; j < split.length; j++){
            for(int i = 0; i < trigg.length; i++){
                if(split[j].equals(trigg[i]) == true) count++;
            }
        }  
        return count; 
    }
    
    // Get two or more phrases from OR or AND syntax
    // SELECT * FROM TAB WHERE A = B OR C = K
    // => SELECT * FROM TAB WHERE A = B
    // => SELECT * FROM TAB WHERE C = K


    public String[] phrasesFromTrig(String request, String[] trigg, String[] gateway) throws Exception{
        int countTrig = this.countSpecificString(request, trigg);

        if(countTrig == 0) throw new Exception("Syntax invalid, counting trigger");

        String[] split = this.deleteAllNoChar(request);
        String[] result = new String[countTrig+1];
        
        int indexTrig = this.getIndexOfTheWord(request, trigg);

        String[] newSplitByTrig = request.split(" "+split[indexTrig]+" ");      // Split by the trigger

        result[0] = newSplitByTrig[0];              // Get main -- select * from --- string

        int indexGate = this.getIndexOfTheWord(request, gateway);   // Index of gateway => Where especially

        String[] splitFirst = result[0].split(" "+split[indexGate]);    // Split by where especiall

        int index = 1;
        for(int k = 1; k < newSplitByTrig.length; k++){
            result[index] = splitFirst[0] + " " + split[indexGate] + " " + newSplitByTrig[k];
            index++;
        }
        return result;
    }

    // Get two or more phrases from OR "&&" AND syntax
    // ------- SELECT * FROM TAB WHERE A = B OR C = K AND  O = M
    // => SELECT * FROM TAB WHERE A = B
    // => OR (union)
    // => SELECT * FROM TAB WHERE C = K
    // => AND (intersect)
    // => SELECT * FROM TAB WHERE O = M

    public Vector<String> phrasesFromArrayTrig(String request, String[][] trigg, String[] gateway) throws Exception{
        Vector<String> result = new Vector<>();
        Vector<String> storeTrig = new Vector<>();
        String[] split = this.deleteAllNoChar(request);
        Vector<Integer> storeIndex = new Vector<>(); 

        for(int m = 0; m < split.length; m++){
            for(int i = 0; i < trigg.length; i++){
                for(int j = 0; j < trigg[i].length; j++){
                    if(split[m].equals(trigg[i][j]) == true) {
                        storeTrig.add(split[m]);   // Adding all specific words that match with trigg in the request
                        storeIndex.add(m);          // Storing their index from split
                    }
                }
            }
        }

        int mainIndexTrig = storeIndex.get(0);        // Setting all index of trigg to same

        for (int i = 0; i < storeIndex.size(); i++) {
            split[storeIndex.get(i)] = split[mainIndexTrig];    // Changing all trigger into same values to facilitate the split process
        }

        String newReq = split[0];

        for(int i = 1; i < split.length; i++){
            newReq += " "+split[i];                     // Rebuild the entire request with a same trigger to facilitate split process
        }

        String[] newSplitByTrig = newReq.split(" "+split[mainIndexTrig]+" ");      // Split by the temporary general trigger

        result.add(newSplitByTrig[0]);              // Get main -- select * from --- string               

        int indexGate = this.getIndexOfTheWord(newReq, gateway);   // Index of gateway => Where especially

        String[] splitFirst = result.get(0).split(" "+split[indexGate]);    // Split by where especiall

        int index = 0;

        for(int k = 1; k < newSplitByTrig.length; k++){
            result.add(storeTrig.get(index));               // Adding the trigger words between specific line
            index++;
            result.add(splitFirst[0] + " " + split[indexGate] + " " + newSplitByTrig[k]);       // Getting each request line
        }

        return result;
    }


    // Get two or more TABLE with OR "&&" AND syntax
    // ------- SELECT * FROM TAB WHERE A = B OR C = K AND  O = M
    // => TABLE from SELECT * FROM TAB WHERE A = B
    // => OR (union)
    // => TABLE from SELECT * FROM TAB WHERE C = K
    // => AND (intersect)
    // => TABLE from SELECT * FROM TAB WHERE O = M
}