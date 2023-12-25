package ru.pavel.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class StorageService {
    @Value("${dir-file.file}")
    private String dirFile;
    @Value("${dir-file.text}")
    private String dirText;
    private String pathFile;

    @PostConstruct
    public void init() {
        File fileFile = new File(dirFile);
        File fileText = new File(dirText);
        if (!fileText.exists()) fileText.mkdir();
        if (!fileFile.exists()) fileFile.mkdir();
        pathFile = fileFile.getAbsolutePath();
    }

    public RedirectView saveText(String json) {
        System.out.println(json);
        String[] split = json.split("&", 2);
        System.out.println(Arrays.toString(split));
        String title = split[0].substring(split[0].indexOf("=") + 1);
        String text = split[1].substring(split[1].indexOf("=") + 1);
        System.out.println(title + "---" + text);
        try {
            File file = new File(dirText, title + ".txt");
            ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
            Files.copy(in, Path.of(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file loading: " + title);
        } catch (IOException e) {
            System.out.println("error save");
        }
        return new RedirectView("/");
    }

    public RedirectView saveFile(MultipartFile[] file) {
        try {
            for (MultipartFile file1 : file) {
                if (file1.isEmpty() || file1.getOriginalFilename() == null) {
                    return new RedirectView("/");
                }
                parseMultiFile(file1);
                System.out.println("file loading: " + file1.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/");
    }

    private void parseMultiFile(MultipartFile file) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(file.getInputStream())) {
            File dirSave = new File(dirFile, file.getOriginalFilename());
            Files.copy(in, Path.of(dirSave.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file loading: " + file.getOriginalFilename());
        } catch (IOException e) {
            System.out.println("error save file");
        }
    }
}
