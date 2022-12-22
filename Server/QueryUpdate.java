package bd;

import java.util.Vector;

import model.Function;
import connection.*;

public class QueryUpdate extends Function{

    Skl skl;

    public QueryUpdate(Skl skl){
        this.setSkl(skl);
    }

    // Get field to be changed, new data and the condition
    public Vector<Object> getDataModification(String request) throws Exception{
        String[] splitRequest = this.deleteAllNoChar(request);  // Get the request split

        Vector<Object> result = new Vector<>();

        Table mainTable = this.getSkl().getTableFromName(splitRequest[1]);     // Get the table concerned => Must be update tab (index 1)
        Vector titles = mainTable.getTitles();                      // Get the titles of the table

        if(splitRequest.length != 8) throw new Exception("Missing syntax");
        if(splitRequest[2].equals("set") == false && splitRequest[2].equals("SET") == false) throw new Exception("Syntax unkown at line 1");
        if(splitRequest[5].equals("where") == false && splitRequest[5].equals("WHERE") == false) throw new Exception("Syntax unkown at line 1");

        String[] verifyColumn = new String[2];                      // Get field name to be changed and the criteria field of the request 
        verifyColumn[0] = splitRequest[3];
        verifyColumn[1] = splitRequest[6];

        for(int i = 0; i < verifyColumn.length; i++){
            if(titles.contains(verifyColumn[i]) == false) throw new Exception("Please verify field name insert");   // Verified if the field insert are table's field
        }

        result.add(splitRequest[3]); result.add(splitRequest[4]); result.add(splitRequest[6]); result.add(splitRequest[7]);     // Insert fields/new value ; fieldsCriteria/value
        return result;
    }   


    // Modification of the table
    public Table modifyContentDb(String request) throws Exception{

        Table result = new Table();

        try {
            String[] splitRequest = this.deleteAllNoChar(request);  // Get the request split
            Table mainTable = this.getSkl().getTableFromName(splitRequest[1]);     // Get the table concerned => Must be update tab (index 1)
    
            Vector<Object> toBeChange = this.getDataModification(request);      // Get content information of the modification 
            String criteriaField = String.valueOf(toBeChange.get(2));     // The field trigger
            Object criteriaObj = toBeChange.get(3);                      // data of field trigger
    
           
            String fieldToChange = String.valueOf(toBeChange.get(0));    // The field that data has to be changed
            Object newValue = toBeChange.get(1);                        // The new value of the field below
    
            Vector<Vector<Object>> getRowData = mainTable.getAllData();         // Get rowdata of the table
            int[] getIndex = this.getIndexOfSpecifiedValue(mainTable, criteriaField, criteriaObj);  // Get the index of the row to be changed
            int indexOfField = this.getIndexOfField(mainTable, fieldToChange);  // Index of the field to be change
    
            for (int i = 0; i < getIndex.length; i++) {
                getRowData.get(getIndex[i]).set(indexOfField, newValue);   // Changing the value
            }
            
            this.insertTableInFile(this.getSkl().getNameUser(), this.getSkl().getNameDb(), mainTable);                           // Changing the value in table

            Vector<String> title = new Vector<>();
            title.add("Modifying data in " + splitRequest[1]);             // Return always a Table that is personalized for each response

            Vector<Vector<Object>> dataResult = new Vector<>();
            Vector<Object> temp = new Vector<>();
            temp.add("Modification successful");
            dataResult.add(temp);

            result.setTitles(title);
            result.setAllData(dataResult);

            result.setName("1 Line modified");
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