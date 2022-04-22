package com.nulp.shymoniak.mastersproject;

import com.nulp.shymoniak.mastersproject.dto.*;
import com.nulp.shymoniak.mastersproject.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public class TestObjectsGenerator {
    // ApplicationUser
    public static ApplicationUser generateApplicationUser() {
        return new ApplicationUser(999L, "Username", "password", null, null, new Person());
    }

    public static ApplicationUserDTO generateApplicationUserDTO() {
        return new ApplicationUserDTO(999L, "Username", "password", null, null, new PersonDTO());
    }

    public static List<ApplicationUser> generateApplicationUserList() {
        return List.of(
                new ApplicationUser(1000L, "Username1", "password", null, null, null),
                new ApplicationUser(1001L, "Username2", "password", null, null, null),
                new ApplicationUser(1002L, "Username3", "password", null, null, null));
    }

    public static List<ApplicationUserDTO> generateApplicationUserDTOList() {
        return List.of(
                new ApplicationUserDTO(1000L, "Username1", "password", null, null, null),
                new ApplicationUserDTO(1001L, "Username2", "password", null, null, null),
                new ApplicationUserDTO(1002L, "Username3", "password", null, null, null));
    }

    // Location
    public static Location generateLocation() {
        return new Location(999L, "39.12345", "39.12345");
    }

    public static LocationDTO generateLocationDTO() {
        return new LocationDTO(999L, "39.12345", "39.12345");
    }

    public static List<LocationDTO> generateLocationDTOList() {
        return List.of(
                new LocationDTO(999L, "39.12345", "39.12345"),
                new LocationDTO(1000L, null, null),
                new LocationDTO(1001L, null, null));
    }

    // Media
    public static Media generateMedia() {
        return new Media(999L, "https://github.com/", "", null, null);
    }

    public static MediaDTO generateMediaDTO() {
        return new MediaDTO(999L, "https://github.com/", "", null, null);
    }

    public static List<MediaDTO> generateMediaDTOList() {
        return List.of(
                new MediaDTO(999L, "https://github.com/", "", null, null),
                new MediaDTO(1000L, null, null, null, null),
                new MediaDTO(1001L, null, null, null, null));
    }

    // MLModel
    public static MLModel generateMLModel() {
        return new MLModel(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
    }

    public static MLModelDTO generateMLModelDTO() {
        return new MLModelDTO(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
    }

    public static List<MLModel> generateMLModelList() {
        List<ObservedObject> observedObjectList = generateObservedObjectList();
        // MLModel instances
        MLModel mlModel1 = new MLModel(1000L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel2 = new MLModel(1001L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel3 = new MLModel(1002L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel4 = new MLModel(1003L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModel mlModel5 = new MLModel(1004L, "MODEL_NAME", "", 1, 1, null, null, null);
        // MLModelObservedObject instances
        // mlModel1
        MLModelObservedObject mlModelObservedObject11 = new MLModelObservedObject(1000L, mlModel1, observedObjectList.get(0));
        MLModelObservedObject mlModelObservedObject12 = new MLModelObservedObject(1000L, mlModel1, observedObjectList.get(1));
        MLModelObservedObject mlModelObservedObject13 = new MLModelObservedObject(1000L, mlModel1, observedObjectList.get(2));
        MLModelObservedObject mlModelObservedObject14 = new MLModelObservedObject(1000L, mlModel1, observedObjectList.get(3));
        mlModel1.setObservedObjectList(List.of(mlModelObservedObject11, mlModelObservedObject12, mlModelObservedObject13, mlModelObservedObject14));
        // mlModel2
        MLModelObservedObject mlModelObservedObject21 = new MLModelObservedObject(1000L, mlModel2, observedObjectList.get(0));
        MLModelObservedObject mlModelObservedObject22 = new MLModelObservedObject(1000L, mlModel2, observedObjectList.get(1));
        MLModelObservedObject mlModelObservedObject23 = new MLModelObservedObject(1000L, mlModel2, observedObjectList.get(2));
        MLModelObservedObject mlModelObservedObject24 = new MLModelObservedObject(1000L, mlModel2, observedObjectList.get(3));
        mlModel2.setObservedObjectList(List.of(mlModelObservedObject21, mlModelObservedObject22, mlModelObservedObject23, mlModelObservedObject24));
        // mlModel3
        MLModelObservedObject mlModelObservedObject31 = new MLModelObservedObject(1000L, mlModel3, observedObjectList.get(1));
        MLModelObservedObject mlModelObservedObject32 = new MLModelObservedObject(1000L, mlModel3, observedObjectList.get(2));
        MLModelObservedObject mlModelObservedObject33 = new MLModelObservedObject(1000L, mlModel3, observedObjectList.get(3));
        MLModelObservedObject mlModelObservedObject34 = new MLModelObservedObject(1000L, mlModel3, observedObjectList.get(4));
        mlModel3.setObservedObjectList(List.of(mlModelObservedObject31, mlModelObservedObject32, mlModelObservedObject33, mlModelObservedObject34));
        // mlModel4
        MLModelObservedObject mlModelObservedObject41 = new MLModelObservedObject(1000L, mlModel4, observedObjectList.get(0));
        MLModelObservedObject mlModelObservedObject42 = new MLModelObservedObject(1000L, mlModel4, observedObjectList.get(1));
        MLModelObservedObject mlModelObservedObject43 = new MLModelObservedObject(1000L, mlModel4, observedObjectList.get(2));
        MLModelObservedObject mlModelObservedObject44 = new MLModelObservedObject(1000L, mlModel4, observedObjectList.get(4));
        mlModel4.setObservedObjectList(List.of(mlModelObservedObject41, mlModelObservedObject42, mlModelObservedObject43, mlModelObservedObject44));
        // mlModel5
        MLModelObservedObject mlModelObservedObject51 = new MLModelObservedObject(1000L, mlModel5, observedObjectList.get(2));
        MLModelObservedObject mlModelObservedObject52 = new MLModelObservedObject(1000L, mlModel5, observedObjectList.get(3));
        MLModelObservedObject mlModelObservedObject53 = new MLModelObservedObject(1000L, mlModel5, observedObjectList.get(4));
        mlModel5.setObservedObjectList(List.of(mlModelObservedObject51, mlModelObservedObject52, mlModelObservedObject53));
        return List.of(mlModel1, mlModel2, mlModel3, mlModel4, mlModel5);
    }

    public static List<MLModelDTO> generateMLModelDTOList() {
        List<ObservedObjectDTO> observedObjectList = generateObservedObjectDTOList();
        // MLModel instances
        MLModelDTO mlModel1 = new MLModelDTO(1000L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel2 = new MLModelDTO(1001L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel3 = new MLModelDTO(1002L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel4 = new MLModelDTO(1003L, "MODEL_NAME", "", 1, 1, null, null, null);
        MLModelDTO mlModel5 = new MLModelDTO(1004L, "MODEL_NAME", "", 1, 1, null, null, null);
        // MLModelObservedObject instances
        // mlModel1
        MLModelObservedObjectDTO mlModelObservedObject11 = new MLModelObservedObjectDTO(1000L, mlModel1, observedObjectList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject12 = new MLModelObservedObjectDTO(1000L, mlModel1, observedObjectList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject13 = new MLModelObservedObjectDTO(1000L, mlModel1, observedObjectList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject14 = new MLModelObservedObjectDTO(1000L, mlModel1, observedObjectList.get(3));
        mlModel1.setObservedObjectList(List.of(mlModelObservedObject11, mlModelObservedObject12, mlModelObservedObject13, mlModelObservedObject14));
        // mlModel2
        MLModelObservedObjectDTO mlModelObservedObject21 = new MLModelObservedObjectDTO(1000L, mlModel2, observedObjectList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject22 = new MLModelObservedObjectDTO(1000L, mlModel2, observedObjectList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject23 = new MLModelObservedObjectDTO(1000L, mlModel2, observedObjectList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject24 = new MLModelObservedObjectDTO(1000L, mlModel2, observedObjectList.get(3));
        mlModel2.setObservedObjectList(List.of(mlModelObservedObject21, mlModelObservedObject22, mlModelObservedObject23, mlModelObservedObject24));
        // mlModel3
        MLModelObservedObjectDTO mlModelObservedObject31 = new MLModelObservedObjectDTO(1000L, mlModel3, observedObjectList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject32 = new MLModelObservedObjectDTO(1000L, mlModel3, observedObjectList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject33 = new MLModelObservedObjectDTO(1000L, mlModel3, observedObjectList.get(3));
        MLModelObservedObjectDTO mlModelObservedObject34 = new MLModelObservedObjectDTO(1000L, mlModel3, observedObjectList.get(4));
        mlModel3.setObservedObjectList(List.of(mlModelObservedObject31, mlModelObservedObject32, mlModelObservedObject33, mlModelObservedObject34));
        // mlModel4
        MLModelObservedObjectDTO mlModelObservedObject41 = new MLModelObservedObjectDTO(1000L, mlModel4, observedObjectList.get(0));
        MLModelObservedObjectDTO mlModelObservedObject42 = new MLModelObservedObjectDTO(1000L, mlModel4, observedObjectList.get(1));
        MLModelObservedObjectDTO mlModelObservedObject43 = new MLModelObservedObjectDTO(1000L, mlModel4, observedObjectList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject44 = new MLModelObservedObjectDTO(1000L, mlModel4, observedObjectList.get(4));
        mlModel4.setObservedObjectList(List.of(mlModelObservedObject41, mlModelObservedObject42, mlModelObservedObject43, mlModelObservedObject44));
        // mlModel5
        MLModelObservedObjectDTO mlModelObservedObject51 = new MLModelObservedObjectDTO(1000L, mlModel5, observedObjectList.get(2));
        MLModelObservedObjectDTO mlModelObservedObject52 = new MLModelObservedObjectDTO(1000L, mlModel5, observedObjectList.get(3));
        MLModelObservedObjectDTO mlModelObservedObject53 = new MLModelObservedObjectDTO(1000L, mlModel5, observedObjectList.get(4));
        mlModel5.setObservedObjectList(List.of(mlModelObservedObject51, mlModelObservedObject52, mlModelObservedObject53));
        return List.of(mlModel1, mlModel2, mlModel3, mlModel4, mlModel5);
    }

    // ObservedObject
    public static ObservedObject generateObservedObject() {
        return new ObservedObject(999L, "OBJ_NAME", null, null);
    }

    public static ObservedObjectDTO generateObservedObjectDTO() {
        return new ObservedObjectDTO(999L, "OBJ_NAME", null, null);
    }

    public static List<ObservedObject> generateObservedObjectList() {
        // ObservedObject instances
        ObservedObject observedObject1 = new ObservedObject(1000L, "OBJ_NAME", null, null);
        ObservedObject observedObject2 = new ObservedObject(1001L, "OBJ_NAME", null, null);
        ObservedObject observedObject3 = new ObservedObject(1002L, "OBJ_NAME", null, null);
        ObservedObject observedObject4 = new ObservedObject(1003L, "OBJ_NAME", null, null);
        ObservedObject observedObject5 = new ObservedObject(1004L, "OBJ_NAME", null, null);
        return List.of(observedObject1, observedObject2, observedObject3, observedObject4, observedObject5);
    }

    public static List<ObservedObjectDTO> generateObservedObjectDTOList() {
        // ObservedObject instances
        ObservedObjectDTO observedObject1 = new ObservedObjectDTO(1000L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject2 = new ObservedObjectDTO(1001L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject3 = new ObservedObjectDTO(1002L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject4 = new ObservedObjectDTO(1003L, "OBJ_NAME", null, null);
        ObservedObjectDTO observedObject5 = new ObservedObjectDTO(1004L, "OBJ_NAME", null, null);
        return List.of(observedObject1, observedObject2, observedObject3, observedObject4, observedObject5);
    }

    // Person
    public static Person generatePerson() {
        return new Person(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com", null);
    }

    public static PersonDTO generatePersonDTO() {
        return new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com", null);
    }

    public static List<PersonDTO> generatePersonDTOList() {
       return List.of(
                new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com", null),
                new PersonDTO(1000L, null, null, null, null),
                new PersonDTO(1001L, null, null, null, null));
    }

    // RecognitionResult
    public static RecognitionResult generateRecognitionResult() {
        return new RecognitionResult(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
    }

    public static RecognitionResultDTO generateRecognitionResultDTO() {
        return new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
    }

    public static List<RecognitionResult> generateRecognitionResultList(LocalDateTime date1, LocalDateTime date2) {
        List<ApplicationUser> users = generateApplicationUserList();
        return List.of(
                new RecognitionResult(1001L, "description ...", 1, 1, date1, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResult(1002L, "description ...", 1, 1, date1, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResult(1003L, "description ...", 1, 1, date1, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResult(1004L, "description ...", 1, 1, date2, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResult(1005L, "description ...", 1, 1, date2, null, null, null, null, users.get(1), users.get(2), null));
    }

    public static List<RecognitionResultDTO> generateRecognitionResultDTOList(LocalDateTime date1, LocalDateTime date2) {
        List<ApplicationUserDTO> users = generateApplicationUserDTOList();
        return List.of(
                new RecognitionResultDTO(1001L, "description ...", 1, 1, date1, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResultDTO(1002L, "description ...", 1, 1, date1, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResultDTO(1003L, "description ...", 1, 1, date1, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResultDTO(1004L, "description ...", 1, 1, date2, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResultDTO(1005L, "description ...", 1, 1, date2, null, null, null, null, users.get(1), users.get(2), null));
    }
}