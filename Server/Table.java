package bd;

import java.io.Serializable;
import java.util.Vector;

import model.Function;

public class Table implements Serializable{
    String name;
    Vector titles;
    Vector<Vector<Object>> allData;


    // Calculate column row(Titles)
    public int getColumnNum(){
        return this.getTitles().size();
    }

    // Calculate row(Data)
    public int getRowData(){
        return this.getAllData().size();
    }

    // Verify some incompatibilities of two tables
    public void verifyIncompatibilities(Table table) throws Exception{
        int numRowFirst = this.getRowData();
        int numRowSecond = table.getRowData();

        int numColFirst = this.getColumnNum();
        int numColSeconde = table.getColumnNum();

        if(numColFirst != numColSeconde) throw new Exception("Table incompatible to be unified: Column number are not the same");   // If num column are not same

    }

    // UNION FROM TWO TABLES
    public Table unionTable(Table table) throws Exception{

        int numRowFirst = this.getRowData();
        int numRowSecond = table.getRowData();

        this.verifyIncompatibilities(table);        // Verify incompatibilities

        Table result = new Table();
        result.setName("Union Table");
        result.setTitles(table.getTitles());

        result.setAllData(this.getAllData());                               // Add to new table data of the object appelant
        for(int i = 0; i < numRowSecond; i++){
            if(result.getAllData().contains(table.getAllData().get(i)) == false){   // Delete doublons in the table
                result.getAllData().add(numRowFirst, table.getAllData().get(i)); // Add to new table data of the second table
            }
        }
        return result;
    }


    // INTERSECT FROM TWO TABLES
    public Table intersectTable(Table table) throws Exception{          
        Table result = new Table();
        result.setName("Intersect between two tables");
        result.setTitles(this.getTitles());

        int numRowFirst = this.getRowData();
        int numRowSecond = table.getRowData();


        this.verifyIncompatibilities(table);        // Verify incompatibilities

        Vector<Vector<Object>> data = new Vector<>();
        for(int i = 0; i < numRowFirst; i++){
            for (int j = 0; j < numRowSecond; j++) {
                if(this.getAllData().get(i).equals(table.getAllData().get(j))){     // Condition if all data of both rows are the same
                    data.add(this.getAllData().get(i));
                }
            }
        }
        result.setAllData(data);
        return result;
    }


    // SELECTION OF THE TABLE
    public Table selectionTable(String nameCol, Object obj) throws Exception{
        Integer index = null;
        for(int i = 0; i < this.getTitles().size(); i++){
            if(this.getTitles().get(i).equals(nameCol)) index = Integer.valueOf(i);     // Verify the name of the field requested
        }

        if(index == null) throw new Exception("Unknow name column");

        Table result = new Table();
        result.setName("WHERE " + nameCol + " = " + obj);               // Naming the table (Facultatif)
        result.setTitles(this.getTitles());                             //  Set titles, same as this (*)

        Vector<Vector<Object>> toInsert = new Vector<>();
        for(int i = 0; i < this.getRowData(); i++){
            if(this.getAllData().get(i).get(index).equals(obj) == true){
                Vector<Object> temp = this.getAllData().get(i);
                toInsert.add(temp);                                     // Inserting the row which match with the condition
            }
        }
        result.setAllData(toInsert);
        // result.viewTable();
        return result;
    }

    // PROJECTION OF TABLE
    public Table projectionTable(String[] nameCol) throws Exception{

        Vector<Integer> index = new Vector<>();                        // To allocate the index of nameCol if namecol == fieds of the table
        Table result = new Table();                                   // Result table
        result.setName("Projection");                           // Set the title of the table, the attribute request

        for(int j = 0; j < nameCol.length; j++){
            for(int i = 0; i < this.getTitles().size(); i++){
                if(this.getTitles().get(i).equals(nameCol[j]) == true){
                    index.add(i);                                            // Get all index of the attribute insert
                }
            }                 
        }

        Vector titlesRes = new Vector<>();                             // Allocate to the titles of the new tables
        for(int i = 0; i < nameCol.length; i++){
            titlesRes.add(nameCol[i]);                                 // Set the array nameCol as titles of the new table
        }
        if(index.size() != nameCol.length) throw new Exception("Unknown name column");        // Wether some attribute insert are invalid
        
        result.setTitles(titlesRes);
        
        Vector<Vector<Object>> data = new Vector<>();               // Allocate the data pf the table
        
        if(nameCol.length == 1){                                    // If single, must delete doublons 
            
            for(int i = 0; i < this.getRowData(); i++){

                Vector<Object> temp = new Vector<>();               // Temporary vector to insert vector<Object>
                temp.add(this.getAllData().get(i).get(index.get(0)));

                if(data.contains(temp) == false){                   // Checking the values insert into data, checking doublons
                    data.add(temp);                                 // Get all object at the specified attribute request
                }            
            }

        }else{                                                      // Else multiple attribute 

            for(int i = 0; i < this.getAllData().size(); i++){
                Vector<Object> temp = new Vector<>();
                for(int k = 0; k < index.size(); k++){
                    temp.add(this.getAllData().get(i).get(index.get(k)));      // Get all object at the specific index of the column requested
                }
                data.add(temp);
            }
        }
        
        result.setAllData(data);
        return result;
    }

    // PRODUIT CARTESIEN
    public Table cartesianProduct(Table table){
        Table result = new Table();

        int sizeColFirst = this.getTitles().size();             // Because we are about changing this.titles

        result.setTitles(this.getTitles());                         // Adding this titles table to new table

        for(int i = 0; i < table.getTitles().size(); i++){
            result.getTitles().add(table.getTitles().get(i));       // Adding the second table titles to new table
        }
        
        Vector<Vector<Object>> motherTemp = new Vector<>();         // Allocating for the entire data
        for(int i = 0; i < this.getRowData(); i++){                 // loop until the row data number of this table
            for(int j = 0; j < table.getRowData(); j++){            // loop until the row data number of the table to product
                Vector<Object> temp = new Vector<>();               // Allocation of temporary vector to insert A ROW (this.data + tableToProduct.data :each line)
                for(int h = 0; h < sizeColFirst; h++){       // Loop column of this.table
                    temp.add(this.getAllData().get(i).get(h));       // Adding the row data of this.table into temporary vect
                }
            
                for(int k = 0; k < table.getColumnNum(); k++){    // Loop column of tableToproduct
                    temp.add(table.getAllData().get(j).get(k));   // Insert tableToproduct.data at the end of the data insert above
                }
                motherTemp.add(temp);                             // Insert the entire data line in the data for the result table
            }
        }
        result.setAllData(motherTemp);                          // Setting the result table data
        return result;

    }

    // CROSS JOIN
    public Table crossJoin(Table table, String nameCol) throws Exception{
        Table result = new Table();
        Function function = new Function();

        int colSize = this.getColumnNum();          // Get the size of the column before exploting this table ***** BE CAREFULL THOSE ARE POINTER

        int indexColThis = function.getIndexOfField(this, nameCol);     // Store the index of nameCol of this.table
        int indexColTable = function.getIndexOfField(table, nameCol);   // Store the index of nameCol of other.table

        Table productTable = this.cartesianProduct(table);              // Product cartesian
        int newIndexColTable = colSize + indexColTable;                 // Index of the colname (other.table) in the table cartesianed
        Vector<Vector<Object>> data = new Vector<>();                   // Allocate for the data of result table

        Vector<Vector<Object>> dataFromProduct = productTable.getAllData(); 

        for(int i = 0; i < dataFromProduct.size(); i++){
            if(dataFromProduct.get(i).get(indexColThis).equals(dataFromProduct.get(i).get(newIndexColTable)) == true){  // Comparing the value from this.table and other table at the nameCol Field
                data.add(dataFromProduct.get(i));   // Inserting row of values into data 
            }
        }

        // Removing the column of the other.table in the new Table (Titles and data)
        Vector tempTitles = productTable.getTitles();
        tempTitles.remove(newIndexColTable);

        for(int i = 0; i < data.size(); i++){
            data.get(i).remove(newIndexColTable);
        }

        // Setting result table
        result.setName("Cross Join between " + this.getName() + " and " + table.getName() + " at "+ nameCol);
        result.setTitles(tempTitles);
        result.setAllData(data);

        return result;
    }


    // DIFFERENCE
    public Table differencTable (Table table2) throws Exception{

        this.verifyIncompatibilities(table2);
        Table result = new Table();
        Vector<Vector<Object>> data1 = new Vector<>();
        Vector<Vector<Object>> data2 = new Vector<>();

        if(this.getAllData().size() >= table2.getAllData().size()){
            data1 = this.getAllData();
            data2 = table2.getAllData();
        }else{
            data1 = table2.getAllData();
            data2 = this.getAllData();
        }

        result.setName("Difference");
        result.setTitles(this.getTitles());       // Set wether this or table2 titles => should be the same table column

        for(int i = 0; i < data2.size(); i++){      // Loop the second table
            for(int k = 0; k < data1.size(); k++){
                if(data2.get(i).equals(data1.get(k)) == true){  // Check if this table has same value with the second one
                    data1.remove(data1.get(k));                 // If so, delete the content
                }
            }
        }
        result.setAllData(data1);

        return result;
    }

    // DIVISION
    public Table divisionTable(Table table, String toVerify, String allData) throws Exception{
       // Table div = test3.divisionTable(test1, "idsubject", "idstudent");
       // donne-moi les bars (id_bar) qui servent (id_bar,id_biere) toutes les bières (id_biere) in Db

        // From the example above, we have to check idStudent that matches all idsubject values
        // toVerify is string to check => ALLDATA
        // This = R1 => A relation between IDSTUDENT and IDSUBJECT
        // Table = R2   => A relation of all IDSUBJECT
        
        Table result = new Table();
        String[] firstCond = {toVerify};      // For projection first table
        String[] secCond = {allData};        // For projection second table

        Table first = this.projectionTable(secCond);   // R3 = Projection (R1) toverify // Projection IDSTUDENT
        Table firstCo = this.projectionTable(secCond);  // Clone the R3 because we have to gauge it after

        Table second = table.projectionTable(firstCond); // R4 = Projection (R2) allData => Projection ALL IDSUBJECT VALUES 

        Table third = first.cartesianProduct(second);   // R5 = Produit Cartesion R3 * R4 => Show all possibilities // IDSUBJECT with IDSTUDENT
        
        Table fourth = third.differencTable(this);     // R6 = Difference all possibilities and those in R1     
                                                        // Get those who are not in the relation between IDSTUDENT and IDSUBJECT


        Table fifth = fourth.projectionTable(secCond); // R7 = Projection of all exception in last table
                                                        // Projection of IDSTUDENT in the table R6 


        result = firstCo.differencTable(fifth);          // R8 = Difference between projection student in R1 and those who are in R7
        return result;
    }


    // Only affichage
    public void viewTable(){

        if(this.getAllData().size() == 0){
            System.out.println("Empty set");
        }else{
            for(int i = 0; i < this.getTitles().size(); i++){
                System.out.print("|-----"+this.getTitles().get(i) + "-----|");
            }
            System.out.println("");
            for (int i = 0; i < this.getAllData().size(); i++) {
                for(int j = 0; j < this.getAllData().get(i).size(); j++){
                    System.out.print("|      "+this.getAllData().get(i).get(j) + "       |");
                }
                System.out.println("");
            }
        }        
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector getTitles() {
        return titles;
    }

    public void setTitles(Vector titles) {
        this.titles = titles;
    }

    public Vector<Vector<Object>> getAllData() {
        return allData;
    }

    public void setAllData(Vector<Vector<Object>> allData) {
        this.allData = allData;
    }
}