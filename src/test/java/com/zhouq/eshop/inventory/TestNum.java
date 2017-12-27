package com.zhouq.eshop.inventory;

/**
 * todo
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/21 21:40
 */
public class TestNum {

    // 通过匿名内部内覆盖 ThreadLocal 的initalValue 方法。指定初始值
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    //获取下一个序列值
    public int getNextNum(){
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {

        TestNum testNum = new TestNum();
        TestClient t1 = new TestClient(testNum);
        TestClient t2 = new TestClient(testNum);
        TestClient t3 = new TestClient(testNum);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class TestClient extends Thread{
        private TestNum sn;
        public TestClient(TestNum sn){
            this.sn = sn;
        }

        public void run(){
            for (int i = 0;i <3;i++){
                System.out.println("thread["+Thread.currentThread().getName()+"] --> sn["+sn.getNextNum()+"]");
            }
        }
    }
}
