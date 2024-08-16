package com.example.taxrobot.app.gui;

import com.example.taxrobot.tools.DataReader;
import com.example.taxrobot.tools.HtmlLoader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;



@Service
public class GuiService {
    private final String PAGES = "src/main/resources/gui/pages";
    private final String HOME = PAGES + "/home";


    public String getHome() {
        HtmlLoader htmlLoader = new HtmlLoader(HOME+"/index.html");
        return htmlLoader.getContent();
    }


    public String getResource(String filepath, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String page;

        page = referer.substring(referer.lastIndexOf("/")+1);

        if (page.isEmpty()){
            page = "home";
        }

        return DataReader.readFile(PAGES + "/" + page + "/" + filepath);
    }
}
