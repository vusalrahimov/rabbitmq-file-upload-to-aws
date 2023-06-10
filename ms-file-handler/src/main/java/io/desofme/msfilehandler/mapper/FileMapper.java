package io.desofme.msfilehandler.mapper;

import io.desofme.msfilehandler.domain.FileInfo;
import io.desofme.msfilehandler.entity.FileData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FileMapper {

    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    FileData toFileData(FileInfo fileInfo);

    FileData updateFileData(@MappingTarget FileData fileData, FileData data);

}
