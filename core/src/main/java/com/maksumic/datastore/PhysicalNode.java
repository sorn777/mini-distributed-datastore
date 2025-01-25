package com.maksumic.datastore;

public final class PhysicalNode {
    private final String id;
    private final String address;

    public PhysicalNode(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + "@" + address;
    }
}
