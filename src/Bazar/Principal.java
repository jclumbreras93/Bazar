package Bazar;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;




public class Principal {
	private static Scanner t = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion=0;
		do {
			System.out.println("0-Salir");
			System.out.println("1-Crear Fichero Aleatorio");
			System.out.println("2-Mostrar FA");
			System.out.println("3-Alta Producto");
			System.out.println("4-Modificar stock");
			System.out.println("5-Borrar producto");
			System.out.println("6-Crear Fichero Objetos");
			System.out.println("7-Mostrar FO");
			opcion = t.nextInt();t.nextLine();
			switch(opcion) {
			case 1:
				crearFA();
				break;
			case 2:
				mostrarFA();
				break;
			case 3:
				altaProducto();
				break;
			case 4:
				modificarStock();
				break;
			case 5:
				borrarProducto();
				break;
			case 6:
				crearFO();
				break;
			case 7:
				mostrarFO();
				break;
			
			}
		}while(opcion!=0);
	}

	private static void mostrarFO() {
		// TODO Auto-generated method stub
		ObjectInputStream fichero = null;
		try {
			fichero = new ObjectInputStream(new FileInputStream("LaChina.obj"));
			while(true) {
				Articulo a = (Articulo) fichero.readObject();
				a.mostrar();
			}
			
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private static void crearFO() {
		// TODO Auto-generated method stub
		RandomAccessFile fichero = null;
		ObjectOutputStream fo = null;
		try {
			fichero = new RandomAccessFile("LaChina.bin", "r");
			fo = new ObjectOutputStream(new FileOutputStream("LaChina.obj",false));
			while(true) {
				Articulo a = new Articulo();
				String texto="";
				for(int i=0;i<3;i++) {
					texto+=fichero.readChar();
				}
				a.setCodigo(texto);
				texto="";
				for(int i=0;i<50;i++) {
					texto+=fichero.readChar();
				}
				a.setNombre(texto);
				a.setStock(fichero.readInt());
				fo.writeObject(a);
			}
			
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(fichero!=null) {
						fichero.close();
					}
					if(fo!=null) {
						fo.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

	}

	private static void borrarProducto() {
		// TODO Auto-generated method stub
		System.out.println("Código");
		String codigo = t.nextLine();
		if(existe(codigo)) {
			RandomAccessFile ficheroBazar = null;
			RandomAccessFile ficheroTmp = null;
			
			try {
				ficheroBazar = new RandomAccessFile("LaChina.bin", "r");
				ficheroTmp = new RandomAccessFile("LaChina.tmp", "rw");
				while(true) {
					String codigoF="";
					for(int i=0;i<3;i++) {
						codigoF+=ficheroBazar.readChar();
					}
					
					if(codigoF.trim().equals(codigo)) {
						ficheroBazar.seek(ficheroBazar.getFilePointer()+104);
					}
					else {
						String nombre ="";
						for(int i=0;i<50;i++) {
							nombre+=ficheroBazar.readChar();
						}
						int stock = ficheroBazar.readInt();
						ficheroTmp.writeChars(codigoF);
						ficheroTmp.writeChars(nombre);
						ficheroTmp.writeInt(stock);
					}
				}
				
			} 
			catch (EOFException e) {
				// TODO: handle exception
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
				try {
					if(ficheroBazar!=null) {
						ficheroBazar.close();
					}
					if(ficheroTmp!=null) {
						ficheroTmp.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			File fBazar = new File("LaChina.bin");
			if(!fBazar.delete()) {
				System.out.println("Error al borrar LaChina.bin");
			}
			File fTmp = new File("LaChina.tmp");
			if(!fTmp.renameTo(fBazar)) {
				System.out.println("Error al renombrar LaChina.rmp");
			}
			
			
		}
		else {
			System.out.println("No existe el producto");
		}
	}

	private static void modificarStock() {
		// TODO Auto-generated method stub
		String codigo;
		System.out.println("Introduce el código");
		codigo= t.nextLine();
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile("LaChina.bin", "rw");
			while(true) {
				String texto="";
				for(int i=0;i<3;i++) {
					texto+=fichero.readChar();
				}
				if(texto.trim().equals(codigo)) {
					fichero.seek(fichero.getFilePointer()+100);
					System.out.println("Nuevo stock:");
					int stock = t.nextInt();t.nextLine();
					fichero.writeInt(stock);
					break;
				}
				else {
					fichero.seek(fichero.getFilePointer()+104);
				}
			}
			
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	private static void altaProducto() {
		// TODO Auto-generated method stub
		Articulo a = new Articulo();
		System.out.println("Introduce código:");
		a.setCodigo(t.nextLine());
		if(!existe(a.getCodigo())) {
			System.out.println("Nombre");
			a.setNombre(t.nextLine());
			System.out.println("Stock");
			a.setStock(t.nextInt());t.nextLine();
			
			RandomAccessFile fichero = null;
			try {
				fichero = new RandomAccessFile("LaChina.bin", "rw");
				fichero.seek(fichero.length());
				
				StringBuilder codigo = new StringBuilder(a.getCodigo());
				codigo.setLength(3);
				fichero.writeChars(codigo.toString());
				StringBuilder nombre = new StringBuilder(a.getNombre());
				nombre.setLength(50);
				fichero.writeChars(nombre.toString());
				fichero.writeInt(a.getStock());
				
			} 
			catch (EOFException e) {
				// TODO: handle exception
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(fichero!=null) {
					try {
						fichero.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			System.out.println("Producto ya existe");
		}
	}

	private static boolean existe(String codigo) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile("LaChina.bin", "r");
			while(true) {
				String texto="";
				for(int i=0;i<3;i++) {
					texto+=fichero.readChar();
				}
				if(texto.trim().equals(codigo)) {
					resultado=true;
					break;
				}
				else {
					fichero.seek(fichero.getFilePointer()+104);
				}
			}
			
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	private static void mostrarFA() {
		// TODO Auto-generated method stub
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile("LaChina.bin", "r");
			while(true) {
				Articulo a = new Articulo();
				String texto="";
				for(int i=0;i<3;i++) {
					texto+=fichero.readChar();
				}
				a.setCodigo(texto);
				texto="";
				for(int i=0;i<50;i++) {
					texto+=fichero.readChar();
				}
				a.setNombre(texto);
				a.setStock(fichero.readInt());
				a.mostrar();
			}
			
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void crearFA() {
		// TODO Auto-generated method stub
		Bazar b = unmarshall();
		
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile(b.getNombre()+".bin", "rw");
			for(Articulo a:b.getListaA()) {
				if(!a.isBaja()) {
					StringBuilder codigo = 
							new StringBuilder(a.getCodigo());
					codigo.setLength(3);
					fichero.writeChars(codigo.toString());
					
					StringBuilder nombre = 
							new StringBuilder(a.getNombre());
					nombre.setLength(50);
					fichero.writeChars(nombre.toString());
					
					fichero.writeInt(a.getStock());
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private static Bazar unmarshall() {
		// TODO Auto-generated method stub
		Bazar resultado = null;
		File fichero = new File("bazar.xml");
		try {
			if(fichero.exists()) {
				JAXBContext contexto = JAXBContext.newInstance(Bazar.class);
				Unmarshaller um = contexto.createUnmarshaller();
				resultado = (Bazar)um.unmarshal(fichero);
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
}
