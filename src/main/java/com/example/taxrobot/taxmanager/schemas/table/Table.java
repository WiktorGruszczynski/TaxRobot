package com.example.taxrobot.taxmanager.schemas.table;

import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.tools.DataReader;


import java.util.List;
import java.util.Map;

public abstract class Table extends Form {
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
    public void loadFromEntity(Object entity){
        if (entity instanceof List<?> entityList){
            for (Object object : entityList) {
                super.loadFromEntity(object);
            }
        }
        else {
            super.loadFromEntity(entity);
        }
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("[");
        Form[] validRows = getRows();

        for (int i=0; i<validRows.length; i++){
            stringBuilder.append(validRows[i]);

            if (i<validRows.length-1){
                stringBuilder.append(",\n");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }


    protected abstract void initRows(int size);

    public abstract void fill();
}
