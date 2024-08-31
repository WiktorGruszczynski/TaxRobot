package com.example.taxrobot.app.admin;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = "/fill")
    public String fill(@RequestParam Long id){
        return adminService.fill(id);
    }

    @GetMapping(path = "/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) throws FileNotFoundException {
        return adminService.downloadFile(filename);
    }



}
