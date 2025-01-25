package com.maksumic.datastore;

public final class VirtualNode {
    private final PhysicalNode node;
    private final int replicaIndex;

    public VirtualNode(PhysicalNode node, int replicaIndex) {
        this.node = node;
        this.replicaIndex = replicaIndex;
    }

    public PhysicalNode getNode() {
        return node;
    }

    public int getReplicaIndex() {
        return replicaIndex;
    }

    @Override
    public String toString() {
        return node.getId() + "_replica_" + replicaIndex;
    }
}
