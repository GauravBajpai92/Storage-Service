package com.example.storageservice.controller;

import com.example.storageservice.model.EmployeeDto;
import com.example.storageservice.service.FileStorageService;
import com.example.storageservice.service.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/${application.url}")
public class FileStorageController {
    private  final FileStorageService fileStorageService;
    @GetMapping("/{empId}")
    public ResponseEntity<byte[]> getEmployee(@PathVariable("empId") UUID empId) throws IOException, JAXBException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        return new ResponseEntity<byte[]>(fileStorageService.getEmployeeByID(empId), HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> validationErrorHandler(Exception e){

        String errors ="Exception while trying to retrieve the File. PLEASE TRY LATER ";
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
