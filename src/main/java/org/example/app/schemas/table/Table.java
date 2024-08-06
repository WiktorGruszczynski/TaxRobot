package org.example.app.schemas.table;

import org.example.app.schemas.form.Form;
import org.example.tools.DataReader;


import java.util.Map;

public abstract class Table extends Form{
    final protected Form[] rows;

    public Table(int size){
        rows = new Form[size];
    }

    protected void setRowValue(int index, Form form){
        rows[index] = form;
    }



    protected Form[] getRows(){
        int size = 0;

        for (Form form: rows){
            if (!form.isEmpty()) size+=1;
        }

        Form[] validRows = new Form[size];


        int n=0;
        while (n<size){
            for (Form form: rows){
                if (!form.isEmpty()){
                    validRows[n] = form;
                    n++;
                }
            }

            break;
        }


        return validRows;
    }


    public void loadFromDataMap(int index ,Map<String, String> map){
        rows[index].loadFromMap(map);
    }


    public void loadFromFile(String path){
        String stringData = DataReader.readFile(path);
        String[] stringForms = stringData.split("\n\n");

        for (int i=0; i<stringForms.length; i++){
            loadFromDataMap(
                    i, DataReader.getDataMap(stringForms[i])
            );
        }
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("[");
        int validRows = 0;

        for (int i=0; i<rows.length; i++){
            if (!rows[i].isEmpty()) validRows+=1;
        }

        while (validRows>0){
            for (Form row: rows){
                if (!row.isEmpty()){
                    stringBuilder.append(row);
                    validRows-=1;
                    if (validRows>0) stringBuilder.append(",\n");
                }
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }


    protected abstract void initRows(int size);

    public abstract void fill();
}
