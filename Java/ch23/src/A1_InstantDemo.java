import java.time.Duration;
import java.time.Instant;

public class A1_InstantDemo {
    public static void main(String[] args) {
        Instant start = Instant.now();  // 현재 시각 정보를 담음
        System.out.println("시작: " + start.getEpochSecond());
        // get.EpochSecond는 1970년 01월 01일을 기준으로 지나온 시간을
        // 초 단위로 계산한 결과

        System.out.println("Time flies like an arrow.");

        Instant end = Instant.now();
        System.out.println("끝: " + end.getEpochSecond());

        Duration between = Duration.between(start, end);
        System.out.println("밀리 초 단위 차: " + between.toMillis());
    }
}
