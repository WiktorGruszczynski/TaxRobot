package com.example.taxrobot.taxmanager.resources.tables;

import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.taxmanager.schemas.Form;
import com.example.taxrobot.taxmanager.schemas.Table;



public class WageStatementTable extends Table {
    public WageStatementTable(){
        super(32);
        initRows(32);
    }


    protected void initRows(int size){
        for (int i=0; i<size; i++){
            setRowValue(i, new WageStatement());
        }
    }


    public void fill(){
        if (getRows().length==0){
            return;
        }

        open();
        move();

        for (Form form: getRows()){
            WageStatement wageStatement = (WageStatement) form;
            wageStatement.fill();
            sleep(50);
        }

        move(4);
        close();

    }
}