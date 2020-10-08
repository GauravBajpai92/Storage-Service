package com.example.storageservice.util;

import com.example.storageservice.model.EmployeeDto;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CsvUtil {
    private static Logger log = LoggerFactory.getLogger(CsvUtil.class);

    public void saveFile(EmployeeDto employeeDto, String full_path) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        FileWriter writer = new FileWriter(full_path);
        ColumnPositionMappingStrategy mappingStrategy=
                new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(EmployeeDto.class);
        String[] columns = new String[]
                { "empId","empName", "salary","age" };
        mappingStrategy.setColumnMapping(columns);
        StatefulBeanToCsvBuilder<EmployeeDto> builder=
                new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv beanWriter =
                builder.withMappingStrategy(mappingStrategy).build();
        beanWriter.write(employeeDto);
        writer.close();
        log.info("Writing the employee Object "+employeeDto +" on path" +full_path);


    }

    public EmployeeDto getEmployeeData(UUID empId, String full_path) throws IOException {
        full_path= full_path+".csv";
        FileReader filereader = new FileReader(full_path);
        log.info("Getting the employee "+empId +" Object from path" +full_path);
        CsvToBean<EmployeeDto> csvToBean = new CsvToBeanBuilder(filereader)
                .withType(EmployeeDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .build();
        List<EmployeeDto> employeeDtoList = csvToBean.parse();
        return employeeDtoList.get(0);
    }
}
