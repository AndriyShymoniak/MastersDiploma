package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import com.nulp.shymoniak.mastersproject.repository.MediaRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MediaService;
import com.nulp.shymoniak.mastersproject.utility.mapping.MediaMapper;
import com.nulp.shymoniak.mastersproject.validation.MediaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl extends AbstractService<Media, MediaDTO> implements MediaService {

    @Autowired
    public MediaServiceImpl(MediaRepository repository, MediaValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = MediaMapper.INSTANCE;
    }
}
