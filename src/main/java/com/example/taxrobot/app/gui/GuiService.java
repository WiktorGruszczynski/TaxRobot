package com.example.taxrobot.app.gui;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.wageStatement.WageStatementDao;
import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.tools.DataReader;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service
public class GuiService {
    private final PersonalDataDao personalDataDao;
    private final WageStatementDao wageStatementDao;
    private final String HOME_PATH = "src/main/resources/gui/pages/home";
    private final String PERSONAL_DATA_ROWS = "{PERSONAL_DATA_ROWS}";

    public GuiService(PersonalDataDao personalDataDao, WageStatementDao wageStatementDao) {
        this.personalDataDao = personalDataDao;
        this.wageStatementDao = wageStatementDao;
    }

    private String getPersonalDataHtmlRows(){
        StringBuilder rows = new StringBuilder();

        List<PersonalData> personalDataList = personalDataDao.findAll();

        personalDataList.forEach(personalData -> {
            StringBuilder row = new StringBuilder("<li class=\"row\">");

            for (Field field: personalData.getClass().getDeclaredFields()){
                StringBuilder cell = new StringBuilder("<div class=\"cell\">");

                try {
                    field.setAccessible(true);

                    if (field.getName().equals("geburtsdatum")){
                         cell.append(
                                Objects.toString(
                                        field.get(personalData)
                                ).split(" ")[0]
                        );
                    }
                    else{
                        cell.append(Objects.toString(field.get(personalData), null));
                    }


                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                cell.append("</div>");
                row.append(cell);
            }

            row.append("</li>");
            rows.append(row);
        });

        return rows.toString();
    }



    public String getHome() {
        String html = DataReader.readFile( HOME_PATH + "/index.html");
        String css = DataReader.readFile(HOME_PATH +  "/style.css");

        return html
                .replace("<style></style>", "<style>"+css+"</style>")
                .replace(PERSONAL_DATA_ROWS, getPersonalDataHtmlRows());
    }
}
