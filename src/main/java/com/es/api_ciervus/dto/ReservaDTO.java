package com.es.api_ciervus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservaDTO {
    private String estado;
    private Long producto_id;
    private Long usuario_id;
}
