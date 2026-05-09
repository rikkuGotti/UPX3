package br.com.ABICAP.pontorecarga_api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class DTORoleFrontResponse {
    private String username;
    private String email;
    private String role;
    private Integer id;


}