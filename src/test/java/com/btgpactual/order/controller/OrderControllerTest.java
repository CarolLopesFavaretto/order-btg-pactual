package com.btgpactual.order.controller;

import com.btgpactual.order.factory.OrderResponseFactory;
import com.btgpactual.order.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService service;

    @InjectMocks
    OrderController controller;

    @Captor
    ArgumentCaptor<Long> customerIdCaptor;

    @Captor
    ArgumentCaptor<PageRequest> pageCaptor;

    @Nested
    class listOrders {

        @Test
        void shouldReturnStatusCodeOk() {
            var customerId = 1L;
            var page = 0;
            var size = 10;
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(service).findAllByCustomerId(anyLong(), any());
            doReturn(BigDecimal.valueOf(100.0))
                    .when(service).findAmountOnOrdersByCustomerId(anyLong());

            var response = controller.listOrders(customerId, page, size);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void shouldPassCorrectParametersToService() {
            var customerId = 1L;
            var page = 0;
            var size = 10;
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(service).findAllByCustomerId(customerIdCaptor.capture(), pageCaptor.capture());
            doReturn(BigDecimal.valueOf(100.0))
                    .when(service).findAmountOnOrdersByCustomerId(customerIdCaptor.capture());

            var response = controller.listOrders(customerId, page, size);
            assertEquals(2, customerIdCaptor.getAllValues().size());
            assertEquals(customerId, customerIdCaptor.getAllValues().get(0));
            assertEquals(customerId, customerIdCaptor.getAllValues().get(1));
            assertEquals(page, pageCaptor.getValue().getPageNumber());
            assertEquals(size, pageCaptor.getValue().getPageSize());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {

            // ARRANGE - prepara todos os mocks para a execucao
            var customerId = 1L;
            var page = 0;
            var pageSize = 10;
            var totalOnOrders = BigDecimal.valueOf(20.50);
            var pagination = OrderResponseFactory.buildWithOneItem();
            doReturn(pagination)
                    .when(service).findAllByCustomerId(anyLong(), any());
            doReturn(totalOnOrders)
                    .when(service).findAmountOnOrdersByCustomerId(anyLong());

            // ACT - executar o metodo a ser testado
            var response = controller.listOrders(customerId, page, pageSize);

            // ASSERT - verifica se a execucao foi certinha
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().data());
            assertNotNull(response.getBody().pagination());
            assertNotNull(response.getBody().summary());

            assertEquals(totalOnOrders, response.getBody().summary().get("amountOnOrders"));

            assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalElements());
            assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalPages());
            assertEquals(pagination.getNumber(), response.getBody().pagination().page());
            assertEquals(pagination.getSize(), response.getBody().pagination().pageSize());

            assertEquals(pagination.getContent(), response.getBody().data());
        }
    }
}