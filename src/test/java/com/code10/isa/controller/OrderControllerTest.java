package com.code10.isa.controller;

import com.code10.isa.model.OrderItem;
import com.code10.isa.model.OrderItemStatus;
import com.code10.isa.model.dto.OrderDto;
import com.code10.isa.model.user.Role;
import com.code10.isa.service.MenuItemService;
import com.code10.isa.service.TableService;
import com.code10.isa.util.JsonUtil;
import com.code10.isa.util.LoginUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class OrderControllerTest {

    private static final String BASE_URL = "/api/orders";

    private static final long EXISTING_TABLE_ID = 1;

    private static final long EXISTING_MENUITEM_ID = 1;

    @Autowired
    private TableService tableService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void createWithValidDataShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.WAITER);

        OrderDto orderDto = new OrderDto();
        orderDto.setTable(tableService.findById(EXISTING_TABLE_ID));
        orderDto.setReadyAtArrival(true);
        orderDto.setOrderItems(new ArrayList<>());
        final OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItemService.findById(EXISTING_MENUITEM_ID));
        orderItem.setAmount(3);
        orderItem.setStatus(OrderItemStatus.CREATED);
        orderDto.getOrderItems().add(orderItem);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(orderDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
