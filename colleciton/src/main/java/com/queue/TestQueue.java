package com.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestQueue {

    @Test
    public void testQueue() {
        // 避免在队列中添加null值
        Queue<String> queue = new LinkedList<>();
        queue.add("apple");
        queue.offer("banana");
        System.out.println(queue);

        // add offer 队尾添加
        // poll remove 队头取出
        // element poll 查看队头
        System.out.println(queue.peek());
        // 抛出异常
        System.out.println(queue.element());
        // 返回null
        System.out.println(queue.peek());

        System.out.println(queue.size());

        String apple = queue.poll();
        System.out.println(apple);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.remove());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
    }

    @Test
    public void testPriority() {
        // 比较器给出优先级

        Queue<Person> queue = new PriorityQueue<>();
        queue.add(new Person("tom1", 1));
        queue.add(new Person("tom2", 2));
        queue.add(new Person("tom3", 3));
        queue.add(new Person("tom4", 4));

        Person ele;
        while ((ele = queue.poll()) != null) {
            System.out.println(ele);
        }
    }

    public static void main(String[] args) {
        // 可变, 不可变, 旋转, 转换
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Person implements Comparable<Person>{
        private String name;
        private Integer age;

        @Override
        public int compareTo(Person o) {
            // 倒叙排列
            return this.getAge() - o.getAge();
        }
    }
}
