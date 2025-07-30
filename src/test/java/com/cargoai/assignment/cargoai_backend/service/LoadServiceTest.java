// package com.cargoai.assignment.cargoai_backend.service;

// import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
// import com.cargoai.assignment.cargoai_backend.entity.Load;
// import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Optional;
// import java.util.UUID;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// public class LoadServiceTest {
//     @Mock
//     private LoadRepository loadRepository;

//     @InjectMocks
//     private LoadService loadService;

//     @Test
//     void testCreateLoad() {
//         Load load = new Load();
//         when(loadRepository.save(any(Load.class))).thenReturn(load);

//         LoadDTO loadDTO = new LoadDTO();
//         LoadDTO result = loadService.createLoad(loadDTO);
//         assertNotNull(result);
//     }

//     @Test
//     void testGetLoad() {
//         Load load = new Load();
//         when(loadRepository.findById(any(UUID.class))).thenReturn(Optional.of(load));

//         LoadDTO result = loadService.getLoad(UUID.randomUUID());
//         assertNotNull(result);
//     }
// }