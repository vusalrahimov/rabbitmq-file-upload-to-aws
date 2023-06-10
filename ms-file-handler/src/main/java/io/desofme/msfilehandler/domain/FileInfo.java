package io.desofme.msfilehandler.domain;

import lombok.Data;

@Data
public class FileInfo {

    private String uniqueFileId;
    private String name;
    private String extension;
    private String mediaType;
    private byte[] body;

}