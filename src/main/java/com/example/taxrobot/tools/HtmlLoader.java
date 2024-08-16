package com.example.taxrobot.tools;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlLoader {
    private String html;
    private final String RESOURCE_URL = "http://localhost:8080/resource/";

    public HtmlLoader(String path) {
        this.html = DataReader.readFile(path);
        init();
    }

    public String getContent() {
        return html;
    }


    private void modifyLinks(){
        String regex = "<link\\s+rel=\"stylesheet\"\\s+href=\"([^\"]+)\">";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(html);

        String baseUrl = "http://localhost:8080/resource/";

        StringBuilder modifiedString = new StringBuilder();

        while (matcher.find()) {
            String originalHref = matcher.group(1);

            String newHref = baseUrl + originalHref;

            matcher.appendReplacement(modifiedString, "<link rel=\"stylesheet\" href=\"" + newHref + "\">");
        }

        matcher.appendTail(modifiedString);

        html = modifiedString.toString();
    }

    private void modifyScripts(){
        String regex = "<script\\s+src=\"([^\"]+)\"></script>";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(html);


        StringBuilder modifiedString = new StringBuilder();

        while (matcher.find()) {
            String originalSrc = matcher.group(1);

            String newSrc = RESOURCE_URL + originalSrc;

            matcher.appendReplacement(modifiedString, "<script src=\"" + newSrc + "\"></script>");
        }

        matcher.appendTail(modifiedString);

        html = modifiedString.toString();
    }


    private void init() {
        modifyLinks();
        modifyScripts();
    }
}
