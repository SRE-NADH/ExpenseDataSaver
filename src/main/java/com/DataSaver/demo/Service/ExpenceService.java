package com.DataSaver.demo.Service;

import com.DataSaver.demo.Dto.ExpenceResDto;
import com.DataSaver.demo.Model.Data;
import com.DataSaver.demo.Repocitory.DataRepocitory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenceService {
  @Autowired
    DataRepocitory dataRepocitory;

    public ExpenceResDto getClaimDetails(String name) {
        Data data = dataRepocitory.findByName(name);
        return ConvertToExpenceResDto(data);
    }

    public ExpenceResDto pdfExtract(String name,String location) throws IOException {

        PDDocument document = PDDocument.load(new File(location));
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(document);

        // split the string with respect to column heading
        String[] list= pdfText.split("description amount");

        List<List<String>> ExpenceList = new ArrayList<>();
        for(int i=1;i< list.length;i++){
            String[] tmp = list[i].split(" ");
            List<String> li = new ArrayList<>();
            int j=2;
            while(j< tmp.length) {
                li.add(tmp[j]);
                j += 2;
            }
            ExpenceList.add(li);
        }
       Data savedData = findtotalAndSave(name,ExpenceList);

        return ConvertToExpenceResDto(savedData);

    }

    private Data findtotalAndSave(String name,List<List<String>> expenceList) {
        Data data = new Data();
        if(dataRepocitory.findByName(name)!=null) {
            data =dataRepocitory.findByName(name);
        }
        data.setName(name);
        data.setTravelAmount(findTotal(expenceList.get(0)));
        data.setFoodAmount(findTotal(expenceList.get(1)));
        Data savedData = dataRepocitory.save(data);
        return savedData;
    }

    private int findTotal(List<String> strings) {
        int sum =0;
        for(String x: strings){
            int num = Integer.parseInt(x);
            sum+=num;
        }
        return sum;
    }


    private ExpenceResDto ConvertToExpenceResDto(Data savedData) {

        ExpenceResDto expenceResDto = new ExpenceResDto();
        expenceResDto.setName(savedData.getName());
        expenceResDto.setTravel(savedData.getTravelAmount());
        expenceResDto.setFood(savedData.getFoodAmount());
        return expenceResDto;
    }

}
