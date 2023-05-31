package com.carrental.microservices.orderservice.domain.mapper;

import com.carrental.microservices.orderservice.domain.entity.Refund;
import com.carrental.microservices.orderservice.domain.dto.request.CreateRefundRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.RefundResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface presents the basic contract for converting Refund to RefundDTO and vice versa.
 */
@Mapper
public interface RefundMapper {

    @Mapping(target = "orderId", source = "order.id")
    RefundResponseDTO refundToRefundResponseDTO(Refund refund);

    Refund createRefundRequestDTOToRefund(CreateRefundRequestDTO createRefundRequestDTO);
}
