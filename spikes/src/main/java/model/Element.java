package model;

public class Element implements Identifier {
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
}
