package io.desofme.msfileprocessing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {

    private String uniqueFileId;
    private String name;
    private String extension;
    private String mediaType;
    private byte[] body;

}
