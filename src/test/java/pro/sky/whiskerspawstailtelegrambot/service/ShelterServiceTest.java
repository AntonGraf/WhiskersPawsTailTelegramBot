package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ShelterMapper;
import pro.sky.whiskerspawstailtelegrambot.record.ShelterRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {
   Long numberShelter = 1L;
    List<String> exampleList = new ArrayList<>(List.of("1","2","3"));
    @InjectMocks
    private ShelterService shelterService;

    @Mock
    private ShelterRepo shelterRepo;
    Shelter shelter = new Shelter(1L,"ttt","uuu","jjj","iii","ppp","ttt",
            "fff",exampleList,"ggg","jjj","nnn",
            "ccc","kkk",exampleList,
            exampleList,null,null,new  byte[]{1,2,3});
//    Shelter shelter1 = new Shelter(1L,"ttt","uuu","jjj","iii","ppp","ttt",
//            null,exampleList,"ggg","jjj","nnn",
//            "ccc","kkk",exampleList,
//            exampleList,null,null,new Byte[]{1,2,3});

    @Test
    void getOfShelterMessagePositiveTest(){
       // System.out.println(shelterRepo.findById(numberShelter).orElseThrow(ElemNotFound::new).getRuleOfMeeting());
        when(shelterRepo.findById(numberShelter)).thenReturn(Optional.ofNullable(shelter));
        assertThat(shelterService.getOfShelterMessage((byte) 3)).isEqualTo("fff");
        verify(shelterRepo, times(1)).findById(numberShelter);

    }
    @Test
    void getOfShelterMessageNegativeTest(){
       // when(shelterRepo.findById(numberShelter)).thenReturn(Optional.ofNullable(shelter1));
        assertThatExceptionOfType(ElemNotFound.class).isThrownBy(() -> shelterService.getOfShelterMessage((byte) 3));
    }


}
