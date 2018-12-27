package com.caiyuna.witness;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.caiyuna.proxy.CGlibProxy;
import com.caiyuna.proxy.Train;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static TreeMap<Long, String> consistentBuckets = new TreeMap<>();
    public static void main(String[] args) {
        Map<String, String> hm = new HashMap<String, String>();
        hm.put(null, "b1");
        hm.put("a", "a1");
        String a = "abcde";
        char[] aar = a.toCharArray();
        int hashcode = a.hashCode();
        System.out.println("hashcode:" + hashcode);
        for (char c : aar) {
            System.out.println(c + "&:" + (int) c);
        }

        Integer i = Integer.parseInt("-12");
        int aw = (i >> 3);
        System.out.println("移位后:" + aw);

        System.out.println("&操作：" + (5 & 12));

        int ae = 4, b = 5;
        b++;
        
        try {
            populateConsistentBuckets(new String[] { "A", "B", "C", "D" });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("now:" + Instant.now().toEpochMilli() + "," + System.currentTimeMillis() + "++"
                + Instant.now().plus(72, ChronoUnit.HOURS).toEpochMilli());

    }

    private static void populateConsistentBuckets(String[] servers) throws NoSuchAlgorithmException {

        // 采用 md5计算key值
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        

        // 为每个server分配虚拟节点
        for (int i = 0; i < servers.length; i++) {
            for (long j = 0; j < 150; j++) {

                byte[] d = md5.digest((servers[i] + "-" + j).getBytes());

                for (int h = 0; h < 4; h++) {
                    Long k = ((long) (d[3 + h * 4] & 0xFF) << 24) | ((long) (d[2 + h * 4] & 0xFF) << 16) | ((long) (d[1 + h * 4] & 0xFF) << 8)
                            | ((long) (d[0 + h * 4] & 0xFF));

                    consistentBuckets.put(k, servers[i]);
                }

            }
        }

    }

    public String getNodeForKey(String key) throws NoSuchAlgorithmException {

        // 采用 md5计算key值
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();

        long hv = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);

        SortedMap<Long, String> tmap = consistentBuckets.tailMap(hv);
        Long res = (tmap.isEmpty()) ? consistentBuckets.firstKey() : tmap.firstKey();
        // 获取下一个节点
        String node = consistentBuckets.get(res);

        return node;
    }

    @Test
    public void testSuperProxy() {
        CGlibProxy proxy = new CGlibProxy();
        Train tr = (Train) proxy.getProxy(Train.class);
        tr.move();
    }

    @Test
    public void testSerious() {
        Person tom = new Person("tom");
        tom.setAge(18);
        Person jerrey = new Person("jerrey");
        jerrey.setAge(23);
        Person hary = new Person("hary");
        hary.setAge(56);
        Person peter = new Person("peter");
        peter.setAge(100);
        Person petera = new Person("peter");
        petera.setAge(10);

        List<Person> ps = new ArrayList<>();
        ps.add(tom);
        ps.add(hary);
        ps.add(jerrey);
        ps.add(peter);
        ps.sort((per1, per2) -> per2.getAge() - per1.getAge());
        ps.forEach(p -> System.out.println(p));
        System.out.println(ps.stream().filter(person -> person.getAge() > 30).count());
        
        List<String> artistNames = ps.stream().map(p -> p.getName()).collect(Collectors.toList());
        
        artistNames.forEach(artname -> System.out.println(artname));

        /*System.out.println(JSON.toJSONString(ps));
        ObjectMapper om = new ObjectMapper();
        try {
            System.out.println(om.writeValueAsString(ps));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }

    @Test
    public void testStringStream() {

        String hex = "IamBuAAStudent";
        String hey = "IamqinghuaStudent";
        String hez = "IamBeidaStudent";
        long lowers = hex.chars().filter(ci -> ci > 91 && ci < 119).count();

        System.out.println("lower count:" + lowers);
        
        long mostLowers = Stream.of(hex, hey, hez).mapToLong(AppTest::countLowers).max().getAsLong();
        System.out.println("mostLowers:" + mostLowers);

    }

    private static long countLowers(String charstr) {
        return charstr.chars().filter(ci -> ci > 91 && ci < 119).count();
    }

    private static int oper(int a, Function<Integer, Integer> action) {
        return action.apply(a);
    }

    private static final int addOne(int a) {
        return a + 1;
    }

    @Test
    public void testFunctionBase() {
        int x = 2;
        // int y = oper(x, AppTest::addOne);
        int y = oper(x, z -> z * 8);

        System.out.println("after oper:" + y);

        Predicate<Integer> preOdd = in -> in % 2 == 1;
        System.out.println(preOdd.negate().test(7));
    }


}
