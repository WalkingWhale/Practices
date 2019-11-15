import java.util.Deque;
import java.util.LinkedList;

public class D2_LinkedListStack {
    public static void main(String[] args) {
        Deque<String> stack = new LinkedList<>();

        // 앞으로 넣고
        stack.offerFirst("Box");
        stack.offerFirst("Toy");
        stack.offerFirst("Robot");

        // 앞에서 꺼내기
        System.out.println(stack.pollFirst());
        System.out.println(stack.pollFirst());
        System.out.println(stack.pollFirst());

        // 뒤로 넣고
        stack.offerLast("Box");
        stack.offerLast("Toy");
        stack.offerLast("Robot");

        // 뒤에서 꺼내기
        System.out.println(stack.pollLast());
        System.out.println(stack.pollLast());
        System.out.println(stack.pollLast());

        // 뒤로 넣고
        stack.offerLast("Box");
        stack.offerLast("Toy");
        stack.offerLast("Robot");

        // 앞에서 꺼내기
        System.out.println(stack.pollFirst());
        System.out.println(stack.pollFirst());
        System.out.println(stack.pollFirst());

    }
}
