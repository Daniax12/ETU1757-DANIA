package connection;

import bd.*;
import model.*;
import java.io.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Skl extends Function{

    private String nameDb;
    private String nameUser;

    public Table connectionQuery(String request) throws Exception{

        Table table = new Table();

        String[] split = this.deleteAllNoChar(request);
        int count = split.length;    
        try {
        
            if((split[0].equals("SHOW") == true || split[0].equals("show") == true) 
                && (split[1].equals("DATABASE") == true || split[1].equals("database") == true )){  // Request of the showing databases
                    table = this.showDatabase();

            } else if(this.getNameDb() == null && split[0].equals("USE") == false && split[0].equals("use") == false
                && split[0].equalsIgnoreCase("create") == false){     // No db mentionned
                    Vector<String> title = new Vector<>();
                    title.add("Error unexpected");
        
                    Vector<Vector<Object>> dataError = new Vector<>();
                    Vector<Object> err = new Vector<>();
                    err.add("DATABASE NOT SELECTED");
                    dataError.add(err);
        
                    table.setTitles(title);
                    table.setAllData(dataError);
                return table;
            } else if(split[0].equalsIgnoreCase("use") == true){       // Setting the dbName

                String dbName = split[1];

                Vector<String> title = new Vector<>();
    
                Vector<Vector<Object>> dataError = new Vector<>();
                Vector<Object> err = new Vector<>();

                if(split.length != 2){
                    title.add("Error unexpected");
                    err.add("SYNTAX ERROR AT LINE 1");
                    dataError.add(err);
                    table.setTitles(title);
                    table.setAllData(dataError);
                    return table;
                } else{                   // If db is not mentionned
                    try {
                        File folder = new File("DATABASE/"+ this.getNameUser()+ "/" + dbName);
                        if(folder.exists() == false) {                      // Check if db mentioned exists
                            title.add("Error unexpected");
                            err.add("DATABASE NOT FOUND");
                            dataError.add(err);                     
                        } else {

                            this.setNameDb(dbName);
                            title.add("Database");
                            err.add("Database changed to "+dbName);     // If the database exists
                            dataError.add(err);              
                        }
                    } catch (Exception e) {
                        err.add("DATABASE NOT FOUND"); 
                        dataError.add(err);

                    } finally {
                        table.setTitles(title);
                        table.setAllData(dataError);
                        return table;
                    }
                }
            } else if(split[0].equalsIgnoreCase("show") == true){

                if(split[1].equals("DATABASE") == true || split[1].equals("database") == true){
                    table = this.showDatabase();
                } else if(split[1].equals("TABLES") == true || split[1].equals("tables") == true){
                    table = this.showTables();
                } else{
                    table.setName("Invalid syntax in line 1");
                }
                return table;
            } else if(split[0].equalsIgnoreCase("create") == true){    // Creating table
                QueryCreate createTable = new QueryCreate(this);
                
                if((split[1].equals("DATABASE") == true || split[1].equals("database") == true) && split.length == 3){
                    table = createTable.createDatabase(request);     // Creating database
                } else {
                    if(this.getNameDb() == null){
                        Vector<String> title = new Vector<>();
                        title.add("Error unexpected");

                        Vector<Vector<Object>> dataError = new Vector<>();
                        Vector<Object> err = new Vector<>();
                        err.add("DATABASE NOT SELECTED");
                        dataError.add(err);
            
                        table.setTitles(title);
                        table.setAllData(dataError);
                    } else{
                        table = createTable.createTable(request);          // Crating table
                    }
                }
                
                return table;

            } else if(split[0].equalsIgnoreCase("update") == true){   // Updating table (update tab set a = b where c = d) => 8 words

                QueryUpdate update = new QueryUpdate(this);                     // Instantiate update class
                table = update.modifyContentDb(request);
                return table;
                

            } else if(split[0].equalsIgnoreCase("delete") == true){   // Deleting table

                QueryDelete delete = new QueryDelete(this);                     // Instantiate delete class
                table  = delete.deleteContentDb(request);
                return table;

            } else if(split[0].equalsIgnoreCase("insert") == true){    // Inserting table
                QueryInsert insert = new QueryInsert(this);
                table = insert.insertDataToDb(request);
                return table;


            } else if(split[0].equalsIgnoreCase("select") == true){   // select * or fields[]
            
                String[] join = {"JOIN", "join"};                              // usefull when we count word join in request, for OR && AND request
                int countJoin = this.countSpecificString(request, join);    // Count join word

                String[] and = {"AND", "and"};                              // usefull when we count word AND in request, for OR && AND request
                int countAnd = this.countSpecificString(request, and);  // Count join AND word

                String[] or = {"OR", "or"};                                 // usefull when we count word OR in request, for OR && AND request
                int countOr = this.countSpecificString(request, or);    // Count OR word

                String[] sel = {"SELECT", "select"};
                int countSelect = this.countSpecificString(request, sel);       // Count 

                QuerySelect select = new QuerySelect(this);                        // Instantiate select class
                                            

                if(countSelect == 1){       // ONE SELECT OTHERWISE DIFFERENCE

                    if(split[1].equals("*") == true){                // First of all, IF THERE IS * IN DA REQUEST (GENERAL REQUEST)
    
                        if(count == 4){                                      // SELECT * FROM TABLE (4)
                            table = select.selectQueryGeneral(request);
    
                        } else if(split[4].equals("WHERE") == true ||  split[4].equals("where") == true){     // SELECT * FROM TABLE WHERE :
                            
                            if(split.length == 7) table = select.selectQueryGeneralWithCondition(request);    // SELECT * FROM TABLE WHERE X = Y
                            
                            else if(split.length == 10){                                                      // SELECT * FROM TABLE WHERE X = Y OR A = B // AND A = B
                                if(split[7].equals("OR") == true || split[7].equals("or") == true) table = select.selectQueryGeneralWithOrCondition(request);   // OR Condition
                                else if(split[7].equals("AND") == true || split[7].equals("and") == true) table = select.selectQueryGeneralWithAndCondition(request);   // AND Condition
                            } else if(split.length > 10) table = select.selectQueryGeneralWithOrAndCondition(request);   // OR AND condition
                            
                        }else if(split[4].equals("JOIN") == true ||  split[4].equals("join") == true){    // SELECT * FROM TABLE JOIN ANOTHER TABLE
    
                            if(count == 11) table = select.selectQueryGeneralCrossJoin(request);                                 // (select * from tab1 join tab2 on tab.x = tab1.y) 
                                
                            else{
                                if(split[11].equals("WHERE") == true || split[11].equals("where") == true){
    
                                    if(split.length == 14) table = select.selectQueryGeneralCrossJoinWithCondition(request);    // (select * from tab1 join tab2 on tab.x = tab1.y where a = b or/and h = k) 
                                    else{
                                        if((split[14].equals("OR") == true || split[14].equals("or") == true || split[14].equals("AND") == true || split[14].equals("and") == true)){
                                            if((split[14].equals("OR") == true || split[14].equals("or") == true) && (countAnd == 1 || countOr == 1)) {
                                                table = select.selectQueryGeneralCrossJoinWithOrCondition(request);                  // Cross join WITH OR only
                                            }else if((split[14].equals("AND") == true || split[14].equals("and") == true) && (countAnd == 1 || countOr == 1)){
                                                table = select.selectQueryGeneralCrossJoinWithAndCondition(request);                  // Cross join WITH AND only
                                            } else if ((countAnd > 1 || countOr > 1) || (countAnd == 1 && countOr == 1) || (countAnd > 1 || countOr == 1) || (countAnd == 1 || countOr > 1)){
                                                    //System.out.println("Fuck heer");
                                                    table = select.selectQueryGeneralCrossJoinWithOrAndCondition(request);
                                            }
                                        }
                                    }
                                }
                            }
                        }
    
                        
                    }else if(split[1].equals("*") == false ){       // IF THERE IS NO * IN DA REQUEST
    
                        if (split[count-2].equals("FROM") == true || split[count-2].equals("from") == true){  // SELECT nameField[] from (index[-2]) table
                            table = select.selectQueryNotGeneral(request);
    
                        } else if(split[count-5].equals("FROM") == true || split[count-5].equals("from") == true){      // SELECT nameField[] from table where a = b
                            table = select.selectQueryNotGeneralWithCondition(request);
    
                        } else if(split[count-8].equals("FROM") == true || split[count-8].equals("from") == true){      // SELECT nameField[] from table where a = b and/or b = h
    
                            if (split[count-3].equals("OR") == true || split[count-3].equals("or") == true){
                                table = select.selectQueryNotGeneralWithOrCondition(request);            // Select not general with OR
                            } else if(split[count-3].equals("AND") == true || split[count-3].equals("and") == true){
                                table = select.selectQueryNotGeneralWithAndCondition(request);            // Select not general with AND
                            }
    
                        } else if(countJoin == 0 && ((countAnd > 1 || countOr > 1) || (countAnd == 1 && countOr == 1) || (countAnd > 1 || countOr == 1) || (countAnd == 1 || countOr > 1))){
                            table = select.selectQueryNotGeneralWithOrAndCondition(request);                     // Select not general with AND && OR condition 
                        } 
                        else if(split[count-7].equals("JOIN") == true || split[count-7].equals("join") == true){
                            table = select.selectQueryNotGeneralCrossJoin(request);                      // Select not general cross join without condition
                        
                        } else if(split[count-10].equals("JOIN") == true || split[count-10].equals("join") == true){
                            table = select.selectQueryNotGeneralCrossJoinWithCondition(request);         // Select not general cross join with one condition
                        
                        } else if(split[count-13].equals("JOIN") == true || split[count-13].equals("join") == true){
                            if (split[count-3].equals("OR") == true || split[count-3].equals("or") == true){
                                table = select.selectQueryNotGeneralCrossJoinWithOrCondition(request);            // Select not general with OR condition Cross join
                            } else if(split[count-3].equals("AND") == true || split[count-3].equals("and") == true){
                                table = select.selectQueryNotGeneralCrossJoinWithAndCondition(request);            // Select not general with AND condition Cross Join  
                            }
                        } else if(countJoin > 0 && ((countAnd > 1 || countOr > 1) || (countAnd == 1 && countOr == 1) || (countAnd > 1 || countOr == 1) || (countAnd == 1 || countOr > 1))){
                            table = select.selectQueryNotGeneralCrossJoinWithOrAndCondition(request);    // Select not general with AND && OR condition  CROSS JOIN
                        }
                    }
                    //table.viewTable(); 
                    return table;  
                } else if (countSelect == 2) {
                    return selectDifferenceTable(request);
                } else {
                    Vector<String> title = new Vector<>();
                    title.add("Error unexpected");
        
                    Vector<Vector<Object>> dataError = new Vector<>();
                    Vector<Object> err = new Vector<>();
                    err.add("SYNTAX ERROE AT LINE 1");
                    dataError.add(err);
        
                    table.setTitles(title);
                    table.setAllData(dataError);
                }
            } else {
                Vector<String> title = new Vector<>();
                title.add("Error unexpected");
    
                Vector<Vector<Object>> dataError = new Vector<>();
                Vector<Object> err = new Vector<>();
                err.add("SYNTAX ERROR AT LINE 1");
                dataError.add(err);

                table.setTitles(title);
                table.setAllData(dataError);
            }             
        
        } catch (Exception e) {
            Vector<String> title = new Vector<>();
            title.add("Error unexpected");

            Vector<Vector<Object>> dataError = new Vector<>();
            Vector<Object> err = new Vector<>();
            //err.add(e.getMessage());
            err.add("SYNTAX ERROR AT LINE 1");
            
            dataError.add(err);

            table.setTitles(title);
            table.setAllData(dataError);
        } finally{
            return table;
        }
    }

    // Usefull function
    // Show all databases in the folder user
    public Table showDatabase() throws Exception{
        Table result = new Table();
        Vector<String> col = new Vector<>();
        col.add("Databases");
        result.setTitles(col);      // Titles of the tables

        try {
            File folder = new File("DATABASE/"+ this.getNameUser());
            String[] all = folder.list();

            Vector<Vector<Object>> data = new Vector<>();

            for (int i = 0; i < all.length; i++) {
                Vector<Object> tempVect = new Vector<>();
                tempVect.add(all[i]);
                data.add(tempVect);
            }
            result.setAllData(data);
        } catch (Exception e) {
            result.setName(e.getMessage());     // Name of db not mentioned yet
        } finally {
            return result;
        }
    }


    // Show all tables in the database
    public Table showTables() throws Exception{
        Table result = new Table();
        Vector<String> col = new Vector<>();
        col.add("Tables");
        result.setTitles(col);      // Titles of the tables

        try {
            File folder = new File("DATABASE/"+ this.getNameUser()+ "/" + this.getNameDb());
            String[] all = folder.list();

            StringBuilder[] temp = new StringBuilder[all.length];

            for (int i = 0; i < temp.length; i++) {
                temp[i] = new StringBuilder();
                temp[i].append(all[i]);
            }

            Vector<Vector<Object>> data = new Vector<>();

            for (int i = 0; i < temp.length; i++) {
                Vector<Object> tempVect = new Vector<>();
                temp[i].delete(temp[i].length()-4, temp[i].length());     // Delete the .txt
                tempVect.add(temp[i].toString());
                data.add(tempVect);
            }
            result.setAllData(data);
        } catch (Exception e) {
            
            result.setTitles(null);
            result.setName(e.getMessage());     // Name of db not mentioned yet
        } finally {
            return result;
        }
    }


    // Getting table NOT IN (SUBQUERY)
    public Table selectDifferenceTable(String request) throws Exception { 
        QuerySelect sel = new QuerySelect(this);
        String[] allReq = sel.getArrayTableForDifference(request);

        Table tab1 = this.connectionQuery(allReq[0]);
        Table tab2 = this.connectionQuery(allReq[1]);

        Vector<String> tit1 = tab1.getTitles();
        Vector<String> tit2 = tab2.getTitles();

        if(tit1.size() != tit2.size()) throw new Exception("Tables are not compatibles for difference");

        for(int i = 0; i < tit1.size(); i++){
            if(tit1.get(i).equals(tit2.get(i)) == false) throw new Exception("Tables are not compatibles for difference");
        }

        Table result = tab1.differencTable(tab2);
        return result;

    }

    // Searching a table from its name
    public Table getTableFromName(String nameTable) throws Exception{
        Table result = new Table();

        try {
            File file = new File("DATABASE/"+ this.getNameUser()+ "/" + this.getNameDb() +"/"+ nameTable + ".txt");
            String longtext = this.getDataToVectoFromFile(file);
            result = this.getTableOneData(longtext);
        } catch (Exception e) {
            throw new Exception("Table not found");
        }
        return result;
    }

    public String getNameDb() {
        return nameDb;
    }

    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}