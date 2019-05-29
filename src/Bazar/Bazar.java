package Bazar;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement
@XmlType(propOrder= {"nombre","localidad","listaA"})
public class Bazar implements Serializable{
	private String nombre;
	private String localidad;
	private ArrayList<Articulo> listaA=new ArrayList<>();
	public Bazar() {
		super();
	}
	@XmlElement
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlElement
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	@XmlElementWrapper(name="listaArticulos")
	@XmlElement(name="artiuculo")
	public ArrayList<Articulo> getListaA() {
		return listaA;
	}
	public void setListaA(ArrayList<Articulo> listaA) {
		this.listaA = listaA;
	}
	
	
}
