package com.example.taxrobot.app.gui;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class GuiController {
    private final GuiService guiService;

    public GuiController(GuiService guiService) {
        this.guiService = guiService;
    }

    @GetMapping()
    public String getHome(){
        return guiService.getHome();
    }

    @GetMapping(path = "/resource/**")
    public String getResource(HttpServletRequest request){
        String fullpath = request.getRequestURI();
        String filepath = fullpath.replace("/resource","");
        return guiService.getResource(filepath, request);
    }
}
