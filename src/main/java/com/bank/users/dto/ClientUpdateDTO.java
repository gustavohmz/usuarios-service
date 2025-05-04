package com.bank.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateDTO {

    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private String clientId;
    private String password;
    private Boolean status;
}
