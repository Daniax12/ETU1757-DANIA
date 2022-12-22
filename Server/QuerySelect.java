package bd;

import java.io.FileNotFoundException;
import java.util.Vector;

import bd.Table;
import model.Function;
import connection.*;

import java.io.*;

public class QuerySelect extends Function{

    Skl skl;

    public QuerySelect(Skl skl){
        this.setSkl(skl);
    }

    // Search the specific nameTab from the request (After FROM)
    public String searchTableName(String request) throws Exception{     
        String[] trigger = {"FROM", "from"};   
        String[] split = this.deleteAllNoChar(request);
        int indexOfFrom = this.getIndexOfTheWord(request, trigger);

        return split[indexOfFrom+1];
    }

    // Getting a table from request of SELECT * WITHOUT CONDITION
    public Table selectQueryGeneral(String request) throws Exception {       
                 
        String getTableName = this.searchTableName(request);                        // Get the nameTable     
        Table result = this.getSkl().getTableFromName(getTableName);                     // Get the specific table
    
        return result;       
    }

    // Getting a table from request of SELECT * WITH CONDITION
    public Table selectQueryGeneralWithCondition(String request) throws Exception {
        Table result = new Table();
   
        Table resultTemp = this.selectQueryGeneral(request);                        // Get the specific table (*)

        String[] split = this.deleteAllNoChar(request);                         // Split the request
                                 
        String[] trigger = {"FROM", "from"};        
        int indexOfForm = this.getIndexOfTheWord(request, trigger);             // Get the index of the word "TABLE" => where a = b
    
        String columnName = split[indexOfForm + 3];                               // Get the field of the name column criteria

        Vector titles = resultTemp.getTitles();                                 // Get the titles of the table to verify if the column name one of titles
        
        if(titles.contains(columnName) == false) throw new Exception("Invalid syntax insert");

        Object valueCondition = split[indexOfForm + 4];                         // To store data

        result = resultTemp.selectionTable(columnName, valueCondition);
        
        return result;
    }

    // Getting a table from request of SELECT * WITH OR CONDITION
    public Table selectQueryGeneralWithOrCondition(String request) throws Exception {
        
        Table result = new Table(); 

        String[] trigOr = {"OR", "or"};   
        String[] gateway = {"WHERE", "where"};

        String[] allPhrases = this.phrasesFromTrig(request, trigOr, gateway);       // Get all phrases split by triger OR and Gate WhERE

        Table firstTable = this.selectQueryGeneralWithCondition(allPhrases[0]);    // Get the first table selectionned   
        Table secondTable = this.selectQueryGeneralWithCondition(allPhrases[1]);  // Get the second table selectionned

        result = firstTable.unionTable(secondTable);

        return result;
    }

    // Getting a table from request with OR "&&" AND CONDITION
    public Table selectQueryGeneralWithOrAndCondition(String request) throws Exception {
        
        Table result = new Table(); 

        String[][] trigg = {{"OR", "or"}, {"AND","and"}};           // Trigger of the request
        String[] gateway = {"WHERE", "where"};

        String statuts = "single";
        Vector newRequest = this.allTablesAndSyntaxFromArrayTrig(request, trigg, gateway, statuts);
    
        result = this.getOneTableFromVectorOfSyntax(newRequest);
        return result;
    }

    // Getting a table General of SELECT * WITH AND CONDITION
    public Table selectQueryGeneralWithAndCondition(String request) throws Exception {
        Table result = new Table(); 
        
        String[] trigOr = {"AND", "and"};   
        String[] gateway = {"WHERE", "where"};

        String[] allPhrases = this.phrasesFromTrig(request, trigOr, gateway);       // Get all phrases split by triger OR and Gate WhERE

        Table firstTable = this.selectQueryGeneralWithCondition(allPhrases[0]);    // Get the first table selectionned   
        Table secondTable = this.selectQueryGeneralWithCondition(allPhrases[1]);  // Get the second table selectionned

        result = firstTable.intersectTable(secondTable);

        return result;
    }

    // Getting a table NOT general (NO SELECT (*) )
    public Table selectQueryNotGeneral(String request) throws Exception {
        Table result = new Table();
        Table resultTemp = this.selectQueryGeneral(request);        // Main table without  any condition

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = resultTemp.projectionTable(fieldRequest);          // Make the projection of the table from Maintable 
    
        return result;
    }

    // Getting a table NOT GENERAL WITH CONDITION
    public Table selectQueryNotGeneralWithCondition(String request) throws Exception {
        Table result = new Table();
        Table general_Table_With_Condition = this.selectQueryGeneralWithCondition(request);

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = general_Table_With_Condition.projectionTable(fieldRequest);
    
        return result;
    }

    // Getting a table NOT GENERAL WITH OR CONDITION
    public Table selectQueryNotGeneralWithOrCondition(String request) throws Exception {
        Table result = new Table();

        Table tempResult = this.selectQueryGeneralWithOrCondition(request);     // Get general table of the request selectioned

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = tempResult.projectionTable(fieldRequest);          // projection

        return result;
    }

    // Getting a table NOT GENERAL WITH AND CONDITION
    public Table selectQueryNotGeneralWithAndCondition(String request) throws Exception {
        Table result = new Table();

        Table tempResult = this.selectQueryGeneralWithAndCondition(request);     // Get general table of the request selectioned

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = tempResult.projectionTable(fieldRequest);          // projection

        return result;
    }

    // Getting a table NOT GENERAL WITH AND CONDITION
    public Table selectQueryNotGeneralWithOrAndCondition(String request) throws Exception {
        Table result = new Table();

        Table tempResult = this.selectQueryGeneralWithOrAndCondition(request);     // Get general table of the request selectioned

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = tempResult.projectionTable(fieldRequest);          // projection

        return result;
    }


    //// -------- CROSS JOIN -------- //
    // Getting a table CROSS JOIN General request
    public Table selectQueryGeneralCrossJoin(String request) throws Exception {
        Table result = new Table();
        String[] split = this.deleteAllNoChar(request);                 // Split the request

        String[] trigger = {"join", "JOIN"};
        int indexJoin = this.getIndexOfTheWord(request, trigger);       // Search the index of the word JOIN

        if(split[indexJoin+2].equals("ON") == false && split[indexJoin+2].equals("on") == false){
            throw new Exception("Invalid syntax on line 1, ON not found");      // When ON is not insert
        }
        
        String nameFirstTable = split[indexJoin-1];                             // from TAB1 JOIN TAB2 (Getting nameTable)
        String nameSecondTable = split[indexJoin+1];

        Table firstTable = this.getSkl().getTableFromName(nameFirstTable);       // Get the first table
        Table secondTable = this.getSkl().getTableFromName(nameSecondTable);     // Get the second table

        if((split[indexJoin+3].equals(nameFirstTable) == false && split[indexJoin+3].equals(nameSecondTable) == false) ||
            (split[indexJoin+5].equals(nameFirstTable) == false && split[indexJoin+5].equals(nameSecondTable) == false)){
            throw new Exception("Request ambigue from line 1");             // When tab.x or tab1.y is not referenced
        }

        // ****** Here, we consider that both field from table1 and table2 are the same ******
        int[] getIndexOfField = new int[2];         // Test wether field insert are in all both tables
        if(split[indexJoin+4].equals(split[indexJoin+6]) == false) throw new Exception("Verify field insert in condition line"); 

        getIndexOfField[0] = this.getIndexOfField(firstTable, split[indexJoin+4]);  // Verify if field is in table1
        getIndexOfField[1] = this.getIndexOfField(secondTable, split[indexJoin+4]); // Verify if field is in table2     => Else, exception

        result = firstTable.crossJoin(secondTable, split[indexJoin+4]);     // Cross join by field split[indexJoin+4]
        return result;
    }

    // Getting a table cross join General with condiotion
    public Table selectQueryGeneralCrossJoinWithCondition(String request) throws Exception {
        Table result = new Table();
        Table resultTemp = this.selectQueryGeneralCrossJoin(request);
        String[] split = this.deleteAllNoChar(request);

        String[] trigger = {"WHERE", "where"};      
        int indexWhere =  this.getIndexOfTheWord(request, trigger); // Searching WHERE index in the request

        String columnName = split[indexWhere + 1];                               // Get the field of the name column criteria
        
        Vector titles = resultTemp.getTitles();                                 // Get the titles of the table to verify if the column name one of titles
            
        if(titles.contains(columnName) == false) throw new Exception("Invalid syntax insert");

        Object valueCondition = split[indexWhere + 2];                         // To store data

        result = resultTemp.selectionTable(columnName, valueCondition);

        return result;
    }

    // Getting a table cross joined General with OR CONDITION
    public Table selectQueryGeneralCrossJoinWithOrCondition(String request) throws Exception {
        Table result = new Table(); 

        String[] trigOr = {"OR", "or"};   
        String[] gateway = {"WHERE", "where"};

        String[] allPhrases = this.phrasesFromTrig(request, trigOr, gateway);       // Get all phrases split by triger OR and Gate WhERE

        Table firstTable = this.selectQueryGeneralCrossJoinWithCondition(allPhrases[0]);    // Get the first table selectionned   
        Table secondTable = this.selectQueryGeneralCrossJoinWithCondition(allPhrases[1]);  // Get the second table selectionned

        result = firstTable.unionTable(secondTable);

        return result;
    }

    // Getting a table cross joined General with AND CONDITION
    public Table selectQueryGeneralCrossJoinWithAndCondition(String request) throws Exception {
        Table result = new Table(); 

        String[] trigOr = {"AND", "and"};   
        String[] gateway = {"WHERE", "where"};

        String[] allPhrases = this.phrasesFromTrig(request, trigOr, gateway);       // Get all phrases split by triger OR and Gate WhERE

        Table firstTable = this.selectQueryGeneralCrossJoinWithCondition(allPhrases[0]);    // Get the first table selectionned   
        Table secondTable = this.selectQueryGeneralCrossJoinWithCondition(allPhrases[1]);  // Get the second table selectionned

        result = firstTable.intersectTable(secondTable);

        return result;
    }

    // Getting a table cross joined whith OR/AND CONDITION
    public Table selectQueryGeneralCrossJoinWithOrAndCondition(String request) throws Exception {
        Table result = new Table(); 

        String[][] trigg = {{"OR", "or"}, {"AND","and"}};           // Trigger of the request
        String[] gateway = {"WHERE", "where"};

        String statuts = "join";
        Vector newRequest = this.allTablesAndSyntaxFromArrayTrig(request, trigg, gateway, statuts);
    
        result = this.getOneTableFromVectorOfSyntax(newRequest);
        return result;
    }

    // Getting a table CROSS JOIN Not General request
    public Table selectQueryNotGeneralCrossJoin(String request) throws Exception {
        Table result = new Table();
        Table resultTemp = this.selectQueryGeneralCrossJoin(request);        // Main table without  any condition

        String[] fieldRequest = this.allFieldRequest(request); // All field request

        result = resultTemp.projectionTable(fieldRequest);          // Make the projection of the table from Maintable 

        return result;
    }
    
    // Getting a table cross join not general with condition
    public Table selectQueryNotGeneralCrossJoinWithCondition(String request) throws Exception {
        Table result = new Table();

        Table general_Table_With_Condition = this.selectQueryGeneralCrossJoinWithCondition(request);

        String[] fieldRequest = this.allFieldRequest(request); // Gett all field request before FROM

        result = general_Table_With_Condition.projectionTable(fieldRequest);

        return result;
    }

    // Getting a table CROSS JOIN NOT GENERAL WITH OR CONDITION
    public Table selectQueryNotGeneralCrossJoinWithOrCondition(String request) throws Exception {
        Table result = new Table();

        Table general_Table_With_Condition = this.selectQueryGeneralCrossJoinWithOrCondition(request);

        String[] fieldRequest = this.allFieldRequest(request); // Gett all field request before FROM

        result = general_Table_With_Condition.projectionTable(fieldRequest);


        return result;
    }

    // Getting a table CROSS JOIN NOT GENERAL WITH AND CONDITION
    public Table selectQueryNotGeneralCrossJoinWithAndCondition(String request) throws Exception {
        Table result = new Table();

        Table general_Table_With_Condition = this.selectQueryGeneralCrossJoinWithAndCondition(request);

        String[] fieldRequest = this.allFieldRequest(request); // Gett all field request before FROM

        result = general_Table_With_Condition.projectionTable(fieldRequest);

        return result;
    }
    
    // Getting a table CROS JOIN NOT GENERAL WITH AND/OR CONDITION
    public Table selectQueryNotGeneralCrossJoinWithOrAndCondition(String request) throws Exception {
        Table result = new Table();
        Table tempResult = this.selectQueryGeneralCrossJoinWithOrAndCondition(request);     // Get general table of the request selectioned

        String[] fieldRequest = this.allFieldRequest(request);  // All field requested

        result = tempResult.projectionTable(fieldRequest);          // projection
        return result;
    }

    /// ----- Usefull this --- //

    // Get two or more TABLE with OR "&&" AND syntax
    // -------> SELECT * FROM TAB WHERE A = B OR C = K AND  O = M
    // => TABLE from SELECT * FROM TAB WHERE A = B
    // => OR (union)
    // => TABLE from SELECT * FROM TAB WHERE C = K
    // => AND (intersect)
    // => TABLE from SELECT * FROM TAB WHERE O = M
    public Vector allTablesAndSyntaxFromArrayTrig(String request, String[][] trigg, String[] gateway, String tableStatus) throws Exception{
        Vector result = new Vector<>();
        Vector<String> getPhrases = this.phrasesFromArrayTrig(request, trigg, gateway);
        int tableSize = (getPhrases.size()/2) +1;

        Table[] allTable = new Table[tableSize];        // All table from allrequest

        int index = 0;
        for(int i = 0; i < getPhrases.size(); i = i + 2){
            //System.out.println(getPhrases.get(i));
            if(tableStatus.equals("single") == true){
                allTable[index] = this.selectQueryGeneralWithCondition(getPhrases.get(i));  // Set all table from request / selection each request <=> Single table
            }else if(tableStatus.equals("join") == true){
                allTable[index] = this.selectQueryGeneralCrossJoinWithCondition(getPhrases.get(i));   // <=> Table joined
            }
            
            index++;
        }

        int count = 0;
        for(int i = 1; i < getPhrases.size(); i = i + 2){
            result.add(allTable[count]);                        // Adding table
            count++;
            result.add(getPhrases.get(i));                      // Adding trigger
        }
        result.add(allTable[allTable.length-1]);                // Adding last table because loop is not enough for last table
        return result;
    }

    // Get only one table from Vector of table syntax above
    public Table getOneTableFromVectorOfSyntax(Vector newRequest) throws Exception{
        Table result = new Table();
        while(newRequest.size() != 1){          // Loop until the vector size is reduced to 1 => means the table result from * and + 

            if(newRequest.contains("AND") == true || newRequest.contains("and") == true){       // Intersect is priority (*)

                for(int i = 0; i < newRequest.size(); i++){
                    if(newRequest.get(i).equals("and") == true || newRequest.get(i).equals("AND") == true){     // get the index of AND or and
                        Table temp = ((Table) newRequest.get(i-1)).intersectTable((Table) newRequest.get(i+1));         // x * y   (Intersect tables concerned)
                        newRequest.add(i-1, temp);                                                                      // = b      (The result of the *) insert into the mother vector
                        newRequest.subList(i, i+3).clear();                                                             // delete x * y from the vector
                    }
                }

            }else if((newRequest.contains("AND") == false || newRequest.contains("and") == false) && 
                (newRequest.contains("OR") == true || newRequest.contains("or") == true)){                      // Enter in this condition only if there in no AND or and int the vector

                    for(int i = 0; i < newRequest.size(); i++){
                        if(newRequest.get(i).equals("or") == true || newRequest.get(i).equals("OR") == true){
                            Table temp = ((Table) newRequest.get(i-1)).unionTable((Table) newRequest.get(i+1));             // Same as * but with + (Union)
                            newRequest.add(i-1, temp);
                            newRequest.subList(i, i+3).clear();
                    }
                }
            }   
        }
        result = (Table) newRequest.get(0);     // Get the first index because it is the result of all operations  (result = a + b * x * y + l)

        return result;
    }


    // Get two table from NOT IN
    // (SELEC * FROM TAB1) NOT IN (SELECT * FROM TAB2)
    // => SELECT * FROM TAB1
    // => SELECT * FROM TAB2

    public String[] getArrayTableForDifference(String request) throws Exception{
        
        String[] result = new String[2];

        String[] not = {"NOT", "not"};
        String[] in = {"IN", "in"};
        
        int indexNot = this.getIndexOfTheWord(request, not);
        int indexIn = this.getIndexOfTheWord(request, in);

        if(indexNot != indexIn - 1) throw new Exception("Invalid syntax at line 1");
        else{

            String[] split = this.deleteAllNoChar(request);

            String req1 = "";
            String req2 = "";

            for (int i = 0; i < split.length; i++) {
                if(i == indexIn || i == indexNot){

                } else{
                    if(i < indexIn){
                        req1 += split[i] + " ";
                    } else if( i > indexNot){
                        req2 += split[i] + " ";
                    }
                }
            }

            result[0] = req1;
            result[1] = req2;

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