package org.example.app.tables;







import org.example.app.forms.WageStatement;
import org.example.app.schemas.form.Form;
import org.example.app.schemas.table.Table;


public class WageStatementTable extends Table{
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
        }

        move(4);
        close();

    }

}
