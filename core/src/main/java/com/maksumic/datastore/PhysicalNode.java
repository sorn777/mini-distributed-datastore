package com.maksumic.datastore;

public final class PhysicalNode {
    private final String id;
    private final String address;
    private final PhysicalNodeSize size;

    public PhysicalNode(String id, String address, PhysicalNodeSize size) {
        this.id = id;
        this.address = address;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public PhysicalNodeSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return id + "@" + address;
    }
}
