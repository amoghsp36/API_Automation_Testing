package org.example;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "testDataForPost")
    public Object[][] providingDataSetsForRequests(){      //returning 2d object(rows and columns)
//        Object[][] data = new Object[2][4];
//
//        data[0][0] = "Akane";
//        data[0][1] = "Kurokawa";
//        data[0][2] = 4;
//        data[0][3] = 4;
//
//        data[1][0] = "Kana";
//        data[1][1] = "Arima";
//        data[1][2] = 5;
//        data[1][3] = 5;
//
//        return data;
        return new Object[][]{
//                {"Ai","Hoshino",1,1},
//                {"Aqua","Hoshino",2,2},
//                {"Akane","Kurokawa",3,3},
//                {"Ruby","Hoshino",4,4},
//                {"Kana","Arima",5,5},
                {"star","idol",9,9},
                {"pqr","xyz",8,8}
        };

    }
    @DataProvider(name = "arrayData")
    public Object[][] subData(){
        return new Object[][]{
                {"abcd","xyz",2,2},
                {"pqr","xyz",3,3}
        };
    }

    @DataProvider(name = "deleteData")
    public Object[] dataToDelete(){
        return new Object[]{
                4,5,6,7
        };
    }

    @DataProvider(name = "dataForPojoClasses")
    public Object[][] pojoData(){
        return new Object[][]{
                {"alex","mercer",23,89374.34},
                {"yuji","nishida",20,8374924.39},
                {"ruka","shirashina",18,8473.84}
        };
    }
}
