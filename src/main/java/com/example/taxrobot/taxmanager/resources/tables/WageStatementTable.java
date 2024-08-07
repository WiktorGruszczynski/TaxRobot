package com.example.taxrobot.taxmanager.resources.tables;



import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.taxmanager.schemas.table.Table;



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
        open();
        move();

        for (Form form:  getRows()){
            WageStatement wageStatement = (WageStatement) form;
            wageStatement.fill();
            sleep(10);
        }

        move(4);
        close();



    }

}
