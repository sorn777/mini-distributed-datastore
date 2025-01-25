package com.maksumic.datastore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Represents a distributed hash ring for a key-value store, using consistent hashing with virtual nodes to map keys to
 * physical nodes.
 */
public final class Cluster {
    private final ConcurrentNavigableMap<Integer, VirtualNode> ring;

    /**
     * Constructs a new Cluster.
     */
    public Cluster(int numberOfReplicas) {
        this.ring = new ConcurrentSkipListMap<>();
    }

    /**
     * Adds multiple physical nodes to the cluster, creating virtual nodes (replicas) for each.
     *
     * @param physicalNodes an array of physical nodes to add to the cluster
     */
    public synchronized void addPhysicalNodes(PhysicalNode... physicalNodes) {
        for (PhysicalNode physicalNode : physicalNodes) {
            addPhysicalNode(physicalNode);
        }
    }

    /**
     * Adds a single physical node to the cluster, creating virtual nodes (replicas) for it.
     *
     * @param physicalNode the physical node to add to the cluster
     */
    public synchronized void addPhysicalNode(PhysicalNode physicalNode) {
        for (int i = 0; i < calculateNumberOfReplicas(physicalNode.getSize()); i++) {
            VirtualNode virtualNode = new VirtualNode(physicalNode, i);
            ring.put(hash(virtualNode.toString()), virtualNode);
        }
    }

    /**
     * Retrieves the virtual node responsible for the given key. If the ring is empty, an empty {@link Optional} is
     * returned. This is acceptable because an empty ring indicates that no physical nodes have been added to the
     * cluster yet. In this case, there is no node responsible for the key, so an empty result is returned.
     *
     * @param key the key for which to find the responsible virtual node
     * @return an {@link Optional} containing the virtual node for the key, or {@link Optional#empty()} if the ring is
     *         empty
     */
    public Optional<VirtualNode> getVirtualNodeForKey(String key) {
        int hash = hash(key);
        if (ring.isEmpty()) {
            return Optional.empty();
        }
        Integer target = ring.ceilingKey(hash);
        if (target == null) {
            target = ring.firstKey();
        }
        return Optional.of(ring.get(target));
    }

    /**
     * Computes the hash of a string. This is used to determine the position of a virtual node in the ring.
     *
     * @param key the string to hash
     * @return the hash value of the input string
     */
    private Integer hash(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(key.getBytes());
            return Math.abs(BytesUtil.bytesToInt(encodedHash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private int calculateNumberOfReplicas(PhysicalNodeSize physicalNodeSize) {
        return switch (physicalNodeSize) {
            case SMALL -> 1;
            case MEDIUM -> 3;
            case LARGE -> 5;
        };
    }
}
