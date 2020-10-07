package com.example.storageservice.service;

import com.example.storageservice.config.JmsConfig;
import com.example.storageservice.model.EmployeeDto;
import com.example.storageservice.proto.EmployeeProto;
import com.example.storageservice.security.SecurityService;
import com.example.storageservice.util.CsvUtil;
import com.example.storageservice.util.XmlUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class FileStorageServiceImpl implements FileStorageService{
    private static Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final JmsTemplate jmsTemplate;
    @Value("${filepath}")
    private String homeDirPath;
    private final SecurityService securityService;
    private CsvUtil csvUtil = new CsvUtil();

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void receiveMessage(@Payload byte[] employeeDto, @Headers Map<String, Object> headers
                               ) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, JAXBException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {

        log.info("Received data in bytes format");
        byte[] message1 =securityService.decryptData(employeeDto);
        EmployeeProto.Employee emp1 = EmployeeProto.Employee.parseFrom(message1);
        log.info("Received data in bytes format "+emp1);
        EmployeeDto empDto = EmployeeDto.builder().empId(emp1.getEmpId()).empName(emp1.getEmpName()).age(emp1.getAge()).salary(emp1.getSalary()).build();

        saveFile(empDto,String.valueOf(headers.get("filetype")));

    }

    private void saveFile(EmployeeDto employeeDto, String filetype) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, JAXBException {
        String homeDir = System.getProperty(homeDirPath);
        String full_path = homeDir + "/" + employeeDto.getEmpId().toString()+"."+filetype;
        if("csv".equalsIgnoreCase(filetype)){
                csvUtil.saveFile(employeeDto,full_path);

        }else{
                XmlUtil.employeeToXML(full_path,employeeDto);

        }
    }

    public EmployeeDto getEmployeeByID(UUID empId) throws IOException, JAXBException {
        EmployeeDto employeeDto=null;

        employeeDto= getEmployeeFromFile(empId);
        //securityService.cipherData(employeeDto);

        return employeeDto;
    }

    private EmployeeDto getEmployeeFromFile(UUID empId) throws IOException, JAXBException {
        String homeDir = System.getProperty(homeDirPath);
        String full_path = homeDir + "\\" + empId.toString();
        System.out.println("Inside storage path "+full_path);
        File file = new File(full_path+".xml");
        if(file.exists()){
            System.out.println("xml "+full_path);
            return XmlUtil.getEmployeeData(empId,full_path);
        }
       return csvUtil.getEmployeeData(empId,full_path);
        }
}
