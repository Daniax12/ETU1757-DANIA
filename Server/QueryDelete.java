package bd;

import java.util.Vector;
import connection.*;

import model.Function;

public class QueryDelete extends Function{

    Skl skl;

    public QueryDelete(Skl skl){
        this.setSkl(skl);
    }

    // Search the tableName from request
    public String searchTableName(String request) throws Exception{         
        String[] trigger = {"FROM", "from"};   
        String[] split = this.deleteAllNoChar(request);
        int indexOfFrom = this.getIndexOfTheWord(request, trigger);

        return split[indexOfFrom+1];
    }

    // Searching the criteria and the value
    public Vector<Object> getDataToDelete(String request) throws Exception{
        String[] split = this.deleteAllNoChar(request);     // Get the request splited

        Vector<Object> result = new Vector<>();
        String nameTable = this.searchTableName(request);           // Search the nameTable
        Table mainTable = this.getSkl().getTableFromName(nameTable);     // Get the table concerned
        Vector titles = mainTable.getTitles();                      // Get the titles of the table


        if(split.length != 3){
            if(split[3].equals("WHERE") == false && split[3].equals("where") == false) throw new Exception("Invalid syntax in line 1");       
            String verifyColumn = split[4];                     // [4] must be WHERE syntax
    
            if(titles.contains(verifyColumn) == false) throw new Exception("Please check field insert");    // after Where, must be a field in the table
    
            int[] allIndex = this.getIndexOfSpecifiedValue(mainTable, verifyColumn, split[5]);          // Get the specific index ROW of the condition
            
            result.add(allIndex);
            result.add(verifyColumn);                                   //Inserting to the vector(Information about the deleting process)
            result.add(split[5]);
        } else {
            result.add("allrow");          // If split == 3 => Then delete all row
        }
        return result;
    }

    // Deleting the row from table
    public Table deleteContentDb(String request)throws Exception{ 

        Table result = new Table();

        try {
    
            String nameTable = this.searchTableName(request);           // Search the nameTable
            Table mainTable = this.getSkl().getTableFromName(nameTable);     // Get the table concerned
            Vector<Vector<Object>> data = mainTable.getAllData();       // Get data of the table


            Vector<Object> getInfoDelete = this.getDataToDelete(request);
            Vector<Vector<Object>> dataResult = new Vector<>();
            Vector<Object> temp = new Vector<>();
            Vector<String> title = new Vector<>();
            title.add("Deleting data");             // Return always a Table that is personalized for each response

            if(getInfoDelete.size() != 1){
                int[] getIndex = (int[]) getInfoDelete.get(0);        
    
                for(int i = 0; i < getIndex.length; i ++){
                    data.remove(getIndex[i]);                               // Deleteting all row that correspond the index of the value criteria
                }
    
                temp.add("Line "+ getIndex.length + " deleted");
                dataResult.add(temp);
  
            } else{

                int s = data.size();
                data.clear();
                
                temp.add("Line "+ s + " deleted");
                dataResult.add(temp);
            }
            result.setTitles(title);
            result.setAllData(dataResult);

            // mainTable.viewTable();                      
            this.insertTableInFile(this.getSkl().getNameUser(), this.getSkl().getNameDb(), mainTable);                      // Changing the database
            

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