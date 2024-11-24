package com.ctrip.dcs.intl.shopping.restful.application.command.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class speedTool {

    private static Speed 纳什之牙 = Speed.builder().speed(45).name("纳什之牙").amount(3000).build();
    private static Speed 破败王者之刃 = Speed.builder().speed(35).name("破败王者之刃").amount(3200).build();
    private static Speed 卢安那的飓风 = Speed.builder().speed(35).name("卢安那的飓风").amount(3000).build();
    private static Speed 三相之力 = Speed.builder().speed(30).name("三相之力").amount(3333).build();
    private static Speed 强磁电铳 = Speed.builder().speed(35).name("强磁电铳").amount(2900).build();
    private static Speed 智慧末刃 = Speed.builder().speed(45).name("智慧末刃").amount(2700).build();
    private static Speed 烈阳魔刃 = Speed.builder().speed(40).name("烈阳魔刃").amount(3000).build();
    private static Speed 不朽盾弓 = Speed.builder().speed(15).name("不朽盾弓").amount(3000).build();
    private static Speed 界弓 = Speed.builder().speed(30).name("界弓").amount(3400).build();
    private static Speed 海克斯岚切 = Speed.builder().speed(20).name("海克斯岚切").amount(3000).build();
//    private static Speed 狂战士胫甲 = Speed.builder().speed(35).name("狂战士胫甲").amount(1400).build();

    /**
     * 稳定的攻速装
     * 其他不稳定的攻速装不列在内：饮血剑、有梦之魂、幻影之舞
     * 攻速鞋也不在内
     * 远程攻速装最多可以搭配5件，一件留给鞋子
     * 近战攻速装最多可以搭配4件，一件留给鞋子，另一件留给心之钢
     * 排序：攻速降序，装备数量升序、价格降序
     * @param args
     */
    public static void main(String[] args) {
        List<Speed> close = Arrays.asList(纳什之牙, 破败王者之刃, 三相之力, 智慧末刃, 烈阳魔刃, 不朽盾弓, 界弓, 海克斯岚切);
        List<Speed> far = Arrays.asList(纳什之牙, 卢安那的飓风, 强磁电铳, 烈阳魔刃, 不朽盾弓, 界弓, 海克斯岚切);

        System.out.println("-------------------------------close----------------------------------");
        handle(close, 4);

        System.out.println();
        System.out.println("-------------------------------far----------------------------------");
        handle(far, 5);
    }

    private static void handle(List<Speed> speeds, int max) {
        List<SpeedCombine> result = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            combine(speeds, 0, i, new ArrayList<>(), result);
        }
        result.stream().sorted().forEach(System.out::println);
    }

    private static void combine(List<Speed> speeds, int start, int count, List<Speed> temp, List<SpeedCombine> result) {
        if (count == 0) {
            result.add(new SpeedCombine(new ArrayList<>(temp)));
            return;
        }
        for (int i = start; i < speeds.size(); i++) {
            temp.add(speeds.get(i));
            combine(speeds, i + 1, count - 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    static class SpeedCombine implements Comparable<SpeedCombine> {
        private final List<Speed> speeds;

        public SpeedCombine(List<Speed> speeds) {
            this.speeds = speeds;
        }

        public int speedSum() {
            return speeds.stream().mapToInt(Speed::getSpeed).sum();
        }

        public int amountSum() {
            return speeds.stream().mapToInt(Speed::getAmount).sum();
        }

        @Override
        public int compareTo(@NotNull SpeedCombine o) {
            if (speedSum() != o.speedSum()) {
                return o.speedSum() - speedSum();
            }
            if (speeds.size() != o.speeds.size()) {
                return speeds.size() - o.speeds.size();
            }
            return o.amountSum() - amountSum();
        }

        public String toString() {
            return "speed:" + speedSum() + ", amount:" + amountSum() + ", combine:" + speeds.stream().map(Speed::getName).reduce((a, b) -> a + "," + b).orElse("");
        }
    }

    @Getter
    @Setter
    @Builder
    static class Speed {
        private int speed;
        private String name;
        private int amount;
    }
}
