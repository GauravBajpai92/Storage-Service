package com.example.storageservice.util;

import com.example.storageservice.model.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.UUID;

public class XmlUtil {
    private static Logger log = LoggerFactory.getLogger(XmlUtil.class);
    public static void employeeToXML(String filename, EmployeeDto employeeDto) throws JAXBException {
        File file = new File(filename);
        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeDto.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(employeeDto, file);
        log.info("Writing the employee Object "+employeeDto +" on path" +filename);
    }

    public static EmployeeDto getEmployeeData(UUID empId, String full_path) throws JAXBException {
        String filename=full_path+".xml";

        File file = new File(filename);
        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeDto.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (EmployeeDto) jaxbUnmarshaller.unmarshal(file);
    }
}
