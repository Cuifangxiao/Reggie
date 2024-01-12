package com.cfx.reggie.controller;

import com.cfx.reggie.common.R;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/common")
@RestController
public class CommonController {
    @Value(value = "${reggie.path}")
    String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;

        file.transferTo(new File(basePath + fileName));
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath+name);

            ServletOutputStream servletOutputStream = response.getOutputStream();


            response.setContentType("image/jpeg");

            byte[] bytes = new byte[1024];
            int length=0;
            while ( (length = fileInputStream.read(bytes)) != -1 ){
                servletOutputStream.write(bytes,0,length);
                servletOutputStream.flush();
            }

            servletOutputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
