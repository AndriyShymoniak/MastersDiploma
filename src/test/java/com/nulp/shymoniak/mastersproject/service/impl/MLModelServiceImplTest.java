package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.MLModelObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.entity.MLModelObservedObject;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.MLModelMapper;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MLModelServiceImplTest {
    @Mock
    private MLModelRepository repository;

    @Mock
    private MLModelMapper mapper;

    @InjectMocks
    private MLModelServiceImpl service;

    private MLModel mlModel;
    private MLModelDTO mlModelDTO;
    private List<ObservedObject> observedObjectList;
    private List<ObservedObjectDTO> observedObjectDTOList;
    private List<MLModel> mlModelList;
    private List<MLModelDTO> mlModelDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mlModel = new MLModel(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
        mlModelDTO = new MLModelDTO(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
        observedObjectList = generateTestValuesForObservedObject();
        observedObjectDTOList = generateTestValuesForObservedObjectDTO();
        mlModelList = generateTestValuesForMLModelList(observedObjectList);
        mlModelDTOList = generateTestValuesForMLModelDTOList(observedObjectDTOList);
    }

    @Test
    void findById_shouldReturnMLModel_IfFinds() {
        // given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.of(mlModel));
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        // when
        MLModelDTO result = service.findById(mlModel.getMlModelId());
        // then
        verify(repository).findById(mlModel.getMlModelId());
        verify(mapper).mapToDTO(mlModel);
        assertEquals(mlModelDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfNotFinds() {
        // given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(mlModel.getMlModelId()));
        verify(repository).findById(mlModel.getMlModelId());
    }

    @Test
    void createItem_shouldSaveMLModel() {
        // given
        when(mapper.mapToEntity(mlModelDTO)).thenReturn(mlModel);
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        // when
        MLModelDTO result = service.createItem(mlModelDTO);
        // then
        verify(mapper).mapToEntity(mlModelDTO);
        verify(mapper).mapToDTO(mlModel);
        verify(repository).save(mlModel);
        assertEquals(mlModelDTO, result);
    }

    @Test
    void updateItem_shouldUpdateMLModel_ifExist() {
        // given
        MLModel newMLModelEntity = new MLModel(999L, "NEW_MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
        MLModelDTO newMLModelDTO = new MLModelDTO(999L, "NEW_MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
        when(repository.existsById(mlModel.getMlModelId())).thenReturn(true);
        when(mapper.mapToEntity(newMLModelDTO)).thenReturn(newMLModelEntity);
        when(mapper.mapToDTO(newMLModelEntity)).thenReturn(newMLModelDTO);
        // when
        MLModelDTO result = service.updateItem(newMLModelDTO);
        // then
        verify(mapper).mapToEntity(newMLModelDTO);
        verify(repository).existsById(mlModel.getMlModelId());
        verify(repository).save(newMLModelEntity);
        assertEquals(newMLModelDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(mlModelDTO)).thenReturn(mlModel);
        when(repository.existsById(mlModel.getMlModelId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(mlModelDTO));
        verify(mapper).mapToEntity(mlModelDTO);
        verify(repository).existsById(mlModel.getMlModelId());
    }

    @Test
    void deleteItem_shouldDeleteMLModel_IfFinds() {
        // given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.of(mlModel));
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        // when
        MLModelDTO result = service.deleteItem(mlModel.getMlModelId());
        // then
        verify(repository).findById(mlModel.getMlModelId());
        verify(repository).deleteById(mlModel.getMlModelId());
        assertEquals(mlModelDTO, result);
    }

    @Test
    void findAllModelsByObservedObject_shouldReturnMLModels_ifTheyContainAllObservedObjects() {
        // given
        Set<Long> observedObjectIdSet = Stream.of(
                observedObjectDTOList.get(0).getObservedObjectId(),
                observedObjectDTOList.get(1).getObservedObjectId()
        ).collect(Collectors.toSet());
        when(repository.findAll()).thenReturn(mlModelList);
        when(mapper.mapToDTO(Stream.of(mlModelList.get(0), mlModelList.get(1), mlModelList.get(3)).collect(Collectors.toList())))
                .thenReturn(Stream.of(mlModelDTOList.get(0), mlModelDTOList.get(1), mlModelDTOList.get(3)).collect(Collectors.toList()));
        // when
        List<MLModelDTO> result = service.findAllModelsByObservedObject(observedObjectIdSet);
        // then
        verify(repository).findAll();
        assertTrue(result.contains(mlModelDTOList.get(0)));
        assertTrue(result.contains(mlModelDTOList.get(1)));
        assertTrue(result.contains(mlModelDTOList.get(3)));
        assertFalse(result.contains(mlModelDTOList.get(2)));
        assertFalse(result.contains(mlModelDTOList.get(4)));
    }

    private List<ObservedObjectDTO> generateTestValuesForObservedObjectDTO() {
        // ObservedObject instances
        ObservedObjectDTO observedObject1 = new ObservedObjectDTO(1000L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject2 = new ObservedObjectDTO(1001L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject3 = new ObservedObjectDTO(1002L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject4 = new ObservedObjectDTO(1003L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject5 = new ObservedObjectDTO(1004L, "OBJ_NAME", null, null);
        return Stream.of(observedObject1, observedObject2, observedObject3, observedObject4, observedObject5)
                .collect(Collectors.toList());
    }

    private List<ObservedObject> generateTestValuesForObservedObject() {
        // ObservedObject instances
        ObservedObject observedObject1 = new ObservedObject(1000L, "OBJ_NAME", null, null);
        ObservedObject observedObject2 = new ObservedObject(1001L, "OBJ_NAME", null, null);
        ObservedObject observedObject3 = new ObservedObject(1002L, "OBJ_NAME", null, null);
        ObservedObject observedObject4 = new ObservedObject(1003L, "OBJ_NAME", null, null);
        ObservedObject observedObject5 = new ObservedObject(1004L, "OBJ_NAME", null, null);
        return Stream.of(observedObject1, observedObject2, observedObject3, observedObject4, observedObject5)
                .collect(Collectors.toList());
    }
    private List<MLModel> generateTestValuesForMLModelList(List<ObservedObject> observedObjectList) {
        // MLModel instances
        MLModel mlModel1 = new MLModel(1000L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel2 = new MLModel(1001L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel3 = new MLModel(1002L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel4 = new MLModel(1003L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel5 = new MLModel(1004L, "MODEL_NAME", "", 1, 1, null, null, null);
        // ObservedObject instances
        List<ObservedObject> ooList = observedObjectList;
        // MLModelObservedObject instances
        // mlModel1
        MLModelObservedObject mlModelObservedObject11 = new MLModelObservedObject(1000L, mlModel1, ooList.get(0));
        MLModelObservedObject mlModelObservedObject12 = new MLModelObservedObject(1000L, mlModel1, ooList.get(1));
        MLModelObservedObject mlModelObservedObject13 = new MLModelObservedObject(1000L, mlModel1, ooList.get(2));
        MLModelObservedObject mlModelObservedObject14 = new MLModelObservedObject(1000L, mlModel1, ooList.get(3));
        mlModel1.setObservedObjectList(Stream.of(mlModelObservedObject11, mlModelObservedObject12, mlModelObservedObject13, mlModelObservedObject14).collect(Collectors.toList()));
        // mlModel2
        MLModelObservedObject mlModelObservedObject21 = new MLModelObservedObject(1000L, mlModel2, ooList.get(0));
        MLModelObservedObject mlModelObservedObject22 = new MLModelObservedObject(1000L, mlModel2, ooList.get(1));
        MLModelObservedObject mlModelObservedObject23 = new MLModelObservedObject(1000L, mlModel2, ooList.get(2));
        MLModelObservedObject mlModelObservedObject24 = new MLModelObservedObject(1000L, mlModel2, ooList.get(3));
        mlModel2.setObservedObjectList(Stream.of(mlModelObservedObject21, mlModelObservedObject22, mlModelObservedObject23, mlModelObservedObject24).collect(Collectors.toList()));
        // mlModel3
        MLModelObservedObject mlModelObservedObject31 = new MLModelObservedObject(1000L, mlModel3, ooList.get(1));
        MLModelObservedObject mlModelObservedObject32 = new MLModelObservedObject(1000L, mlModel3, ooList.get(2));
        MLModelObservedObject mlModelObservedObject33 = new MLModelObservedObject(1000L, mlModel3, ooList.get(3));
        MLModelObservedObject mlModelObservedObject34 = new MLModelObservedObject(1000L, mlModel3, ooList.get(4));
        mlModel3.setObservedObjectList(Stream.of(mlModelObservedObject31, mlModelObservedObject32, mlModelObservedObject33, mlModelObservedObject34).collect(Collectors.toList()));
        // mlModel4
        MLModelObservedObject mlModelObservedObject41 = new MLModelObservedObject(1000L, mlModel4, ooList.get(0));
        MLModelObservedObject mlModelObservedObject42 = new MLModelObservedObject(1000L, mlModel4, ooList.get(1));
        MLModelObservedObject mlModelObservedObject43 = new MLModelObservedObject(1000L, mlModel4, ooList.get(2));
        MLModelObservedObject mlModelObservedObject44 = new MLModelObservedObject(1000L, mlModel4, ooList.get(4));
        mlModel4.setObservedObjectList(Stream.of(mlModelObservedObject41, mlModelObservedObject42, mlModelObservedObject43, mlModelObservedObject44).collect(Collectors.toList()));
        // mlModel5
        MLModelObservedObject mlModelObservedObject51 = new MLModelObservedObject(1000L, mlModel5, ooList.get(2));
        MLModelObservedObject mlModelObservedObject52 = new MLModelObservedObject(1000L, mlModel5, ooList.get(3));
        MLModelObservedObject mlModelObservedObject53 = new MLModelObservedObject(1000L, mlModel5, ooList.get(4));
        mlModel5.setObservedObjectList(Stream.of(mlModelObservedObject51, mlModelObservedObject52, mlModelObservedObject53).collect(Collectors.toList()));
        return Stream.of(mlModel1, mlModel2, mlModel3, mlModel4, mlModel5)
                .collect(Collectors.toList());
    }

    private List<MLModelDTO> generateTestValuesForMLModelDTOList(List<ObservedObjectDTO> observedObjectList) {
        // MLModel instances
        MLModelDTO mlModel1 = new MLModelDTO(1000L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel2 = new MLModelDTO(1001L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel3 = new MLModelDTO(1002L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel4 = new MLModelDTO(1003L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel5 = new MLModelDTO(1004L, "MODEL_NAME", "", 1, 1, null, null, null);
        // ObservedObject instances
        List<ObservedObjectDTO> ooList = observedObjectList;
        // MLModelObservedObject instances
        // mlModel1
        MLModelObservedObjectDTO mlModelObservedObject11 = new MLModelObservedObjectDTO(1000L, mlModel1, ooList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject12 = new MLModelObservedObjectDTO(1000L, mlModel1, ooList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject13 = new MLModelObservedObjectDTO(1000L, mlModel1, ooList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject14 = new MLModelObservedObjectDTO(1000L, mlModel1, ooList.get(3));
        mlModel1.setObservedObjectList(Stream.of(mlModelObservedObject11, mlModelObservedObject12, mlModelObservedObject13, mlModelObservedObject14).collect(Collectors.toList()));
        // mlModel2
        MLModelObservedObjectDTO mlModelObservedObject21 = new MLModelObservedObjectDTO(1000L, mlModel2, ooList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject22 = new MLModelObservedObjectDTO(1000L, mlModel2, ooList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject23 = new MLModelObservedObjectDTO(1000L, mlModel2, ooList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject24 = new MLModelObservedObjectDTO(1000L, mlModel2, ooList.get(3));
        mlModel2.setObservedObjectList(Stream.of(mlModelObservedObject21, mlModelObservedObject22, mlModelObservedObject23, mlModelObservedObject24).collect(Collectors.toList()));
        // mlModel3
        MLModelObservedObjectDTO mlModelObservedObject31 = new MLModelObservedObjectDTO(1000L, mlModel3, ooList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject32 = new MLModelObservedObjectDTO(1000L, mlModel3, ooList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject33 = new MLModelObservedObjectDTO(1000L, mlModel3, ooList.get(3));
        MLModelObservedObjectDTO mlModelObservedObject34 = new MLModelObservedObjectDTO(1000L, mlModel3, ooList.get(4));
        mlModel3.setObservedObjectList(Stream.of(mlModelObservedObject31, mlModelObservedObject32, mlModelObservedObject33, mlModelObservedObject34).collect(Collectors.toList()));
        // mlModel4
        MLModelObservedObjectDTO mlModelObservedObject41 = new MLModelObservedObjectDTO(1000L, mlModel4, ooList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject42 = new MLModelObservedObjectDTO(1000L, mlModel4, ooList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject43 = new MLModelObservedObjectDTO(1000L, mlModel4, ooList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject44 = new MLModelObservedObjectDTO(1000L, mlModel4, ooList.get(4));
        mlModel4.setObservedObjectList(Stream.of(mlModelObservedObject41, mlModelObservedObject42, mlModelObservedObject43, mlModelObservedObject44).collect(Collectors.toList()));
        // mlModel5
        MLModelObservedObjectDTO mlModelObservedObject51 = new MLModelObservedObjectDTO(1000L, mlModel5, ooList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject52 = new MLModelObservedObjectDTO(1000L, mlModel5, ooList.get(3));
        MLModelObservedObjectDTO mlModelObservedObject53 = new MLModelObservedObjectDTO(1000L, mlModel5, ooList.get(4));
        mlModel5.setObservedObjectList(Stream.of(mlModelObservedObject51, mlModelObservedObject52, mlModelObservedObject53).collect(Collectors.toList()));
        return Stream.of(mlModel1, mlModel2, mlModel3, mlModel4, mlModel5)
                .collect(Collectors.toList());
    }
}