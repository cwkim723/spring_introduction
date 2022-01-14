package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /*
        Optional 에서 제공하는 of 와 ofNullable 매서드를 사용. 둘의 차이점은 of는 인자로서 null값을 받지 않는다는 것이고 ofNullable은 null값을 허용
        ofNullable은 일반 객체뿐만 아니라 null값까지 입력으로 받을 수 있음
            -> isPresent 메서드로 현재 Optional이 보유한 값이 null인지 아닌지를 확인 가능
            -> if를 이용한 null값 체크를 대체할 수 있음
        Optional에서는 값을 가져올 때 자주 사용되는 메서드 두 가지: orElseGet, orElse
            - 이 메서드가 자주 사용되는 이유는 null값 체크를 할 수 있음과 동시에 null값일 경우일 경우 간단한 코드로 처리할 수 있어 코드의 가독성이 좋아지고 코드 생산성이 올라감
            - 주의할 부분은 null값일 때 어떤 값을 쓸 것이냐를 처리하는 로직에 함수를 썼을 때
            - orElseGet은 Optional이 가지고 있는 값이 null일 경우에만 orElseGet에 주어진 함수를 실행
            - 하지만 orElse는 null값 유무와 상관없이 사용
				출처: https://engkimbs.tistory.com/646
    */

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // Optional.ofNullable: null이 반환될 가능성이 있을 때
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
