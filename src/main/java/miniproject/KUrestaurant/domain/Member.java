package miniproject.KUrestaurant.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Size(min=1, max=10, message = "이름은 1~10자 이내여야 합니다.")
    private String name;

    @NotNull
    @Size(min=4, max=15, message = "ID는 4~10자 이내여야 합니다.")
    private String loginId;

    @NotNull
    @Size(min=5, message = "비밀번호는 5자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "회원 카테고리를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @OneToMany(mappedBy = "member")
    private List<MemberRestaurant> memberRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replies = new ArrayList<>();
}
