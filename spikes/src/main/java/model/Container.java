package model;

import java.util.Set;

public class Container implements Identifier{
	Integer id;
	String name;
	Set<Element> elementos;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Element> getElementos() {
		return elementos;
	}
	public void setElementos(Set<Element> elementos) {
		this.elementos = elementos;
	}
	@Override
	public String toString() {
		return "Container [idCont=" + id + ", name=" + name
				+ ", elementos=" + elementos + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer idContainer) {
		this.id = idContainer;
	}

}
