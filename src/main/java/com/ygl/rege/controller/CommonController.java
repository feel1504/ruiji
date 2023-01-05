package com.ygl.rege.controller;

import com.ygl.rege.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${ruiji.path}")
    String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        File dir = new File(basePath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(new File(basePath+uuid+suffix));
        return R.success(uuid+suffix);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        File file = new File(basePath + name);
        byte[] bytes = new byte[1024];
        int len = 0;
        FileInputStream fis = new FileInputStream(file);
        ServletOutputStream os = response.getOutputStream();
        while ((len = fis.read(bytes)) !=-1){
            os.write(bytes,0,len);
            os.flush();
        }
    }
}
