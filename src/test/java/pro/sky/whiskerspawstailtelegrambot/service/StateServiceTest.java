package pro.sky.whiskerspawstailtelegrambot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent.FREE;
import static pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent.NULL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.StateRepository;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {

  @Spy
  AdoptiveParentMapper adoptiveParentMapper = new AdoptiveParentMapperImpl();
  @Mock
  StateRepository stateChangeRepos;

  @InjectMocks
  StateService stateService;
  List<PetRecord> petRecords;
  AdoptiveParent adoptiveParent;
  List<Pet> pets;
  AdoptiveParentRecord adoptiveParentRecord;


  @BeforeEach
  void setUp() throws IOException {
    petRecords = new ArrayList<>();
    pets = new ArrayList<>();
    adoptiveParentRecord = new AdoptiveParentRecord(1l, "fullName", "phone", true, "FREE",
        0l, petRecords);
    adoptiveParent = new AdoptiveParent(1l, "fullName", "phone", true, "FREE", 0l, pets);

  }

  @Test
  void getAdoptiveParentByChatId() throws ElemNotFoundChecked {
    when(stateChangeRepos.getAdoptiveParentByChatId(anyLong())).thenReturn(
        Optional.ofNullable(adoptiveParent));

    assertThat(stateService.getAdoptiveParentByChatId(1l)).isEqualTo(adoptiveParentRecord);
    verify(stateChangeRepos, times(1)).getAdoptiveParentByChatId(any());
  }

  @Test
  void getAdoptiveParentByChatIdNegative() {
    assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
        () -> stateService.getAdoptiveParentByChatId(anyLong()));
    verify(stateChangeRepos, times(1)).getAdoptiveParentByChatId(any());
  }

  @Test
  void getStateAdoptiveParentByChatId() throws ElemNotFoundChecked {
    when(stateChangeRepos.getAdoptiveParentByChatId(anyLong())).thenReturn(
        Optional.ofNullable(adoptiveParent));
    when(stateService.getAdoptiveParentByChatId(anyLong())).thenReturn((adoptiveParentRecord));

    assertThat(stateService.getStateAdoptiveParentByChatId(1L)).isEqualTo(FREE);
    verify(stateChangeRepos, times(2)).getAdoptiveParentByChatId(any());
  }

  @Test
  void getStateAdoptiveParentByChatIdNegative() {
    assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
        () -> stateService.getAdoptiveParentByChatId(anyLong()));
    assertThat(stateService.getStateAdoptiveParentByChatId(anyLong())).isEqualTo(NULL);
  }

  @Test
  void updateStateAdoptiveParentByChatId() {
    when(stateChangeRepos.getAdoptiveParentByChatId(anyLong())).thenReturn(
        Optional.ofNullable(adoptiveParent));

    assertThat(stateService.updateStateAdoptiveParentByChatId(anyLong(), NULL)).isEqualTo(NULL);
    verify(stateChangeRepos, times(1)).getAdoptiveParentByChatId(any());
  }

  @Test
  void updateStateAdoptiveParentByChatIdNegative() {
    assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
        () -> stateService.getAdoptiveParentByChatId(anyLong()));
    assertThat(stateService.updateStateAdoptiveParentByChatId(anyLong(), NULL));
  }
}