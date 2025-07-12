package com.mballem.demoparkapi.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mballem.demoparkapi.web.dto.UserRequestDTO;
import com.mballem.demoparkapi.web.dto.UserResponseDTO;
import com.mballem.demoparkapi.web.entity.User;
import com.mballem.demoparkapi.web.mapper.UserMapper;
import com.mballem.demoparkapi.web.repository.UserRepository;
import com.mballem.demoparkapi.web.validation.ValidationRules;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidationRules validationRules;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private UserRequestDTO userRequest;

    @BeforeEach
    void setUp() {
        // Preparar dados de teste
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("joao");
        user1.setPassword("123456");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("maria");
        user2.setPassword("123456");

        userRequest = new UserRequestDTO(
        "joao",
        "123456");
    }

    @Test
    void deveListarTodosUsuarios() {
      // Given (Arrange) - Preparar dados
      List<User> users = Arrays.asList(user1, user2);
      when(userRepository.findAll()).thenReturn(users);

      // When (Act) - Executar ação
      List<UserResponseDTO> result = userService.findAll();

      // Then (Assert) - Verificar resultado
      assertNotNull(result);
      assertEquals(2, result.size());
      assertEquals("joao", result.get(0).getUsername());
      assertEquals("maria", result.get(1).getUsername());

      // Verificar se o repository foi chamado
      verify(userRepository).findAll();
    }
    
    @Test
    void deveEncontrarUsuarioPorId() {

      when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

      assertNotNull(user1);
      assertEquals(1L, user1.getId());
      assertEquals("joao", user1.getUsername());
      assertEquals("123456", user1.getPassword());

      verify(userRepository).findById(1L);
    }
    
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
      // Given
      when(userRepository.findById(999L)).thenReturn(Optional.empty());

      // When & Then
      RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        userService.findById(999L);
      });

      assertEquals("User not found", exception.getMessage());
      verify(userRepository).findById(999L);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
      when(userMapper.toUser(userRequest)).thenReturn(user1);
      doNothing().when(validationRules).validateUser(user1);
      when(userRepository.save(user1)).thenReturn(user1);

      User user = userService.save(userRequest);

      assertNotNull(user);
      assertEquals(1L, user.getId());
      assertEquals("joao", user.getUsername());
      assertEquals("123456", user.getPassword());

      verify(userMapper).toUser(userRequest);
      verify(validationRules).validateUser(user1);
      verify(userRepository).save(user1);
    };

    @Test
    void deveExcluirUsuarioComSucesso() {
      when(userRepository.existsById(1L)).thenReturn(true);
      doNothing().when(validationRules).validateUser(user1);
      when(userRepository.existsById(1L)).thenReturn(true);
      doNothing().when(userRepository).deleteById(1L);


      verify(userMapper).toUser(userRequest);
      verify(validationRules).validateUser(user1);
    }
} 