import java.time.Duration;
import java.time.LocalTime;

public class A5_LocalTimeDemo2 {
    public static void main(String[] args) {
        // PC방의 PC 이용 시작 시간
        LocalTime start = LocalTime.of(14,24,35);
        System.out.println("PC 이용 시작 시각: " + start);

        // PC방의 PC 이용 시작 시간
        LocalTime end = LocalTime.of(17,31,19);
        System.out.println("PC 이용 종료 시각: " + end);

        // PC 이용 시간 계산
        Duration between = Duration.between(start,end);
        System.out.println("총 PC 이용 시간: " + between);
        System.out.println("총 PC 이용 시간: " + between.toHours()
                                        + ":" + between.toMinutes()
                                        + ":" + between.toMillis());
        //시각 차 계산에도 Duration 클래스가 사용된다.
    }
}
