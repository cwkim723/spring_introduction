package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.ManagedMap;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        // 테스트는 서로의 의존관계없이 만들어야 함 -> 하나의 테스트가 끝날 때마다 clear를 해줘야 함
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();

        // import org.junit.jupiter.api.Assertions;
//        Assertions.assertEquals(member, result); // Process finished with exit code 0
//        Assertions.assertEquals(member, null); -> 실행하면 에러 뜸

        // import org.assertj.core.api.Assertions;
//        Assertions.assertThat(member).isEqualTo(result); // Process finished with exit code 0
        assertThat(member).isEqualTo(result); // import static org.assertj.core.api.Assertions.assertThat; 추가 시
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // Process finished with exit code 0
//        Member result = repository.findByName("spring2").get(); // 에러

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.size()).isEqualTo(3); // 에러

    }



}
