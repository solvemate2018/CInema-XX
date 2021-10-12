package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.cinemaxx.entities.Projection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDTO {
    private int ticketRow;
    private int ticketColumn;
    private boolean sold;
    private ProjectionDTO projection;
}
