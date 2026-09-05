package com.app.sberjv4_reliabilty2.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    int status;

    @JsonProperty("status_message")
    String statusMessage;

    String message;
    String uri;
}
