package com.app.sberjv4_reliabilty2.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadTimeoutException {
    private int status;
    private String message;
}
