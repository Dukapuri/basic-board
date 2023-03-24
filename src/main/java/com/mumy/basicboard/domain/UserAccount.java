package com.mumy.basicboard.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(
        indexes = {
                @Index(columnList = "email", unique = true),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        }
)
@Entity
public class UserAccount extends AuditingFields{

    @Id
    @Column(length = 50)
    private String userId;

    @Setter @Column(nullable = false) private String userPassword;

    @Setter @Column(length = 100)
    private String email;

    @Setter @Column(length = 100)
    private String nickname;
    @Setter
    private String memo;

    protected UserAccount() {}

    private UserAccount(String userId, String user_password, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = user_password;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String user_password, String email, String nickname, String memo){
        return new UserAccount(userId, user_password, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;

        return userId != null && userId.equals(userAccount.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
