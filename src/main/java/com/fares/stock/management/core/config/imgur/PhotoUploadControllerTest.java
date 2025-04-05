package com.fares.stock.management.core.config.imgur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DirtiesContext
@ExtendWith(MockitoExtension.class)  // Enables Mockito annotations
public class PhotoUploadControllerTest {

    private MockMvc mockMvc;

//    @Mock(lenient = true)
//    private PhotoUploadClient photoUploadClient;  // Mock PhotoUploadClient

    @InjectMocks
    private TestController testController;  // Inject mocks into the controller

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

//    @Test
//    public void testUploadPhoto() throws Exception {
//        // Prepare a mock file (use any file for testing)
//        MockMultipartFile mockFile = new MockMultipartFile(
//                "file", "testImage.jpg", "image/jpeg", "dummy content".getBytes());
//
//        // Mock the behavior of the photoUploadClient
//        when(photoUploadClient.uploadPhoto(mockFile)).thenReturn("https://imgur.com/test-image");
//
//        // Perform the upload request and verify the response
//        // Perform the upload request and verify the response
//        mockMvc.perform(multipart("/test/upload")  // The endpoint you're testing
//                        .file(mockFile))
//                .andExpect(status().isOk())  // Expect HTTP 200 OK
//                .andExpect(content().string("Image uploaded successfully: https://imgur.com/test-image"))
//                .andDo(print());  // This will print request and response to console
//
//    }
}
