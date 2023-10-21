package com.DataSaver.demo.Controller;

import com.DataSaver.demo.Dto.ExpenceResDto;
import com.DataSaver.demo.Service.ExpenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/expence-data")
public class ExpenseDataController {

    @Autowired
    ExpenceService expenseService;
    @PostMapping("/postfile")
    public ResponseEntity PostFile(@RequestParam String name,  @RequestParam("file") MultipartFile file) throws IOException {
        try {
            File tempFile = File.createTempFile("temp-pdf-", ".pdf");
            file.transferTo(tempFile);

            ExpenceResDto expenceResDto = expenseService.pdfExtract(name,tempFile.getAbsolutePath());
            return new ResponseEntity<>(expenceResDto, HttpStatus.CREATED);
        }
        catch (IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getClaimDetails")
    public ResponseEntity getClaimDetails(@RequestParam String name){
        try{
            ExpenceResDto expenceResDto = expenseService.getClaimDetails(name);
            return new ResponseEntity<>(expenceResDto,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
