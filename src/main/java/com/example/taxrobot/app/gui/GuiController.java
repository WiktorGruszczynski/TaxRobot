package com.example.taxrobot.app.gui;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*")
public class GuiController {
    private final GuiService guiService;

    public GuiController(GuiService guiService) {
        this.guiService = guiService;
    }


    @GetMapping(path = "/**")
    public String getFile(HttpServletRequest request){
        return guiService.getFile(request);
    }

}
