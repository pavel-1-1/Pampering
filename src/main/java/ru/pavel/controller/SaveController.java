package ru.pavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.pavel.service.StorageService;

@RestController
@RequestMapping("/")
public class SaveController {
    private final StorageService storageService;

    @Autowired
    public SaveController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("text")
    public RedirectView saveText(@RequestBody String json) {
        return storageService.saveText(json);
    }

    @PostMapping("file")
    public RedirectView saveFile(@RequestBody MultipartFile[] file) {
        return storageService.saveFile(file);
    }
}
