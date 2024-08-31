package com.example.taxrobot.app.gui;

import com.example.taxrobot.tools.DataReader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class GuiService {
    private final String PAGES = "src/main/resources/gui/pages";


    private String getHtmlPage(String uri){
        String page;

        if (Objects.equals(uri, "/")){
            page = "/home";
        }
        else{
            page = uri;
        }

        String path = PAGES + page + "/index.html";

        return DataReader.readFile(path);
    }

    public String getResource(String page, String uri){
        return DataReader.readFile(
                "src/main/resources/gui/pages" + page + uri
        );
    }


    public String getFile(HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (uri.startsWith("/api")){
            return null;
        }

        String referer = request.getHeader("Referer");

        if (referer == null){
            return getHtmlPage(uri);
        }
        else{
            String page = referer.substring(referer.lastIndexOf("/")+1);
            if (page.isEmpty()){
                page = "/home";
            }

            return getResource(page, uri);
        }
    }
}
