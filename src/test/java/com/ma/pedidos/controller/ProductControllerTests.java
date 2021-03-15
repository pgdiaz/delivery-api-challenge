package com.ma.pedidos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import com.ma.pedidos.command.ProductCreateCommand;
import com.ma.pedidos.command.ProductUpdateCommand;
import com.ma.pedidos.exception.ResourceNotFoundException;
import com.ma.pedidos.model.ProductModel;
import com.ma.pedidos.service.ProductService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void getProductTest() throws Exception {

        ProductModel model = new ProductModel();
        UUID modelId = UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31");
        model.setId(modelId);
        model.setName("testProduct");
        model.setShortDescription("testShortDescription");
        model.setLongDescription("testLongDescription");
        model.setUnitPrice(BigDecimal.TEN);

        when(service.find(any(UUID.class))).thenReturn(model);

        String expectedContent = "{\"id\":\"89efb206-2aa6-4e21-8a23-5765e3de1f31\"," +
            "\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        this.mockMvc.perform(get("/products/89efb206-2aa6-4e21-8a23-5765e3de1f31"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedContent));
        
        verify(service, times(1)).find(eq(modelId));
    }

    @Test
    public void getProductNotFoundTest() throws Exception {

        UUID modelId = UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31");

        when(service.find(any(UUID.class)))
            .thenThrow(new ResourceNotFoundException("Product not found"));

        String expectedContent = "{\"error\":\"Product not found\"}";

        this.mockMvc.perform(get("/products/89efb206-2aa6-4e21-8a23-5765e3de1f31"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedContent));
        
        verify(service, times(1)).find(eq(modelId));
    }

    @Test
    public void createProductTest() throws Exception {

        ProductModel model = new ProductModel();
        UUID modelId = UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31");
        model.setId(modelId);
        model.setName("testProduct");
        model.setShortDescription("testShortDescription");
        model.setLongDescription("testLongDescription");
        model.setUnitPrice(BigDecimal.TEN);

        when(service.create(any(ProductCreateCommand.class))).thenReturn(model);

        String requestBody = "{\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        RequestBuilder request = MockMvcRequestBuilders.post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        String expectedContent = "{\"id\":\"89efb206-2aa6-4e21-8a23-5765e3de1f31\"," +
            "\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        String expectedLocation = "http://localhost/products/89efb206-2aa6-4e21-8a23-5765e3de1f31";

        this.mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedContent))
            .andExpect(header().string(HttpHeaders.LOCATION, expectedLocation));
        
        ArgumentCaptor<ProductCreateCommand> captor = ArgumentCaptor
            .forClass(ProductCreateCommand.class);

        verify(service, times(1)).create(captor.capture());

        ProductCreateCommand commandCaptured = captor.getValue();

        assertEquals("testProduct", commandCaptured.getName());
        assertEquals("testShortDescription", commandCaptured.getShortDescription());
        assertEquals("testLongDescription", commandCaptured.getLongDescription());
        assertEquals(BigDecimal.TEN, commandCaptured.getUnitPrice());
    }

    @Test void updateProductTest() throws Exception {

        UUID productId = UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31");

        String requestBody = "{\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        RequestBuilder request = MockMvcRequestBuilders
            .put("/products/89efb206-2aa6-4e21-8a23-5765e3de1f31")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent());

        ArgumentCaptor<ProductUpdateCommand> commandCaptor = ArgumentCaptor
            .forClass(ProductUpdateCommand.class);

        verify(service, times(1)).update(eq(productId), commandCaptor.capture());

        ProductUpdateCommand commandCaptured = commandCaptor.getValue();

        assertEquals("testProduct", commandCaptured.getName());
        assertEquals("testShortDescription", commandCaptured.getShortDescription());
        assertEquals("testLongDescription", commandCaptured.getLongDescription());
        assertEquals(BigDecimal.TEN, commandCaptured.getUnitPrice());
    }

    @Test void removeProductTest() throws Exception {

        UUID productId = UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31");

        this.mockMvc.perform(delete("/products/89efb206-2aa6-4e21-8a23-5765e3de1f31"))
            .andExpect(status().isNoContent());

        verify(service, times(1)).remove(eq(productId));
    }
}
