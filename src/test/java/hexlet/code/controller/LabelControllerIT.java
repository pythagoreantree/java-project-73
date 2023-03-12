package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dtos.LabelDto;
import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.repositories.LabelRepository;
import hexlet.code.repositories.UserRepository;
import hexlet.code.utils.AuthorizationUtils;
import hexlet.code.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controller.UserControllerIT.DEFAULT_USER_EMAIL;
import static hexlet.code.utils.JsonUtils.asJson;
import static hexlet.code.utils.JsonUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class LabelControllerIT {

    @Autowired
    private AuthorizationUtils authUtils;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void before() throws Exception {
        userUtils.addDefaultUser();
    }

    @AfterEach
    public void tearDown() {
        labelRepository.deleteAll();
        userRepository.deleteAll();
    }

    private static final String LABEL_CONTROLLER_PATH =  "/api/labels";

    private static final String LABEL_CONTROLLER_PATH_ID =  "/api/labels/{id}";

    private static final String DEFAULT_LABEL_NAME = "Bug";

    @Test
    public void createLabel() throws Exception {

        assertEquals(0, labelRepository.count());

        addDefaultLabel();

        assertEquals(1, labelRepository.count());
    }

    private Label addDefaultLabel() throws Exception {
        return addLabel(DEFAULT_LABEL_NAME);
    }

    private Label addLabel(String labelName) throws Exception {
        final LabelDto labelDto = new LabelDto(labelName);

        final MockHttpServletRequestBuilder request = authUtils.getAuthRequest(
                post(LABEL_CONTROLLER_PATH)
                        .content(asJson(labelDto))
                        .contentType(APPLICATION_JSON),
                DEFAULT_USER_EMAIL
        );

        final MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        final Label label = fromJson(response.getContentAsString(), new TypeReference<>() { });
        return label;
    }
}


