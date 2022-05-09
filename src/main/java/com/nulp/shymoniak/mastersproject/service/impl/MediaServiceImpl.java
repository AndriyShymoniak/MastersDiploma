package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import com.nulp.shymoniak.mastersproject.repository.MediaRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MediaService;
import com.nulp.shymoniak.mastersproject.mapping.MediaMapper;
import com.nulp.shymoniak.mastersproject.validation.MediaValidator;

@CrudService(
        validator = MediaValidator.class,
        repository = MediaRepository.class,
        mapper = MediaMapper.class
)
public class MediaServiceImpl extends AbstractService<Media, MediaDTO> implements MediaService {
}
