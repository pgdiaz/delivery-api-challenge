package com.ma.pedidos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import com.ma.pedidos.command.OrderCreateCommand;
import com.ma.pedidos.model.OrderModel;
import com.ma.pedidos.model.SearchModel;
import com.ma.pedidos.service.OrderService;
import com.ma.pedidos.utils.MocksBuilder;
import com.ma.pedidos.utils.ResourceReader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @Test
    public void searchOrdersTest() throws Exception {
        
        SearchModel<OrderModel> searchOrders = MocksBuilder.buildSearchOrdersResult();
        when(service.findByDate(any(LocalDate.class))).thenReturn(searchOrders);

        LocalDate expectedDate = LocalDate.of(2020, 05, 26);

        ResourceReader reader = new ResourceReader("/mock/searchOrders.json");
        String expectedContent = reader.asString();

        this.mockMvc.perform(get("/orders?date=2020-05-26"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedContent));
        
        verify(service, times(1)).findByDate(eq(expectedDate));
    }

    @Test
    public void createOrderTest() throws Exception {
        
        OrderModel orderCreated = MocksBuilder.buildOrderWhitDiscount();
        when(service.create(any(OrderCreateCommand.class))).thenReturn(orderCreated);

        String requestBody = "{\"address\":\"Dorton Road 80\"," +
            "\"email\":\"tsayb@opera.com\"," +
            "\"phone\":\"(0351) 48158101\"," +
            "\"time\":\"21:00\"," +
            "\"detail\":[" +
            "{\"product\":\"89efb206-2aa6-4e21-8a23-5765e3de1f31\",\"quantity\":2}," +
            "{\"product\":\"e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1\",\"quantity\":2}]}";

        RequestBuilder request = MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        ResourceReader reader = new ResourceReader("/mock/orderCreated.json");
        String expectedContent = reader.asString();

        this.mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedContent));
    }

    @Test
    public void createOrderBadRequestTest() throws Exception {

        String requestBody = "{\"time\":\"21:00\"," +
            "\"detail\":[" +
            "{\"product\":\"e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1\",\"quantity\":1}]}";

        RequestBuilder request = MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);
        
        String expectedContent = "{\"errors\":[" +
            "{\"error\":\"The address is required\"}]}";

        this.mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedContent));
    }

    @Test
    public void createOrderInvalidDetailBadRequest() throws Exception {

        String requestBody = "{\"address\":\"Dorton Road 80\"," +
            "\"email\":\"tsayb@opera.com\"," +
            "\"phone\":\"(0351) 48158101\"," +
            "\"time\":\"21:00\"," +
            "\"detail\":[" +
            "{\"product\":\"89efb206-2aa6-4e21-8a23-5765e3de1f31\",\"quantity\":1}," +
            "{\"product\":\"e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1\"}]}";

        RequestBuilder request = MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);
        
        String expectedContent = "{\"errors\":[" +
            "{\"error\":\"The quantity is required\"}]}";

        this.mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedContent));
    }
}
