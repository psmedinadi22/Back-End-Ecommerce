package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payment")
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paymentdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;
    private String status;
    private String state;
    private String error;
    private String message;
    private Integer orderId;
    private String externalId;
    private String externalState;
    private Date creationDate;
    @ManyToOne
    private Userdb user;

    @Override
    public String toString() {
        return "Paymentdb{" +
                "paymentID=" + paymentID +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", orderId=" + orderId +
                ", externalId='" + externalId + '\'' +
                ", externalState='" + externalState + '\'' +
                ", creationDate=" + creationDate +
                ", user=" + user +
                '}';
    }


}
