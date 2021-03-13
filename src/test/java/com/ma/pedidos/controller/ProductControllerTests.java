package com.ma.pedidos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        model.setId("1-2-3-4");
        model.setName("testProduct");
        model.setShortDescription("testShortDescription");
        model.setLongDescription("testLongDescription");
        model.setUnitPrice(BigDecimal.TEN);

        when(service.find(anyString())).thenReturn(model);

        String expectedContent = "{\"id\":\"1-2-3-4\"," +
            "\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        this.mockMvc.perform(get("/products/1-2-3-4"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent));
        
        verify(service, times(1)).find(eq("1-2-3-4"));
    }

    @Test
    public void getProductNotFoundTest() throws Exception {

        when(service.find(anyString()))
            .thenThrow(new ResourceNotFoundException("Product not found"));

        String expectedContent = "{\"error\":\"Product not found\"}";

        this.mockMvc.perform(get("/products/4-3-2-1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(expectedContent));
        
        verify(service, times(1)).find(eq("4-3-2-1"));
    }

    @Test
    public void createProductTest() throws Exception {

        ProductModel model = new ProductModel();
        model.setId("1-2-3-4");
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

        String expectedContent = "{\"id\":\"1-2-3-4\"," +
            "\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        String expectedLocation = "http://localhost/products/1-2-3-4";

        this.mockMvc.perform(request)
            .andExpect(status().isCreated())
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

        String requestBody = "{\"name\":\"testProduct\"," +
            "\"short_description\":\"testShortDescription\"," +
            "\"long_description\":\"testLongDescription\"," +
            "\"unit_price\":10}";

        RequestBuilder request = MockMvcRequestBuilders.put("/products/1-2-3-4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent());

        ArgumentCaptor<ProductUpdateCommand> commandCaptor = ArgumentCaptor
            .forClass(ProductUpdateCommand.class);

        verify(service, times(1)).update(eq("1-2-3-4"), commandCaptor.capture());

        ProductUpdateCommand commandCaptured = commandCaptor.getValue();

        assertEquals("testProduct", commandCaptured.getName());
        assertEquals("testShortDescription", commandCaptured.getShortDescription());
        assertEquals("testLongDescription", commandCaptured.getLongDescription());
        assertEquals(BigDecimal.TEN, commandCaptured.getUnitPrice());
    }

    @Test void removeProductTest() throws Exception {

        this.mockMvc.perform(delete("/products/4-3-2-1"))
            .andExpect(status().isNoContent());

        verify(service, times(1)).remove(eq("4-3-2-1"));
    }
}
