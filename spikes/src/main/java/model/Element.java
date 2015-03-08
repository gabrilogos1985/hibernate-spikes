package model;

public class Element implements Identifier, Comparable<Element> {
    Integer id;
    String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idElement) {
        this.id = idElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Element [idElement=" + id + ", name=" + name + "]";
    }

    @Override
    public int compareTo(Element o) {
        if (this.getName() == null) return -1;
        if (o.getName() == null) return 1;
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;

        Element element = (Element) o;

        if (id != null ? !id.equals(element.id) : element.id != null) return false;
        if (name != null ? !name.equals(element.name) : element.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

