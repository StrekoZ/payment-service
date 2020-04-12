package lv.dp.education.ps.common.api.rest;

import lombok.Data;

import java.util.List;

@Data
public class ErrorRestModel {
    private final String errorTitle;
    private final List<String> errorDetails;
}
