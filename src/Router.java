import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Clase para guardar y obtner las direcciones IP
 * @author PB
 *
 */
public class Router {
	private String direccion;
	private String puerto;

	/* METODOS */
	@Override
	public String toString() {
		return direccion + ":" + puerto;
	}

	public String getDireccionETH0() {
		String interfaz = "wlan0";
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface ni = e.nextElement();
				Enumeration<InetAddress> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					InetAddress ip = e2.nextElement();
					if (interfaz.equals(ni.getName())) { // Cambiar linea por eth0
						return ip.toString();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR, no se ha encontrado una IP para " + interfaz);
			System.exit(0);
		}
		return null;
	}

	/* GETTERS Y SETTERS */
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPuerto() {
		return this.puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	/**
	 * 
	 * @return direccion IP en forma Inetaddress
	 */
	public InetAddress getInet() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(this.direccion.substring(1, this.direccion.length()));
		} catch (Exception e) {
		}
		return addr;
	}

	/* CONSTRUCTOR */
	public Router() {
	}

	/**
	 * Constructor asigna direccion y puerto al arrancar el programa
	 * @param args String de entrada del main()
	 */

	public Router(String[] args) {
		if (args.length == 0) { // Si no tiene datos de entrada
			this.direccion = this.getDireccionETH0(); // Obtiene y asigna la direccion IP del puerto "eth0"
			this.puerto = "5512";
		} else {
			String[] entrada = args[0].split(":");
			if (entrada.length == 1) { // Si no tiene un puerto asignado
				String[] separar = entrada[0].split("/");
				if (separar.length == 1) {
					this.direccion = entrada[0];
					this.puerto = "5512";
				} else if (separar.length == 2) { // si es una ruta conectada
					this.direccion = separar[0];
					// Que hacemos con el campo longitud?
					// Una clase hija??
					// Porque hay que a�adirle el campo "len"
				}
			} else if (entrada.length == 2) { // Si ya tiene los dos campos inicializados
				this.direccion = entrada[0];
				this.puerto = entrada[1];
			} else {
				this.direccion = "ERROR";
			}
		}
	}

	/**
	 * Constructor para las direcciones del archivo .topo
	 * @param args p.e. "192.168.0.43" � "192.168.0.43:7878"
	 */
	public Router(String args) {
		String[] entrada = args.split(":");
		if (entrada.length == 1) {
			this.direccion = entrada[0];
			this.puerto = "5512";
		} else if (entrada.length == 2) {
			this.direccion = entrada[0];
			this.puerto = entrada[1];
		} else {
			this.direccion = "ERROR";
		}
	}
}