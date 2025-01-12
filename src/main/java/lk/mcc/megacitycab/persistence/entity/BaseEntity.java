package lk.mcc.megacitycab.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Title: Mega-City-Cab
 * Description: BaseEntity Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class BaseEntity {
    @CreatedDate
    @Column(name = "CREATED_TIME", updatable = false)
    private LocalDateTime createdTime;

    @CreatedBy
    @Column(name = "CREATED_USER", updatable = false)
    private String createdUser;

    @LastModifiedDate
    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;

    @LastModifiedBy
    @Column(name ="UPDATED_USER")
    private String updatedUser;
}
