package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.File;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingFileByUpload extends Outgoing {
    private final File file;
    private final String fileName;
    private final String caption;
}
