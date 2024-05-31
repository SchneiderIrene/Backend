package de.leafgrow.leafgrow_project.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expired")
    private LocalDateTime expired;

    public ConfirmationCode() {
    }

    public ConfirmationCode(String code, User user, LocalDateTime expired) {
        this.code = code;
        this.user = user;
        this.expired = expired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmationCode that = (ConfirmationCode) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(code, that.code)) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(expired, that.expired);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfirmationCode{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", user=" + user +
                ", expired=" + expired +
                '}';
    }
}
