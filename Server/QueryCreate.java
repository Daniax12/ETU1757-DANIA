package bd;

import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import connection.Skl;
import model.*;

public class QueryCreate extends Function{ 
    
    private Skl skl;

    public QueryCreate(Skl skl){
        this.setSkl(skl);
    }

    public QueryCreate(){}

    // Search the tableName from request
    public String searchTableName(String request) throws Exception{         
        String[] trigger = {"TABLE", "table"};                              // Knowing where Table is put, and getting the value after this word   
        String[] split = this.deleteAllNoChar(request);
        int indexOfTable = this.getIndexOfTheWord(request, trigger);

        return split[indexOfTable+1];
    }

    // Creating database
    public Table createDatabase(String request) throws Exception{
        Table result = new Table();
        try {
            result.setName("Database created");
            String[] trigger = {"DATABASE", "database"};                              // Knowing where DABATBASE is put, and getting the value after this word   
            String[] split = this.deleteAllNoChar(request);
            int indexOfDb = this.getIndexOfTheWord(request, trigger);

            String newDb = split[indexOfDb+1];     // Get the new database name
            File folder = new File("DATABASE/"+ this.getSkl().getNameUser() + "/" +newDb);
            if(folder.exists() == false){       // If database not exists
                folder.mkdir();

                Vector<String> title = new Vector<>();
                title.add("Creating database");             // Return always a Table that is personalized for each response
    
                Vector<Vector<Object>> dataResult = new Vector<>();
                Vector<Object> temp = new Vector<>();
                temp.add("Database "+ newDb + " created");
                dataResult.add(temp);
    
                result.setTitles(title);
                result.setAllData(dataResult);

            }else{              // If database already exists

                Vector<String> title = new Vector<>();
                title.add("Error unexpected");
    
                Vector<Vector<Object>> dataError = new Vector<>();
                Vector<Object> temp = new Vector<>();
                temp.add("Database "+ newDb + " already exists");
                dataError.add(temp);
    
                result.setTitles(title);
                result.setAllData(dataError);
            }
        } catch (Exception e) {
            Vector<String> title = new Vector<>();
            title.add("Error unexpected");

            Vector<Vector<Object>> dataError = new Vector<>();
            Vector<Object> temp = new Vector<>();
            temp.add(e.getMessage());
            dataError.add(temp);

            result.setTitles(title);
            result.setAllData(dataError);

        } finally{
            return result;
        }
    }
    
    // Searching the information about the creation
    public Table createTable(String request) throws Exception{
        Table result = new Table();

        try {
            String[] split = this.deleteAllNoChar(request);    // Get the request split
            String nameTab = this.searchTableName(request);
            
            File file = new File("DATABASE/" + this.getSkl().getNameUser() + "/"+ this.getSkl().getNameDb() +"/" +nameTab + ".txt");
            boolean create = file.createNewFile();          // Create a new File

            FileWriter fw = new FileWriter(file, false);

            fw.write(nameTab + "//");

            for(int i = 3; i < split.length; i++){      // Begenning at 3 (After the table name => All fieldname )
                fw.write(split[i] + ";");
            }
            fw.write("//");
            fw.close();

            Vector<String> title = new Vector<>();
            title.add("Creating database");             // Return always a Table that is personalized for each response

            Vector<Vector<Object>> dataResult = new Vector<>();
            Vector<Object> temp = new Vector<>();
            temp.add("Table "+ nameTab + " created");
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
        } finally{
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