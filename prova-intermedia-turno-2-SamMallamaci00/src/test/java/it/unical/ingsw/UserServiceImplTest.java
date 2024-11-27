package it.unical.ingsw;


import it.unical.ingsw.dao.UserDao;
import it.unical.ingsw.dto.CreateUserDTO;
import it.unical.ingsw.dto.UserConverter;
import it.unical.ingsw.dto.UserDTO;
import it.unical.ingsw.entities.Role;
import it.unical.ingsw.entities.User;
import it.unical.ingsw.service.EmailService;
import it.unical.ingsw.service.SecurityService;
import it.unical.ingsw.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private SecurityService securityService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserConverter converter;

    @Mock
    private User user;

    @Mock
    private CreateUserDTO createUserDTO;

    @Mock
    private UserDTO userDTO;

    @Mock
    private Role role;


    @BeforeEach
    public void preoareTest(){
        userService = new UserServiceImpl(userDao, securityService, emailService, converter);
        userService.setShouldVerifyEmail(true);
    }

    @BeforeEach
    public void prepareUser(){

        user = new User("NameTest", "passwordTest", "email@test.com");

        createUserDTO = new CreateUserDTO("Samuele", "passwordTest", "email@test.com");

    }

    @BeforeEach
    public void prepareUserDto(){

        userDTO = new UserDTO("idtest", "nametest", "emailtest@gmail.com", role);

        createUserDTO = new CreateUserDTO("Samuele", "passwordTest", "email@test.com");

    }





    @Test
    public void shouldNotCreateUserWhenUserExists() throws Exception {

    when(userDao.getUserByEmail(anyString())).thenReturn(user);



        assertThrows(RuntimeException.class ,() -> {
            userService.createUser(createUserDTO);
        });

   }


    @Test
    public void shoulConvertUserToDTO() throws Exception{



        when(userDao.getUserByEmail(anyString())).thenReturn(null);


        when(converter.createUserDTOtoUser(createUserDTO)).thenReturn(user);

        when(securityService.hash(anyString())).thenReturn("hashed_password");

        when(userDao.save(user)).thenReturn(user);

        when(converter.userToUserDTO(user)).thenReturn(userDTO);
        UserDTO result = userService.createUser(createUserDTO);





        System.out.println("Result: " + result);  // Aggiungi questa stampa

        assertNotNull(result);
        verify(emailService, never()).sendEmailVerificationEmail(anyString());
    }

    @Test
    public void shouldNotCreateUserDtoWhenShouldVerifyIsFalse() throws Exception{

        userService.setShouldVerifyEmail(false);

        when(userDao.getUserByEmail(anyString())).thenReturn(null);

        when(converter.createUserDTOtoUser(createUserDTO)).thenReturn(user);

        when(securityService.hash(anyString())).thenReturn("hashed_password");

        when(userDao.save(user)).thenReturn(user);



        UserDTO result = userService.createUser(createUserDTO);


        assertEquals(user.getPassword(), "hashed_password");
        assertNull(result);

        verify(emailService,times(1)).sendEmailVerificationEmail(anyString());


    }


}
