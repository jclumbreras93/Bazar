package Bazar;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"nombre","stock"})
public class Articulo implements Serializable{
	private String codigo;
	private boolean baja;
	private String nombre;
	private int stock;
	public Articulo() {
		super();
	}
	@XmlAttribute
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@XmlAttribute
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
		this.baja = baja;
	}
	@XmlElement
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlElement
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void mostrar() {
		System.out.println("Codigo:"+codigo+
				"\tNombre:"+nombre +
				"\tStock:"+stock);
	}
}
