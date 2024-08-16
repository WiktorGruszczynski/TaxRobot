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

    @GetMapping(path = "/resource/{filepath}")
    public String getResource(@PathVariable("filepath") String filepath, HttpServletRequest request){
        return guiService.getResource(filepath, request);
    }
}
