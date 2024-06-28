package com.hasanalmunawr.files_keeper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasanalmunawr.files_keeper.file.FileEntity;
import com.hasanalmunawr.files_keeper.file.FilesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FilesRepository repository;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void searchByIsbn() throws Exception {
		FileEntity entity = new FileEntity();
		entity.setIsbn("999999");
		entity.setTitle("am i programmer?");
		entity.setPrice(0L);
		repository.save(entity);


		String json = mapper.writeValueAsString(entity);

		mockMvc.perform(
						get("/api/v1/user/999999")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpectAll(
				status().isOk(),
				content().json(json)
		);

	}
}
