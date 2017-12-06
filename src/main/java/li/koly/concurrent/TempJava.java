//package li.koly.concurrent;
//
//public class TempJava {
//    /**
//     * 一个过渡的table表  只有在扩容的时候才会使用
//     */
//    private transient volatile Node<K,V>[] nextTable;
//
//    /**
//     * Moves and/or copies the nodes in each bin to new table. See
//     * above for explanation.
//     */
//    private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
//        int n = tab.length, stride;
//        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
//            stride = MIN_TRANSFER_STRIDE; // subdivide range
//        if (nextTab == null) {            // initiating
//            try {
//                @SuppressWarnings("unchecked")
//                Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];//构造一个nextTable对象 它的容量是原来的两倍
//                nextTab = nt;
//            } catch (Throwable ex) {      // try to cope with OOME
//                sizeCtl = Integer.MAX_VALUE;
//                return;
//            }
//            nextTable = nextTab;
//            transferIndex = n;
//        }
//        int nextn = nextTab.length;
//        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);//构造一个连节点指针 用于标志位
//        boolean advance = true;//并发扩容的关键属性 如果等于true 说明这个节点已经处理过
//        boolean finishing = false; // to ensure sweep before committing nextTab
//        for (int i = 0, bound = 0;;) {
//            Node<K,V> f; int fh;
//            //这个while循环体的作用就是在控制i--  通过i--可以依次遍历原hash表中的节点
//            while (advance) {
//                int nextIndex, nextBound;
//                if (--i >= bound || finishing)
//                    advance = false;
//                else if ((nextIndex = transferIndex) <= 0) {
//                    i = -1;
//                    advance = false;
//                }
//                else if (U.compareAndSwapInt
//                        (this, TRANSFERINDEX, nextIndex,
//                                nextBound = (nextIndex > stride ?
//                                        nextIndex - stride : 0))) {
//                    bound = nextBound;
//                    i = nextIndex - 1;
//                    advance = false;
//                }
//            }
//            if (i < 0 || i >= n || i + n >= nextn) {
//                int sc;
//                if (finishing) {
//                    //如果所有的节点都已经完成复制工作  就把nextTable赋值给table 清空临时对象nextTable
//                    nextTable = null;
//                    table = nextTab;
//                    sizeCtl = (n << 1) - (n >>> 1);//扩容阈值设置为原来容量的1.5倍  依然相当于现在容量的0.75倍
//                    return;
//                }
//                //利用CAS方法更新这个扩容阈值，在这里面sizectl值减一，说明新加入一个线程参与到扩容操作
//                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
//                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
//                        return;
//                    finishing = advance = true;
//                    i = n; // recheck before commit
//                }
//            }
//            //如果遍历到的节点为空 则放入ForwardingNode指针
//            else if ((f = tabAt(tab, i)) == null)
//                advance = casTabAt(tab, i, null, fwd);
//                //如果遍历到ForwardingNode节点  说明这个点已经被处理过了 直接跳过  这里是控制并发扩容的核心
//            else if ((fh = f.hash) == MOVED)
//                advance = true; // already processed
//            else {
//                //节点上锁
//                synchronized (f) {
//                    if (tabAt(tab, i) == f) {
//                        Node<K,V> ln, hn;
//                        //如果fh>=0 证明这是一个Node节点
//                        if (fh >= 0) {
//                            int runBit = fh & n;
//                            //以下的部分在完成的工作是构造两个链表  一个是原链表  另一个是原链表的反序排列
//                            Node<K,V> lastRun = f;
//                            for (Node<K,V> p = f.next; p != null; p = p.next) {
//                                int b = p.hash & n;
//                                if (b != runBit) {
//                                    runBit = b;
//                                    lastRun = p;
//                                }
//                            }
//                            if (runBit == 0) {
//                                ln = lastRun;
//                                hn = null;
//                            }
//                            else {
//                                hn = lastRun;
//                                ln = null;
//                            }
//                            for (Node<K,V> p = f; p != lastRun; p = p.next) {
//                                int ph = p.hash; K pk = p.key; V pv = p.val;
//                                if ((ph & n) == 0)
//                                    ln = new Node<K,V>(ph, pk, pv, ln);
//                                else
//                                    hn = new Node<K,V>(ph, pk, pv, hn);
//                            }
//                            //在nextTable的i位置上插入一个链表
//                            setTabAt(nextTab, i, ln);
//                            //在nextTable的i+n的位置上插入另一个链表
//                            setTabAt(nextTab, i + n, hn);
//                            //在table的i位置上插入forwardNode节点  表示已经处理过该节点
//                            setTabAt(tab, i, fwd);
//                            //设置advance为true 返回到上面的while循环中 就可以执行i--操作
//                            advance = true;
//                        }
//                        //对TreeBin对象进行处理  与上面的过程类似
//                        else if (f instanceof TreeBin) {
//                            TreeBin<K,V> t = (TreeBin<K,V>)f;
//                            TreeNode<K,V> lo = null, loTail = null;
//                            TreeNode<K,V> hi = null, hiTail = null;
//                            int lc = 0, hc = 0;
//                            //构造正序和反序两个链表
//                            for (Node<K,V> e = t.first; e != null; e = e.next) {
//                                int h = e.hash;
//                                TreeNode<K,V> p = new TreeNode<K,V>
//                                        (h, e.key, e.val, null, null);
//                                if ((h & n) == 0) {
//                                    if ((p.prev = loTail) == null)
//                                        lo = p;
//                                    else
//                                        loTail.next = p;
//                                    loTail = p;
//                                    ++lc;
//                                }
//                                else {
//                                    if ((p.prev = hiTail) == null)
//                                        hi = p;
//                                    else
//                                        hiTail.next = p;
//                                    hiTail = p;
//                                    ++hc;
//                                }
//                            }
//                            //如果扩容后已经不再需要tree的结构 反向转换为链表结构
//                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
//                                    (hc != 0) ? new TreeBin<K,V>(lo) : t;
//                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
//                                    (lc != 0) ? new TreeBin<K,V>(hi) : t;
//                            //在nextTable的i位置上插入一个链表
//                            setTabAt(nextTab, i, ln);
//                            //在nextTable的i+n的位置上插入另一个链表
//                            setTabAt(nextTab, i + n, hn);
//                            //在table的i位置上插入forwardNode节点  表示已经处理过该节点
//                            setTabAt(tab, i, fwd);
//                            //设置advance为true 返回到上面的while循环中 就可以执行i--操作
//                            advance = true;
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
