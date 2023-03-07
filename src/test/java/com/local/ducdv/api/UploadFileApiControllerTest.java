package com.local.ducdv.api;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.local.ducdv.mapper.UserMapper;

public class UploadFileApiControllerTest extends AbstractTest {
    @Mock
    UserMapper userMapper;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void test_uploadCsv() throws Exception {
        String uri = "/api/upload/csv";
        String path = "D:\\JACIC\\data\\upload2.csv";
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);

        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", IOUtils.toByteArray(input));

        mvc.perform(MockMvcRequestBuilders.multipart(uri)
                        .file(multipartFile)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_uploadExcel() throws Exception {
        String uri = "/api/upload/excel";
        String path = "D:\\JACIC\\data\\EXCEL_export_users_20230303.xlsx";
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);

        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/octet-stream", IOUtils.toByteArray(input));

        mvc.perform(MockMvcRequestBuilders.multipart(uri)
                        .file(multipartFile)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
