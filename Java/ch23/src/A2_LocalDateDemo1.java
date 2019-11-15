import java.time.LocalDate;

public class A2_LocalDateDemo1 {
    public static void main(String[] args) {

        // 오늘
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);

        // 올 해의 크리스마스
        LocalDate xmas = LocalDate.of(today.getYear(), 12, 25);
        System.out.println("X-mas: " + xmas);

        // 올 해의 크리스마스 이브
        LocalDate eve = xmas.minusDays(1);
        System.out.println("X-mas Eve: " + eve);

    }
}
