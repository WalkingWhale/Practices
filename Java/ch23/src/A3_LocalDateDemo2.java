import java.time.LocalDate;
import java.time.Period;

public class A3_LocalDateDemo2 {
    public static void main(String[] args) {
        // 오늘
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);

        // 올 해의 크리스마스
        LocalDate xmas = LocalDate.of(today.getYear(), 12, 25);
        System.out.println("X-mas: " + xmas);

        // 크리스마스까지 앞으로 며칠?
        Period left = Period.between(today,xmas);
        System.out.println("X-mas까지 앞으로 "
                + left.getMonths() + "개월 "
                + left.getDays() + "일");

    }
}
