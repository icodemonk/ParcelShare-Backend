package com.parcelshare.parcelshare.Dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    String token;
    String role;
    int userid;
}
