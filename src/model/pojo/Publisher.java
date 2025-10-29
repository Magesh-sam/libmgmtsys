package src.model.pojo;

public class Publisher {
    private int publisherId;
    private String name;
    private String address;

    public Publisher(int publisherId, String name, String address) {
        this.publisherId = publisherId;
        this.name = name;
        this.address = address;
    }

    public Publisher(int publisherId, String name) {
        this.publisherId = publisherId;
        this.name = name;
    }

    public Publisher(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Publisher(String name) {
        this.name = name;
    }

    public Publisher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Publisher [publisherId=" + publisherId + ", name=" + name + ", address=" + address + "]";
    }

}
