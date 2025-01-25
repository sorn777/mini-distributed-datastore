package com.maksumic.datastore;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClusterTest {
    private Cluster cluster;

    @BeforeEach
    public void beforeEach() {
        cluster = new Cluster(3);
    }

    @Test
    public void testAddNodesThenGetNodeForKey() {
        PhysicalNode pn1 = new PhysicalNode("server1", "192.168.1.1", PhysicalNodeSize.SMALL);
        PhysicalNode pn2 = new PhysicalNode("server2", "192.168.1.2", PhysicalNodeSize.MEDIUM);
        PhysicalNode pn3 = new PhysicalNode("server3", "192.168.1.3", PhysicalNodeSize.LARGE);
        cluster.addPhysicalNodes(pn1, pn2, pn3);
        Optional<VirtualNode> vn1 = cluster.getVirtualNodeForKey("");
        Optional<VirtualNode> vn2 = cluster.getVirtualNodeForKey("abcdef");
        Optional<VirtualNode> vn3 = cluster.getVirtualNodeForKey("abcdefghi");
        Optional<VirtualNode> vn4 = cluster.getVirtualNodeForKey("abcdefghijkl");
        Optional<VirtualNode> vn5 = cluster.getVirtualNodeForKey("abcdefghijklmno");
        Optional<VirtualNode> vn6 = cluster.getVirtualNodeForKey("abcdefghijklmnopqr");
        Optional<VirtualNode> vn7 = cluster.getVirtualNodeForKey("abcdefghijklmnopqrstu");
        Optional<VirtualNode> vn8 = cluster.getVirtualNodeForKey("abcdefghijklmnopqrstuvwx");
        Optional<VirtualNode> vn9 = cluster.getVirtualNodeForKey("abcdefghijklmnopqrstuvwxyz0");
        Optional<VirtualNode> vn10 = cluster.getVirtualNodeForKey("abcdefghijklmnopqrstuvwxyz0123");
        Assertions.assertTrue(vn1.isPresent(), "vn1 is empty");
        Assertions.assertTrue(vn2.isPresent(), "vn2 is empty");
        Assertions.assertTrue(vn3.isPresent(), "vn3 is empty");
        Assertions.assertTrue(vn4.isPresent(), "vn4 is empty");
        Assertions.assertTrue(vn5.isPresent(), "vn5 is empty");
        Assertions.assertTrue(vn6.isPresent(), "vn6 is empty");
        Assertions.assertTrue(vn7.isPresent(), "vn7 is empty");
        Assertions.assertTrue(vn8.isPresent(), "vn8 is empty");
        Assertions.assertTrue(vn9.isPresent(), "vn9 is empty");
        Assertions.assertTrue(vn10.isPresent(), "vn10 is empty");
        Assertions.assertEquals(pn3, vn1.get().getNode());
        Assertions.assertEquals(pn3, vn2.get().getNode());
        Assertions.assertEquals(pn2, vn3.get().getNode());
        Assertions.assertEquals(pn2, vn4.get().getNode());
        Assertions.assertEquals(pn3, vn5.get().getNode());
        Assertions.assertEquals(pn2, vn6.get().getNode());
        Assertions.assertEquals(pn2, vn7.get().getNode());
        Assertions.assertEquals(pn2, vn8.get().getNode());
        Assertions.assertEquals(pn3, vn9.get().getNode());
        Assertions.assertEquals(pn1, vn10.get().getNode());
    }
}
