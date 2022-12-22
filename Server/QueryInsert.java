package bd;

import java.io.*;
import java.util.Vector;
import connection.*;

import model.*;

public class QueryInsert extends Function{

    Skl skl;

    public QueryInsert(Skl skl){
        this.setSkl(skl);
    }

    // Search the tableName from request
    public String searchTableName(String request) throws Exception{         
        String[] trigger = {"INTO", "into"};   
        String[] split = this.deleteAllNoChar(request);
        int indexOfInto= this.getIndexOfTheWord(request, trigger);

        return split[indexOfInto+1];
    }

    // Get all data to insert into table
    public String[] dataToInsert(String request) throws Exception{  
        
        String nameTab = this.searchTableName(request);

        Table mainTable = this.getSkl().getTableFromName(nameTab);   // Get the main table
        Vector titles= mainTable.getTitles();       // Get all data of the table

        String[] splited = this.deleteAllNoChar(request);
        String[] trigger = {"VALUES", "values"};
        
        int indexValues = this.getIndexOfTheWord(request, trigger);

        String[] result = new String[splited.length - indexValues - 1];     // String data must be after "values"   
        
        if(result.length != titles.size()) throw new Exception("Data insert not match fields of the table");

        int index = 0;
        for(int i = indexValues+1; i < splited.length ; i++){
            result[index] = splited[i];                                     //Store all string after "Values"
            index++;
        }
        return result;
    }

    // Inserting allvalues to the table
    public Table insertDataToDb(String request) throws Exception, IOException, FileNotFoundException{

        Table result = new Table();

        try {
            String tableName = this.searchTableName(request);           // The name of the table Intorequest

            Table mainTable = this.getSkl().getTableFromName(tableName);      // The table concerned
            
            File file = new File("DATABASE/"+ this.getSkl().getNameUser() + "/" + this.getSkl().getNameDb()+"/"+tableName + ".txt");      // The file concerned
            Vector titles = mainTable.getTitles();                      // Get all titles to compared
            String[] data = this.dataToInsert(request);                 // The data to insert

            if(data.length != titles.size()) throw new Exception("Data insert invalid");

            FileWriter fw = new FileWriter(file, true);
            for (int i = 0; i < data.length; i++) {
                fw.write(data[i]+";");
            }
            fw.write("//");
            fw.close();
            result.setName("Loading successful");

            Vector<String> title = new Vector<>();
            title.add("Inserting data into "+tableName);             // Return always a Table that is personalized for each response

            Vector<Vector<Object>> dataResult = new Vector<>();
            Vector<Object> temp = new Vector<>();
            temp.add("Loading successful");
            dataResult.add(temp);

            result.setTitles(title);
            result.setAllData(dataResult);

        } catch (Exception e) {
            Vector<String> title = new Vector<>();
            title.add("Error unexpected");

            Vector<Vector<Object>> dataError = new Vector<>();
            Vector<Object> temp = new Vector<>();
            temp.add(e.getMessage());
            dataError.add(temp);

            result.setTitles(title);
            result.setAllData(dataError);
        } finally {
            return result;
        }

        

    }

    public Skl getSkl() {
        return skl;
    }

    public void setSkl(Skl skl) {
        this.skl = skl;
    }

}