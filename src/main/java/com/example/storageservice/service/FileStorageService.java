package com.example.storageservice.service;

import com.example.storageservice.model.EmployeeDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.UUID;

public interface FileStorageService {
    abstract void receiveMessage(@Payload byte[] employeeDto, @Headers Map<String, Object> headers) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, JAXBException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException;
    abstract EmployeeDto getEmployeeByID(UUID empId) throws IOException, JAXBException;
}
