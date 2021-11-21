import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestSet {

    @Test
    public void testQueue() {
        // PriorityQueue<String> queue = new PriorityQueue<>(10);
        Queue<String> queue = new LinkedList<>();

        queue.add("pear");
        queue.add("banana");
        queue.add("apple");

        String element = null;
        while ((element = queue.poll()) != null) {
            System.out.println(element);
        }
    }

    @Test
    public void testSet() {
        List<Message> msgs = List.of(
                new Message(1, "Hello!"),
                new Message(2, "发工资了吗?"),
                new Message(2, "发工资了吗?"),
                new Message(3, "去哪吃饭?"),
                new Message(3, "去哪吃饭?"),
                new Message(4,"Bye~")
        );
        
        List<Message> displayMessages = process(msgs);
        for(Message msg: displayMessages) {
            System.out.println(msg);
        }
    }

    private List<Message> process(List<Message> msgs) {
        Set<Message> set = new TreeSet<Message>((m1, m2) -> m2.sequence - m1.sequence);
        set.addAll(msgs);
        return set.stream().toList();
    }

    static class Message {
        private int sequence;
        private String text;

        public Message(int sequence, String text) {
            this.sequence = sequence;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "sequence=" + sequence +
                    ", text='" + text + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            int result = sequence;
            result = 31 * result + (text != null ? text.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o instanceof Message m) {
                return this.sequence == m.sequence;
            }
            return false;
        }
    }
}

