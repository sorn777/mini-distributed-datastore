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
        PhysicalNode pn1 = new PhysicalNode("server1", "192.168.1.1");
        PhysicalNode pn2 = new PhysicalNode("server2", "192.168.1.2");
        PhysicalNode pn3 = new PhysicalNode("server3", "192.168.1.3");
        cluster.addPhysicalNodes(pn1, pn2, pn3);
        Optional<VirtualNode> vn1 = cluster.getVirtualNodeForKey("abc");
        Optional<VirtualNode> vn2 = cluster.getVirtualNodeForKey("abcdef");
        Optional<VirtualNode> vn3 = cluster.getVirtualNodeForKey("abcdefghi");
        Assertions.assertTrue(vn1.isPresent(), "vn1 is empty");
        Assertions.assertTrue(vn2.isPresent(), "vn2 is empty");
        Assertions.assertTrue(vn3.isPresent(), "vn3 is empty");
        Assertions.assertEquals(pn1, vn1.get().getNode());
        Assertions.assertEquals(pn2, vn2.get().getNode());
        Assertions.assertEquals(pn3, vn3.get().getNode());
        Assertions.assertEquals(2, vn1.get().getReplicaIndex());
        Assertions.assertEquals(2, vn2.get().getReplicaIndex());
        Assertions.assertEquals(0, vn3.get().getReplicaIndex());
    }
}
