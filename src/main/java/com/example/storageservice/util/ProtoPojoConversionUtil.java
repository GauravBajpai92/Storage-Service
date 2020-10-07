package com.example.storageservice.util;


import com.example.storageservice.model.EmployeeDto;
import com.example.storageservice.proto.EmployeeProto;

import java.util.UUID;

public class ProtoPojoConversionUtil {
    public static EmployeeDto toPojo(EmployeeProto.Employee empDto) {
        return EmployeeDto.builder().empId(empDto.getEmpId()).empName(empDto.getEmpName()).age(empDto.getAge()).salary(empDto.getSalary()).build();
    }

    public static EmployeeProto.Employee toProto(EmployeeDto empDto) {
        return EmployeeProto.Employee.newBuilder().setEmpId(UUID.randomUUID()
                .toString()).setEmpName(empDto.getEmpName()).setAge(empDto.getAge())
                .setSalary(empDto.getSalary()).build();
    }
    public static EmployeeProto.Employee toProto(EmployeeDto empDto,String empId) {
        return EmployeeProto.Employee.newBuilder().setEmpId(empId).setEmpName(empDto.getEmpName()).setAge(empDto.getAge())
                .setSalary(empDto.getSalary()).build();
    }
}
